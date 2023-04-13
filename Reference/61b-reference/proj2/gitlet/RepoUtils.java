package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Repository.checkout;
import static gitlet.Utils.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Providing several useful utility functions.
 *
 * @author Christina0031
 */
public class RepoUtils {
    /**
     * The current working directory.
     */
    static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    static final File GITLET_DIR = join(CWD, ".gitlet");

    //static final String slash = System.getProperty("file.separator");
    static final String SLASH = "/";

    static void makeDir() {
        String[] directory = {"blobs", "commits", "branches", "info"};
        File dirs;
        for (String s : directory) {
            dirs = new File(GITLET_DIR + SLASH + s);
            dirs.mkdirs();
        }
    }

    static void initStagingArea() {
        StagingArea stagingArea = new StagingArea();
        cleanStagingArea(stagingArea);
    }

    static void cleanStagingArea(StagingArea stagingArea) {
        stagingArea.clean();
        saveStagingArea(stagingArea);
    }

    static void saveStagingArea(StagingArea stagingArea) {
        File f = new File(GITLET_DIR + SLASH + "info" + SLASH + "stagingArea");
        Utils.writeObject(f, stagingArea);
    }

    static StagingArea getStagingArea() {
        File file = new File(GITLET_DIR + SLASH + "info" + SLASH + "stagingArea");
        StagingArea stagingArea = Utils.readObject(file, StagingArea.class);
        return stagingArea;
    }

    static Branch createBranch(String name, String pointTo) {
        Branch branch = new Branch(name, pointTo);
        File f = getBranchFile(GITLET_DIR, name);
        Utils.writeObject(f, branch);
        return branch;
    }

    static File getBranchFile(File path, String name) {
        String fileName = name;
        if (name.contains("/")) {
            fileName = sha1(name);
        }
        return new File(path + SLASH + "branches" + SLASH + fileName);
    }

    static Branch createRemoteBranch(File remoteGitlet, String name, String pointTo) {
        File f = getBranchFile(remoteGitlet, name);
        Branch branch = new Branch(name, pointTo);
        Utils.writeObject(f, branch);
        return branch;
    }

    static Branch createHEAD(String pointTo) {
        Branch head = new Branch(pointTo);
        File f = new File(GITLET_DIR + SLASH + "branches" + SLASH + "HEAD");
        Utils.writeObject(f, head);
        return head;
    }

    static void changeHEAD(String dest) {
        Branch head = getBranch("HEAD");
        head.changePointTo(dest);
        File f = new File(GITLET_DIR + SLASH + "branches" + SLASH + "HEAD");
        Utils.writeObject(f, head);
    }

    static Branch getCurrBranch() {
        String headBranch = getBranch("HEAD").pointTo();
        return getBranch(headBranch);
    }

    static Branch getRemoteBranch(File remoteGitlet, String remoteBranchName) {
        File f = getBranchFile(remoteGitlet, remoteBranchName);
        if (!f.exists()) {
            return null;
        }
        return Utils.readObject(f, Branch.class);
    }

    static Branch changeBranch(Branch branch, String commit) {
        branch.changeCommitTo(commit);
        File f = getBranchFile(GITLET_DIR, branch.getName());
        Utils.writeObject(f, branch);
        return branch;
    }

    static Branch changeRemoteBranch(File remoteGitlet, Branch branch, String commit) {
        branch.changeCommitTo(commit);
        File f = getBranchFile(remoteGitlet, branch.getName());
        Utils.writeObject(f, branch);
        return branch;
    }

    static void removeBranch(String name) {
        File f = getBranchFile(GITLET_DIR, name);
        f.delete();
    }

    static Commit getLastCommit() {
        String commitID = getCurrBranch().getCommitID();
        return getCommit(commitID);
    }

    static String initCommit() {
        Commit init = new Commit("initial commit", new Date(0), new TreeMap<>(), null);
        byte[] serialized = Utils.serialize(init);
        String commitID = sha1(serialized);
        File file = new File(GITLET_DIR + SLASH + "commits" + SLASH + commitID);
        Utils.writeContents(file, serialized);
        return commitID;
    }

    static String makeCommit(String msg, Map<String, String> allFiles, String parent) {
        Commit commit = new Commit(msg, allFiles, parent);
        byte[] serialized = Utils.serialize(commit);
        String commitID = sha1(serialized);
        File f = new File(GITLET_DIR + SLASH + "commits" + SLASH + commitID);
        Utils.writeContents(f, serialized);
        return commitID;
    }

    static String makeMergeCommit(
            String msg, Map<String, String> allFiles, String parent, String secondParent) {
        Commit commit = new Commit(msg, allFiles, parent, secondParent);
        byte[] serialized = Utils.serialize(commit);
        String commitID = sha1(serialized);
        File f = new File(GITLET_DIR + SLASH + "commits" + SLASH + commitID);
        Utils.writeContents(f, serialized);
        return commitID;
    }

    static void deleteBlob(String fileID) {
        File file = new File(GITLET_DIR + SLASH + "blobs" + SLASH + fileID);
        file.delete();
    }

    static void writeBlob(String contents, String fileID) {
        File file = new File(GITLET_DIR + SLASH + "blobs" + SLASH + fileID);
        Utils.writeContents(file, contents);
    }

    static void readBlob(String fileName, String fileID) {
        File blob = new File(GITLET_DIR + SLASH + "blobs" + SLASH + fileID);
        File file = new File(fileName);
        Utils.writeContents(file, Utils.readContentsAsString(blob));
    }

    static String printCommit(String commitID) {
        Commit commit = getCommit(commitID);
        Utils.message("===");
        Utils.message("commit " + commitID);
        if (commit.hasSecondParent()) {
            Utils.message("Merge: " + commit.getParent().substring(0, 7)
                    + " " + commit.getSecondParent().substring(0, 7));
        }
        Date date = commit.getDate();
        SimpleDateFormat f = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        f.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        Utils.message("Date: " + f.format(date));
        Utils.message(commit.getMessage());
        Utils.message("");

        return commit.getParent();
    }

    static Commit getCommit(String id) {
        if (id.length() < 40) {
            List<String> commits = Utils.plainFilenamesIn(GITLET_DIR + SLASH + "commits");
            for (String s : commits) {
                if (s.startsWith(id)) { // when use abbr. ignore duplicate
                    id = s;
                    break;
                }
            }
        }
        File commitID = new File(GITLET_DIR + SLASH + "commits" + SLASH + id);
        if (!commitID.exists()) {
            Utils.message("No commit with that id exists.");
            System.exit(0);
        }
        return Utils.readObject(commitID, Commit.class);
    }

    static Branch getBranch(String name) {
        File f = getBranchFile(GITLET_DIR, name);
        if (!f.exists()) {
            Utils.message("No such branch exists.");
            System.exit(0);
        }
        return Utils.readObject(f, Branch.class);
    }

    static Map<String, String> getFileSetToBeCommitted(StagingArea stagingArea, Commit commit) {
        Map<String, String> stagedFiles = stagingArea.getFiles();
        Map<String, String> originCommitFiles = commit.getFiles();
        for (String name : stagingArea.getRemovalFiles()) {
            originCommitFiles.remove(name);
        }
        for (String name : stagedFiles.keySet()) {
            originCommitFiles.put(name, stagedFiles.get(name));
        }
        return originCommitFiles;
    }

    static void printBranches() {
        Branch curr = getCurrBranch();
        List<String> branches = Utils.plainFilenamesIn(GITLET_DIR + SLASH + "branches");
        for (String name : branches) {
            if (name.equals("HEAD")) {
                continue;
            }
            if (name.equals(curr.getName())) {
                Utils.message("*" + name);
            } else {
                Utils.message(name);
            }
        }
        Utils.message("");
    }

    static Set<String> untrackedFile() {
        Set<String> untracked = new TreeSet<>();
        List<String> allFiles = Utils.plainFilenamesIn(CWD);
        Commit lastCommit = getLastCommit();
        StagingArea stagingArea = getStagingArea();
        for (String file : allFiles) {
            if (!lastCommit.contain(file) && !stagingArea.contain(file)) {
                untracked.add(file);
            }
        }
        return untracked;
    }

    static Set<String> modifiedFile() {
        Set<String> modified = new TreeSet<>();
        Commit lastCommit = getLastCommit();
        StagingArea stagingArea = getStagingArea();
        Set<String> set = lastCommit.getFilesSet();
        for (String fileName : set) {
            if (stagingArea.contain(fileName)) {
                continue;
            }
            File file = new File(fileName);
            if (file.exists()) {
                String contents = Utils.readContentsAsString(file);
                String fileID = sha1(contents);
                if (lastCommit.contain(fileName)
                        && !lastCommit.getFileID(fileName).equals(fileID)) {
                    modified.add(fileName + " (modified)");
                }
            } else {
                modified.add(fileName + " (deleted)");
            }
        }
        return modified;
    }

    static void deleteAllFilesInCWD() {
        List<String> allFiles = Utils.plainFilenamesIn(CWD);
        for (String file : allFiles) {
            File f = new File(file);
            f.delete();
        }
    }

    static void validDirectory() {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }

    static void restoreCommit(Commit commit) {
        if (!untrackedFile().isEmpty()) {
            String msg0 = "There is an untracked file in the way; ";
            String msg1 = "delete it, or add and commit it first.";
            Utils.message(msg0 + msg1);
            System.exit(0);
        }
        deleteAllFilesInCWD();
        Set<String> newFiles = commit.getFilesSet();
        for (String file : newFiles) {
            commit.restoreFile(file);
        }
    }

    static String latestCommit(String c1, String c2) {
        if (c1 == null) {
            return c2;
        } else if (c2 == null) {
            return c1;
        }
        Commit commit1 = getCommit(c1);
        Commit commit2 = getCommit(c2);
        int cmp = commit1.getDate().compareTo(commit2.getDate());
        if (cmp > 0) {
            return c1;
        } else {
            return c2;
        }
    }

    static String getSplitPoint(String commitID1, String commitID2) {
        if (commitID1.equals(commitID2)) {
            return commitID2;
        }
        Commit c1 = getCommit(commitID1);
        Commit c2 = getCommit(commitID2);

        if (c1.hasSecondParent()) {
            String res1 = getSplitPoint(c1.getParent(), commitID2);
            String res2 = getSplitPoint(c1.getSecondParent(), commitID2);
            return latestCommit(res1, res2);
        } else if (c2.hasSecondParent()) {
            String res1 = getSplitPoint(commitID1, c2.getParent());
            String res2 = getSplitPoint(commitID1, c2.getSecondParent());
            return latestCommit(res1, res2);
        } else {
            String latest = latestCommit(commitID1, commitID2);
            if (latest.equals(commitID1)) {
                if (c1.hasParent()) {
                    return getSplitPoint(c1.getParent(), commitID2);
                }
            } else {
                if (c2.hasParent()) {
                    return getSplitPoint(commitID1, c2.getParent());
                }
            }
        }
        return null;
    }

    static String splitPoint(Branch curr, Branch given) {
        String originCurr = curr.getCommitID();
        String originGiven = given.getCommitID();
        String splitPoint = getSplitPoint(originCurr, originGiven);
        if (splitPoint.equals(originCurr)) {
            String[] args = {"checkout", given.getName()};
            checkout(args);
            Utils.message("Current branch fast-forwarded.");
            System.exit(0);
        }
        if (splitPoint.equals(originGiven)) {
            Utils.message("Given branch is an ancestor of the current branch.");
            System.exit(0);
        }
        return splitPoint;
    }

    static void handleConflict(Map<String, String> currFiles, Map<String, String> givenFiles,
                               String conflictedFileName) {
        File file = new File(conflictedFileName);
        String fileHead = "<<<<<<< HEAD\n";
        String separator = "=======\n";
        String fileFoot = ">>>>>>>\n";
        String givenContent = null;
        String currContent = null;
        if (currFiles.containsKey(conflictedFileName)) {
            String fileID = currFiles.get(conflictedFileName);
            File blob = new File(GITLET_DIR + SLASH + "blobs" + SLASH + fileID);
            currContent = Utils.readContentsAsString(blob);
        }
        if (givenFiles.containsKey(conflictedFileName)) {
            String fileID = givenFiles.get(conflictedFileName);
            File blob = new File(GITLET_DIR + SLASH + "blobs" + SLASH + fileID);
            givenContent = Utils.readContentsAsString(blob);

        }
        if (givenContent == null) {
            Utils.writeContents(file, fileHead, currContent, separator, "", fileFoot);
        } else if (currContent == null) {
            Utils.writeContents(file, fileHead, "", separator, givenContent, fileFoot);
        } else {
            Utils.writeContents(file, fileHead, currContent, separator, givenContent, fileFoot);
        }
    }

    static boolean equal(Map<String, String> map1, Map<String, String> map2,
                         String name) {
        return map1.get(name).equals(map2.get(name));
    }

    static boolean processFiles(
            String splitPointID, Commit lastCommit, Branch given, StagingArea stagingArea) {
        Map<String, String> splitFiles = getCommit(splitPointID).getFiles();
        Map<String, String> currFiles = lastCommit.getFiles();
        Map<String, String> givenFiles = getCommit(given.getCommitID()).getFiles();

        boolean conflict = false;
        for (String s : givenFiles.keySet()) {
            boolean split = splitFiles.containsKey(s);
            boolean cur = currFiles.containsKey(s);
            boolean onlyModifiedInGiven = cur && split && equal(splitFiles, currFiles, s)
                    && !equal(splitFiles, givenFiles, s);
            boolean addedInGiven = !split && !cur;
            boolean allDiff = cur && split && !equal(givenFiles, currFiles, s)
                    && !equal(givenFiles, splitFiles, s) && !equal(currFiles, splitFiles, s);
            boolean addedDiff = cur && !split && !equal(givenFiles, currFiles, s);
            boolean delInCurAndModifiedInGiven = !cur && split && !equal(splitFiles, givenFiles, s);
            boolean delInCur = !cur && split && equal(splitFiles, givenFiles, s);
            if (onlyModifiedInGiven || addedInGiven) {
                String[] args = {"checkout", given.getCommitID(), "--", s};
                checkout(args);
                stagingArea.add(s, lastCommit);
            } else if (allDiff || addedDiff || delInCurAndModifiedInGiven) {
                handleConflict(currFiles, givenFiles, s);
                conflict = true;
                stagingArea.add(s, lastCommit);
            } else if (delInCur) {
                File f = new File(s);
                if (f.exists()) {
                    f.delete();
                }
                stagingArea.delete(s);
                stagingArea.addRemovedFiles(s);
            }
        }
        for (String s : currFiles.keySet()) {
            boolean split = splitFiles.containsKey(s);
            boolean give = givenFiles.containsKey(s);
            if (!give && split) {
                if (equal(splitFiles, currFiles, s)) {
                    File f = new File(s);
                    if (f.exists()) {
                        f.delete();
                    }
                    stagingArea.delete(s);
                    stagingArea.addRemovedFiles(s);
                } else {
                    handleConflict(currFiles, givenFiles, s);
                    conflict = true;
                    stagingArea.add(s, lastCommit);
                }
            }
        }
        return conflict;
    }


    static boolean isHistory(String commitID, String objectID) {
        if (commitID.equals(objectID)) {
            return true;
        }
        Commit c = getCommit(commitID);
        if (c.hasSecondParent()) {
            return isHistory(c.getParent(), objectID) || isHistory(c.getSecondParent(), objectID);
        } else if (c.hasParent()) {
            return isHistory(c.getParent(), objectID);
        } else {
            return false;
        }
    }

    static File getGitletDir(String remoteName) {
        File f = new File(GITLET_DIR + SLASH + "remotes" + SLASH + remoteName);
        if (!f.exists()) {
            Utils.message("A remote with that name does not exist.");
            System.exit(0);
        }
        Remote remote = readObject(f, Remote.class);
        File remoteGitlet = new File(remote.getDirectory());
        if (!remoteGitlet.exists()) {
            Utils.message("Remote directory not found.");
            System.exit(0);
        }
        return remoteGitlet;
    }

    static void copyCommitsAndBlobs(File from, File to, String endCommit, String startCommit) {
        Queue<String> commits = new ArrayDeque<>();
        commits.add(startCommit);
        while (!commits.isEmpty()) {
            String commitID = commits.remove();
            if (commitID == null || commitID.equals(endCommit)) {
                continue;
            }
            File f = new File(from + SLASH + "commits" + SLASH + commitID);
            File t = new File(to + SLASH + "commits" + SLASH + commitID);
            if (!t.exists()) {
                try {
                    Files.copy(f.toPath(), t.toPath(), REPLACE_EXISTING);
                    Commit commit = getCommit(commitID);
                    for (String s : commit.getFiles().values()) {
                        f = new File(from + SLASH + "blobs" + SLASH + s);
                        t = new File(to + SLASH + "blobs" + SLASH + s);
                        if (!t.exists()) {
                            Files.copy(f.toPath(), t.toPath(), REPLACE_EXISTING);
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e);
                    System.exit(0);
                }
            }
            Commit commit = getCommit(commitID);
            if (commit.hasSecondParent()) {
                commits.add(commit.getParent());
                commits.add(commit.getSecondParent());
            } else if (commit.hasParent()) {
                commits.add(commit.getParent());
            }
        }
    }

}

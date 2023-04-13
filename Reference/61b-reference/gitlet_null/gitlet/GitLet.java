package gitlet;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashSet;

import static gitlet.Utils.equal;

/**
 * represent the whole lifetime of internal objects
 * in each gitlet command run.
 * @author null
 */
public class GitLet {

    /** untracked files exists. */
    private static final String UNTRACKED_FILES_ERROR =
        "There is an untracked file in the way; "
                + "delete it, or add and commit it first.";
    /** sha1 length. */
    private static final int SHA1_LENGTH = 40;
    /** repo exists. */
    private static final String REPO_EXISTS_ERROR =
        "A Gitlet version-control system "
                + "already exists in the current directory.";
    /** the repo path. */
    private final Path repoDirPath;
    /** the head commit. */
    private Commit theHead;
    /** the stage. */
    private Stage theStage;

    /**
     * constructor!
     * @param repoDir .
     */
    public GitLet(Path repoDir) {
        this.repoDirPath = repoDir.toAbsolutePath();
    }

    /**
     * init.
     */
    public void init() {
        File repoDir = this.repoDirPath.toFile();
        if (repoDir.exists()) {
            if (isValidateRepo()) {
                throw Utils.error(REPO_EXISTS_ERROR);
            } else {
                repoDir.delete();
            }
        }
        repoDir.mkdirs();
        theStage = new Stage();
        saveStage(theStage);
        getBlobDirPath().toFile().mkdirs();
        getPath("commits").toFile().mkdirs();
        Commit initialCommit = createCommit("initial commit", 0);
        getBranchDirPath().toFile().mkdirs();
        setBranch(getBranchFile("master"), initialCommit);
        updateHead("master");
    }

    /**
     * Validate the internal structure of this repo.
     * this is critical since once it fails, the whole .gitlet is wiped out
     * @return
     */
    private boolean isValidateRepo() {
        File repoDir = this.repoDirPath.toFile();
        return repoDir.isDirectory();
    }

    /**
     * validate the repo subdir internal structure and do some init.
     */
    private void validateRepo() {
        if (!isValidateRepo()) {
            throw Utils.error("Not in an initialized Gitlet directory.");
        }
        theHead = getTheHead();
        theStage = getTheStage();
    }

    /**
     * get the stage.
     * @return
     */
    private Stage getTheStage() {
        if (theStage != null) {
            return theStage;
        }
        theStage = Utils.readObject(getStageFile(), Stage.class);
        return theStage;
    }

    /**
     * get stage file.
     * @return
     */
    public File getStageFile() {
        return getStagePath().toFile();
    }

    /**
     * get stage subdir path.
     * @return
     */
    public Path getStagePath() {
        return getPath("stage");
    }

    /**
     * save stage.
     * @param stage to save.
     */
    public void saveStage(Stage stage) {
        File stageFile = getStageFile();
        Utils.writeObject(stageFile, stage);
    }

    /**
     * get branch file.
     * @param branchName .
     * @return
     */
    private File getBranchFile(String branchName) {
        return Paths.get(getBranchDirPath().toString(), branchName).toFile();
    }

    /**
     * update head to point to given branch.
     * @param branchName .
     */
    private void updateHead(String branchName) {
        Utils.writeContents(getHeadFile(),
                "ref: " + getBranchRelativePath(branchName) + '\n');
        theHead = getCommit(branchName);
    }

    /**
     * get branch internal relative path.
     * @param branchName .
     * @return
     */
    private Path getBranchRelativePath(String branchName) {
        return Paths.get("refs", "heads", branchName);
    }

    /**
     * set branch to a commit.
     * @param branchFile the file of the branch.
     * @param commit the target commit.
     */
    private void setBranch(File branchFile, Commit commit) {
        Utils.writeContents(branchFile, commit.getKey() + '\n');
    }

    /**
     * commit a commit.
     * @param commit .
     */
    public void commit(Commit commit) {
        File commitFile = getPath("commits", commit.getKey()).toFile();
        Utils.writeObject(commitFile, commit);
        theStage.clean(this);
    }

    /**
     * get the head file.
     * @return
     */
    private File getHeadFile() {
        Path theHeadFilePath = Paths.get(this.repoDirPath.toString(), "HEAD");
        return theHeadFilePath.toFile();
    }

    /**
     * get the head.
     * @return
     */
    private Commit getTheHead() {
        if (theHead != null) {
            return theHead;
        }
        String content = Utils.readContentsAsString(getHeadFile()).strip();
        if (isKey(content)) {
            Path theHeadCommitPath = getPath("commits", content);
            theHead = Utils.readObject(
                    theHeadCommitPath.toFile(),
                    Commit.class);
        } else {
            if (!content.startsWith("ref: ")) {
                throw Utils.error("corrupted internal structure");
            }
            Path relativePath = Paths.get(content.strip().substring(5));
            assert (relativePath.startsWith("refs"));
            Path refPath = getPath(relativePath);
            File refFile = refPath.toFile();
            assert (refFile.exists());
            String key = Utils.readContentsAsString(refFile).strip();
            theHead = Utils.readObject(
                    getPath("commits", key).toFile(),
                    Commit.class);
        }
        return theHead;
    }

    /**
     * get path.
     * @param relativePath of the repo meta items.
     * @return
     */
    private Path getPath(Path relativePath) {
        assert (!relativePath.isAbsolute());
        return repoDirPath.resolve(relativePath).toAbsolutePath();
    }

    /**
     * check wither given content is a sha1 key.
     * @param content .
     * @return
     */
    private boolean isKey(String content) {
        return content.length() == SHA1_LENGTH;
    }

    /**
     * get repo subdir path.
     * @param dirs .
     * @return
     */
    private Path getPath(String...dirs) {
        return Paths.get(this.repoDirPath.toString(), dirs);
    }

    /**
     * log.
     */
    public void log() {
        validateRepo();
        Commit currentCommit = theHead;
        while (currentCommit != null) {
            System.out.println(currentCommit);
            currentCommit = getCommit(currentCommit.getParentKey());
        }
    }

    /**
     * status.
     */
    public void status() {
        validateRepo();
        showBranches();
        showStagedFiles();
        showRemovedFiles();
        showUnStagedModifications();
        showUntrackedFiles();
    }

    /**
     * show untracked files.
     */
    private void showUntrackedFiles() {
        System.out.println("=== Untracked Files ===");
        List<String> untracked = getUntrackedFiles(theStage, theHead);
        for (String file : untracked) {
            System.out.println(file);
        }
        System.out.println();
    }

    /**
     * show un-staged changes.
     */
    private void showUnStagedModifications() {
        System.out.println("=== Modifications Not Staged For Commit ===");
        Map<String, String> tracked = theHead.getFiles();
        List<String> sortable = new ArrayList<>(tracked.keySet());
        Collections.sort(sortable);
        for (String corePath : sortable) {
            File userFile = userPath(corePath).toFile();
            if (userFile.exists()) {
                String fileContentKey =
                        Utils.sha1(Utils.readContents(userFile));
                if (!equal(tracked.get(corePath), fileContentKey)) {
                    System.out.println(corePath + " (modified)");
                }
            } else if (!theStage.getRemovedFiles().contains(corePath)) {
                System.out.println(corePath + " (deleted)");
            }
        }
        System.out.println();
    }

    /**
     * show files to be removed.
     */
    private void showRemovedFiles() {
        System.out.println("=== Removed Files ===");
        for (String filePath : theStage.getRemovedFiles()) {
            System.out.println(filePath);
        }
        System.out.println();
    }

    /**
     * show stage files.
     */
    private void showStagedFiles() {
        System.out.println("=== Staged Files ===");
        for (String filePath : theStage.getAddedFiles().keySet()) {
            System.out.println(filePath);
        }
        System.out.println();
    }

    /**
     * get branch name.
     * @return
     */
    private List<String> getBranchNames() {
        File[] branchFiles = getBranchDirPath().toFile().listFiles();
        Arrays.sort(branchFiles);
        List<String> branchNames = new ArrayList<>();
        for (File branchFile : branchFiles) {
            branchNames.add(branchFile.getName());
        }
        return branchNames;
    }

    /**
     * show branches.
     */
    private void showBranches() {
        System.out.println("=== Branches ===");
        for (String branchName : getBranchNames()) {
            if (getHeadName().compareTo(branchName) == 0) {
                System.out.println("*" + branchName);
            } else {
                System.out.println(branchName);
            }
        }
        System.out.println();
    }

    /**
     * get the head name. Could be either a commit it points to or a branch.
     * @return
     */
    private String getHeadName() {
        String content = Utils.readContentsAsString(getHeadFile()).strip();
        if (isKey(content)) {
            return content;
        } else {
            assert content.startsWith("ref: ");
            Path relativePath = Paths.get(content.substring(5));
            assert (relativePath.startsWith("refs"));
            Path refPath = getPath(relativePath);
            File refFile = refPath.toFile();
            assert (refFile.exists());
            return refPath.getFileName().toString();
        }
    }

    /**
     * get branch subdir path.
     * @return
     */
    private Path getBranchDirPath() {
        return getPath("refs", "heads");
    }

    /**
     * add.
     * @param args .
     */
    public void add(String[]args) {
        validateRepo();
        if (args.length != 2) {
            throw Utils.error("Incorrect operands.");
        }
        Path userPath = Paths.get(args[1]).toAbsolutePath();
        if (!userPath.toFile().exists()) {
            throw Utils.error("File does not exist.");
        }
        Path corePath = corePath(userPath);
        byte[] fileContent = Utils.readContents(userPath.toFile());
        String key = Utils.sha1(fileContent);
        if (theStage.getRemovedFiles().contains(corePath.toString())) {
            theStage.cancelRemove(corePath, this);
        }
        String blobKey = theHead.getFiles().get(corePath.toString());
        if (blobKey == null || blobKey.compareTo(key) != 0) {
            createBlob(getBlobFile(key), fileContent);
            theStage.add(corePath, key, this);
        }
    }

    /**
     * get blob subdir path.
     * @return
     */
    public Path getBlobDirPath() {
        return getPath("blobs");
    }

    /**
     * get the blob file.
     * @param blobKey .
     * @return
     */
    public File getBlobFile(String blobKey) {
        return Paths.get(getBlobDirPath().toString(), blobKey).toFile();
    }

    /**
     * create a blob file.
     * @param blobFile .
     * @param content .
     */
    public void createBlob(File blobFile, byte[] content) {
        Utils.writeContents(blobFile, content);
    }

    /**
     * commit a commit.
     * @param message .
     */
    public void commit(String message) {
        validateRepo();
        String branchName = getCurrentBranchName();
        if (branchName == null) {
            throw Utils.error("cannot commit to detached head");
        }
        if (message.strip().length() == 0) {
            throw Utils.error("Please enter a commit message.");
        }
        if (theStage.getRemovedFiles().size() == 0
                && theStage.getAddedFiles().size() == 0) {
            throw Utils.error("No changes added to the commit.");
        }
        Commit commit = createCommit(message, System.currentTimeMillis());
        setBranch(getBranchFile(branchName), commit);
    }

    /**
     * get current branch name.
     * @return
     */
    private String getCurrentBranchName() {
        String headName = getHeadName();
        if (!isKey(headName)) {
            return headName;
        }
        return null;
    }

    /**
     * create a commit.
     * @param message commit message.
     * @param commitTime commit time.
     * @return
     */
    private Commit createCommit(String message, long commitTime) {
        Commit commit = new Commit(message, theHead);
        commit.commit(theStage, commitTime, this);
        return commit;
    }

    /**
     * create a commit.
     * @param message commit message.
     * @param commitTime commit time.
     * @param otherCommit 2nd parent of a merge commit.
     * @return
     */
    private Commit createCommit(String message,
                                long commitTime,
                                Commit otherCommit) {
        Commit commit = new Commit(message, theHead, otherCommit);
        commit.commit(theStage, commitTime, this);
        return commit;
    }

    /**
     * checkout.
     * @param args .
     */
    public void checkout(String[] args) {
        validateRepo();
        if (args.length < 2) {
            throw Utils.error("Incorrect operands.");
        }

        if (args.length == 2) {
            String branchName = args[1];
            if (!getBranchFile(branchName).exists()) {
                throw Utils.error("No such branch exists.");
            }
            if (branchName.compareTo(getCurrentBranchName()) == 0) {
                throw Utils.error("No need to checkout the current branch.");
            }
            Commit targetCommit = getCommit(branchName);
            Commit currentCommit = theHead;
            for (String coreFile : targetCommit.getFiles().keySet()) {
                if (!currentCommit.hasFile(coreFile)
                        && userPath(coreFile).toFile().exists()) {
                    throw Utils.error(UNTRACKED_FILES_ERROR);
                }
            }
            updateHead(branchName);
            checkoutCommit(currentCommit, targetCommit);
            cleanStage();
            return;
        }

        String filePath = "";
        Commit commit = theHead;
        if (args[1].compareTo("--") == 0) {
            filePath = args[2];
        } else if (args[2].compareTo("--") == 0) {
            if (args.length != 4) {
                throw Utils.error("Incorrect operands.");
            }
            commit = getCommit(args[1]);
            filePath = args[3];
        } else {
            throw Utils.error("Incorrect operands.");
        }
        checkoutFile(commit, corePath(filePath).toString());
    }

    /**
     * checkout file.
     * @param commit .
     * @param corePath .
     */
    private void checkoutFile(Commit commit, String corePath) {
        if (!commit.hasFile(corePath)) {
            throw Utils.error("File does not exist in that commit.");
        }
        File blobFile = getBlobFile(commit.getFileBlobKey(corePath));
        Utils.writeContents(userPath(corePath).toFile(),
                Utils.readContents(blobFile));
    }

    /**
     * Convert core path in relative form to user path in absolute form.
     * @param corePath .
     * @return
     */
    private Path userPath(String corePath) {
        return Paths.get(repoDirPath.getParent().toString(), corePath)
                .toAbsolutePath();
    }

    /**
     * convert a user file path to gitlet core path.
     * @param userPath .
     * @return
     */
    private Path corePath(Path userPath) {
        Path result =
                repoDirPath.getParent().relativize(userPath.toAbsolutePath());
        if (result.isAbsolute() || result.startsWith("..")) {
            throw Utils.error("%s is outside repository", result);
        }
        return result;
    }

    /**
     * convert a user file path to gitlet core path.
     * @param userPathString .
     * @return
     */
    private Path corePath(String userPathString) {
        return corePath(Paths.get(userPathString));
    }

    /**
     * get commit of given hint.
     * @param hint could be branch name or commit hash key prefix.
     * @return
     */
    private Commit getCommit(String hint) {
        if (hint == null) {
            return null;
        }

        if (getBranchNames().contains(hint)) {
            String key =
                    Utils.readContentsAsString(getBranchFile(hint)).strip();
            return getCommit(key);
        }

        List<String> commits = lookupCommits(hint);
        if (commits.size() != 1
                || !getCommitFile(commits.get(0)).exists()) {
            throw Utils.error("No commit with that id exists.");
        }

        return Utils.readObject(getCommitFile(commits.get(0)), Commit.class);
    }

    /**
     * get all commits.
     * @param prefixKey the prefix of a commit hash key
     * @return
     */
    private List<String> lookupCommits(String prefixKey) {
        List<String> commitsKeys =
                Utils.plainFilenamesIn(getPath("commits").toFile());
        List<String> result = new ArrayList<>();
        for (String commitKey : commitsKeys) {
            if (commitKey.startsWith(prefixKey)) {
                result.add(commitKey);
            }
        }
        return result;
    }

    /**
     * get commit file.
     * @param key commit hash key.
     * @return
     */
    private File getCommitFile(String key) {
        return getCommitPath(key).toFile();
    }

    /**
     * get commit file path.
     * @param key commit hash key.
     * @return
     */
    private Path getCommitPath(String key) {
        return getPath("commits", key);
    }

    /**
     * rm.
     * @param filePath the file path in user space.
     */
    public void rm(Path filePath) {
        validateRepo();
        Path relativePath = corePath(filePath);
        if (!theStage.isAdded(relativePath) && !theHead.hasFile(relativePath)) {
            throw Utils.error("No reason to remove the file.");
        }
        if (theStage.isAdded(relativePath)) {
            theStage.cancelAddition(relativePath, this);
        }
        if (theHead.hasFile(relativePath)) {
            theStage.markRemove(relativePath, this);

            if (filePath.toFile().exists()) {
                filePath.toFile().delete();
            }
        }
    }

    /**
     * create branch.
     * @param branchName .
     */
    public void createBranch(String branchName) {
        validateRepo();
        File branchFile = getBranchFile(branchName);
        if (branchFile.exists()) {
            throw Utils.error("A branch with that name already exists.");
        }
        setBranch(branchFile, theHead);
    }

    /**
     * rm-branch.
     * @param branchName .
     */
    public void removeBranch(String branchName) {
        validateRepo();
        File branchFile = getBranchFile(branchName);
        if (!branchFile.exists()) {
            throw Utils.error("A branch with that name does not exist.");
        }
        if (getCurrentBranchName().compareTo(branchName) == 0) {
            throw Utils.error("Cannot remove the current branch.");
        }
        branchFile.delete();
    }

    /**
     * reset.
     * @param commitKey .
     */
    public void reset(String commitKey) {
        validateRepo();
        if (!getCommitFile(commitKey).exists()) {
            throw Utils.error("No commit with that id exists.");
        }
        Commit targetCommit = getCommit(commitKey);
        Commit currentCommit = theHead;
        for (String coreFile : targetCommit.getFiles().keySet()) {
            if (!currentCommit.hasFile(coreFile)
                    && userPath(coreFile).toFile().exists()) {
                throw Utils.error(UNTRACKED_FILES_ERROR);
            }
        }
        checkoutCommit(currentCommit, targetCommit);
        setBranch(getBranchFile(getCurrentBranchName()), targetCommit);
    }

    /**
     * checkout commit based on given base commit.
     * @param from the base commit.
     * @param to the target commit.
     */
    private void checkoutCommit(Commit from, Commit to) {
        for (String corePath: from.getFiles().keySet()) {
            if (from.hasFile(corePath) && !to.hasFile(corePath)) {
                userPath(corePath).toFile().delete();
            }
        }
        for (String corePath : to.getFiles().keySet()) {
            checkoutFile(to, corePath);
        }
        cleanStage();
    }

    /**
     * clean stage.
     */
    private void cleanStage() {
        theStage = new Stage();
        saveStage(theStage);
    }

    /**
     * find commits.
     * @param commitMessage 。
     */
    public void find(String commitMessage) {
        validateRepo();
        List<String> commitsKeys =
                Utils.plainFilenamesIn(getPath("commits").toFile());
        boolean found = false;
        for (String commitKey : commitsKeys) {
            Commit currentCommit = getCommit(commitKey);
            if (currentCommit.getMessage().compareTo(commitMessage) == 0) {
                found = true;
                System.out.println(currentCommit.getKey());
            }
        }
        if (!found) {
            System.out.println("Found no commit with that message.");
        }
    }

    /**
     * merge a target branch.
     * @param otherBranchName the branch to merge.
     */
    public void merge(String otherBranchName) {
        validateRepo();
        if (equal(getCurrentBranchName(), otherBranchName)) {
            throw Utils.error("Cannot merge a branch with itself.");
        }

        if (!getBranchFile(otherBranchName).exists()) {
            throw Utils.error("A branch with that name does not exist.");
        }

        if (!theStage.getAddedFiles().isEmpty()
                || !theStage.getRemovedFiles().isEmpty()) {
            throw Utils.error("You have uncommitted changes.");
        }

        List<String> untrackedFiles = getUntrackedFiles(theStage, theHead);
        if (!untrackedFiles.isEmpty()) {
            throw Utils.error(UNTRACKED_FILES_ERROR);
        }

        List<String> myCommits = getCommits(getCurrentBranchName());
        List<String> otherCommits = getCommits(otherBranchName);
        String splitCommitKey = null;
        for (String commitKey : myCommits) {
            if (otherCommits.contains(commitKey)) {
                splitCommitKey = commitKey;
                break;
            }
        }

        Commit splitCommit = getCommit(splitCommitKey);
        Commit thisCommit = getCommit(getCurrentBranchName());
        Commit otherCommit = getCommit(otherBranchName);

        assert splitCommitKey != null;
        if (splitCommitKey.compareTo(otherCommits.get(0)) == 0) {
            Utils.message("Given branch is an ancestor of the current branch.");
            return;
        }
        if (splitCommitKey.compareTo(myCommits.get(0)) == 0) {
            Utils.message("Current branch fast-forwarded.");
            setBranch(getBranchFile(getCurrentBranchName()),
                    getCommit(otherBranchName));
            checkoutCommit(thisCommit, otherCommit);
            return;
        }
        mergeFiles(thisCommit, otherCommit, splitCommit);
        String commitMessage = "Merged "
                + otherBranchName + " into " + getCurrentBranchName() + ".";
        Commit mergeCommit =
                createCommit(commitMessage, System.currentTimeMillis(),
                        otherCommit);
        setBranch(getBranchFile(getCurrentBranchName()), mergeCommit);
    }

    /**
     * merge files.
     * @param thisCommit .
     * @param otherCommit .
     * @param splitCommit .
     */
    private void mergeFiles(Commit thisCommit,
                            Commit otherCommit,
                            Commit splitCommit) {
        Map<String, String> filesOfThisCommit = thisCommit.getFiles();
        Map<String, String> filesOfOtherCommit = otherCommit.getFiles();
        Map<String, String> filesOfSplitCommit = splitCommit.getFiles();

        Set<String> files = new HashSet<>();
        files.addAll(filesOfThisCommit.keySet());
        files.addAll(filesOfOtherCommit.keySet());

        for (String file : files) {
            String thisFileKey = filesOfThisCommit.get(file);
            String otherFileKey = filesOfOtherCommit.get(file);
            String splitFileKey = filesOfSplitCommit.get(file);
            if (equal(thisFileKey, otherFileKey)
                    || equal(splitFileKey, otherFileKey)) {
                continue;
            }
            if (equal(thisFileKey, splitFileKey)) {
                if (otherFileKey == null) {
                    theStage.markRemove(file, this);
                    File userFile = userPath(file).toFile();
                    if (userFile.exists()) {
                        userFile.delete();
                    }
                } else {
                    checkoutFile(otherCommit, file);
                    theStage.add(file, otherFileKey, this);
                }
            } else {
                Utils.message("Encountered a merge conflict.");
                String content = "<<<<<<< HEAD\n";
                content += thisFileKey == null ? ""
                        : Utils.readContentsAsString(getBlobFile(thisFileKey));
                content += "=======\n";
                content += otherFileKey == null ? ""
                        : Utils.readContentsAsString(getBlobFile(otherFileKey));
                content += ">>>>>>>\n";
                String blobKey =
                        Utils.sha1(content.getBytes(StandardCharsets.UTF_8));
                createBlob(getBlobFile(blobKey),
                        content.getBytes(StandardCharsets.UTF_8));
                theStage.add(file, blobKey, this);
                File blobFile = getBlobFile(blobKey);
                Utils.writeContents(userPath(file).toFile(),
                        Utils.readContents(blobFile));
            }
        }
    }

    /**
     * Get untracked files.
     * @param stage .
     * @param commit .
     * @return
     */
    private List<String> getUntrackedFiles(Stage stage, Commit commit) {
        List<String> files =
                Utils.plainFilenamesIn(repoDirPath.getParent().toFile());
        Set<String> tracked = commit.getFiles().keySet();
        Set<String> added = stage.getAddedFiles().keySet();
        List<String> removed = stage.getRemovedFiles();
        List<String> result = new ArrayList<>();
        for (String file : files) {
            String corePath = corePath(file).toString();
            if (!tracked.contains(corePath)
                    && !added.contains(corePath)
                    && !removed.contains(corePath)) {
                result.add(corePath);
            }
        }
        return result;
    }

    /**
     * Get all commits of given branch.
     * @param branchName .
     * @return
     */
    private List<String> getCommits(String branchName) {
        List<String> commitKeys = new ArrayList<>();
        Queue<Commit> queue = new ArrayDeque<>();
        Commit currentCommit = getCommit(branchName);
        if (currentCommit != null) {
            queue.add(currentCommit);
        }
        while (!queue.isEmpty()) {
            currentCommit = queue.remove();
            if (!commitKeys.contains(currentCommit.getKey())) {
                commitKeys.add(currentCommit.getKey());
                Commit p1Commit = getCommit(currentCommit.getParentKey());
                if (p1Commit != null) {
                    queue.add(p1Commit);
                }
                Commit p2Commit = getCommit(currentCommit.getParentKey2());
                if (p2Commit != null) {
                    queue.add(p2Commit);
                }
            }
        }
        return commitKeys;
    }

    /**
     * global-log.
     */
    public void globalLog() {
        List<String> commitsKeys =
                Utils.plainFilenamesIn(getPath("commits").toFile());
        for (String commitKey : commitsKeys) {
            System.out.println(getCommit(commitKey));
        }
    }
}

package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.RepoUtils.*;
import static gitlet.Utils.sha1;

public class StagingArea implements Serializable {

    private static final long serialVersionUID = 4449685098267757691L;
    private Map<String, String> files = new TreeMap<>();
    private Set<String> removal = new TreeSet<>();
    private Set<String> blobCanBeDeleted = new TreeSet<>();

    public Map<String, String> getFiles() {
        return files;
    }

    public void clean() {
        files = new TreeMap<>();
        removal = new TreeSet<>();
        blobCanBeDeleted = new TreeSet<>();
    }

    public boolean isEmpty() {
        return files.isEmpty() && removal.isEmpty();
    }

    public Set<String> getRemovalFiles() {
        return removal;
    }

    public Set<String> getFilenameSet() {
        return files.keySet();
    }

    public boolean willBeAddedOrModified(String name) {
        return this.files.containsKey(name);
    }

    public boolean contain(String name) {
        return this.files.containsKey(name) || this.removal.contains(name);
    }

    public void add(String fileName, Commit lastCommit) {
        File file = new File(fileName);
        if (!file.exists()) {
            Utils.message("File does not exist.");
            System.exit(0);
        } else {
            if (removal.contains(fileName)) {
                removal.remove(fileName);
            }

            String contents = Utils.readContentsAsString(file);
            String fileID = sha1(contents);
            if (fileID.equals(lastCommit.getFileID(fileName))) {
                delete(fileName);
            } else {
                files.put(fileName, fileID);
                List<String> blobs = Utils.plainFilenamesIn(GITLET_DIR + SLASH + "blobs");
                if (!blobs.contains(fileID)) {
                    blobCanBeDeleted.add(fileID);
                }
                writeBlob(contents, fileID);
            }
        }
    }

    public void delete(String fileName) {
        if (files.containsKey(fileName)) {
            String fileID = files.get(fileName);
            if (blobCanBeDeleted.contains(fileID)) {
                deleteBlob(fileID);
            }
            files.remove(fileName);
        }
    }

    public void addRemovedFiles(String fileName) {
        removal.add(fileName);
    }

    public void deleteRemovedFiles(String fileName) {
        removal.remove(fileName);
    }

    public void printStagedFiles() {
        Set<String> set = files.keySet();
        for (String name : set) {
            Utils.message(name);
        }
        Utils.message("");
    }

    public void printRemovedFiles() {
        for (String name : removal) {
            Utils.message(name);
        }
        Utils.message("");
    }

}

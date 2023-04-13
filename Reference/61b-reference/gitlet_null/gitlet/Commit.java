package gitlet;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author null
 */
public class Commit implements Serializable {

    /** Date format pattern used to generate the required date string. */
    public static final String DATE_PATTERN = "EEE MMM dd HH:mm:ss yyyy Z";

    /** internal error message. */
    public static final String ERROR_INTERNAL = "broken internal structure";

    /** commit message. */
    private String message;

    /** parent commit hash key. */
    private String parentKey;

    /** parent commit hash key 2. */
    private String parentKey2;

    /** commit time. */
    private long commitTime;

    /** files being tracked. */
    private Map<String, String> trackedFiles;

    /**
     * Construct a commit.
     * @param commitMessage is the commit message.
     * @param parent is the parent commit.
     */
    public Commit(String commitMessage, Commit parent) {
        this.message = commitMessage;
        this.trackedFiles = new HashMap<>();
        this.commitTime = 0;
        if (parent != null) {
            this.parentKey = parent.getKey();
            for (String filePath : parent.trackedFiles.keySet()) {
                trackedFiles.put(filePath, parent.trackedFiles.get(filePath));
            }
        }
    }

    /**
     * Construct a commit with provided information.
     * @param commitMessage is commit message
     * @param parentCommit is the parent commit
     * @param parentCommit2 is the 2nd parent of a merge commit
     */
    public Commit(String commitMessage,
                  Commit parentCommit, Commit parentCommit2) {
        this(commitMessage, parentCommit);
        this.parentKey2 = parentCommit2.getKey();
    }

    /**
     * Get hash key of this commit.
     * When this is called, nothing is expected to change after.
     * @return
     */
    public String getKey() {
        return Utils.sha1(Utils.serialize(this));
    }

    /**
     * Get parent commit hash key.
     * @return
     */
    public String getParentKey() {
        return parentKey;
    }

    /**
     * Get the second parent of a merge commit.
     * @return
     */
    public String getParentKey2() {
        return parentKey2;
    }

    @Override
    public String toString() {
        return "===\n"
                + "commit " + getKey() + '\n'
                + "Date: " + getDate() + '\n'
                + message + '\n';
    }

    /**
     * Get date time string.
     * @return
     */
    private String getDate() {
        Instant instantTime = Instant.ofEpochSecond(this.commitTime / 1000);
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(DATE_PATTERN);
        return ZonedDateTime.ofInstant(instantTime, ZoneId.systemDefault())
                .format(formatter);
    }

    /**
     * Get file blob hash key of given file in core path.
     * @param corePath of the file
     * @return
     */
    public String getFileBlobKey(String corePath) {
        return trackedFiles.get(corePath);
    }

    /**
     * Write out commit with the staged contents.
     * @param stage holds contents of change
     * @param currentTimeMillis the commit timestamp
     * @param gitlet knows how to write the commit into storage
     */
    public void commit(Stage stage, long currentTimeMillis, GitLet gitlet) {
        Map<String, String> filesToAdd = stage.getAddedFiles();
        for (String filePath : filesToAdd.keySet()) {
            trackedFiles.put(filePath, filesToAdd.get(filePath));
        }
        for (String filePath : stage.getRemovedFiles()) {
            assert trackedFiles.containsKey(filePath);
            trackedFiles.remove(filePath);
        }
        this.commitTime = currentTimeMillis;
        gitlet.commit(this);
    }

    /**
     * Check whether specific file is tracked.
     * @param corePath of the file
     * @return true if the file is tracked
     */
    public boolean hasFile(Path corePath) {
        boolean result = trackedFiles.containsKey(corePath.toString());
        if (result && trackedFiles.get(corePath.toString()) == null) {
            throw Utils.error(ERROR_INTERNAL);
        }
        return result;
    }

    /**
     * Check whether specific file is tracked.
     * @param corePath of the file
     * @return true if the file is tracked
     */
    public boolean hasFile(String corePath) {
        return hasFile(Paths.get(corePath));
    }

    /**
     * Get files being tracked.
     * @return
     */
    public Map<String, String> getFiles() {
        return trackedFiles;
    }

    /**
     * Get commit message.
     * @return
     */
    public String getMessage() {
        return message;
    }
}

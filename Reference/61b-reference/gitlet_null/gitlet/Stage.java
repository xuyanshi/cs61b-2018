package gitlet;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stage represent the files to be add / remove / change.
 * @author null
 */
public class Stage implements Serializable {

    /** files to add. */
    private Map<String, String> filesToAdd;
    /** files to remove. */
    private List<String> filesToRemove;

    /**
     * Construct a stage.
     */
    public Stage() {
        filesToAdd = new HashMap<>();
        filesToRemove = new ArrayList<>();
    }

    /**
     * Add a file.
     * @param corePath of the file.
     * @param blobKey of the file content blob.
     * @param gitlet knows!
     */
    public void add(String corePath, String blobKey, GitLet gitlet) {
        filesToAdd.put(corePath, blobKey);
        gitlet.saveStage(this);
    }

    /**
     * Add a file.
     * @param corePath of the file.
     * @param blobKey of the file content blob.
     * @param gitlet knows!
     */
    public void add(Path corePath, String blobKey, GitLet gitlet) {
        add(corePath.toString(), blobKey, gitlet);
    }

    /**
     * Get files to be added.
     * @return
     */
    public Map<String, String> getAddedFiles() {
        return filesToAdd;
    }

    /**
     * Clean up the stage.
     * @param gitlet knows!
     */
    public void clean(GitLet gitlet) {
        filesToAdd.clear();
        filesToRemove.clear();
        gitlet.saveStage(this);
    }

    /**
     * Check whether a file is added.
     * @param corePath of the file.
     * @return
     */
    public boolean isAdded(Path corePath) {
        return filesToAdd.containsKey(corePath.toString());
    }

    /**
     * Cancel the addition of a file.
     * @param corePath of the file.
     * @param gitlet knows everything.
     */
    public void cancelAddition(Path corePath, GitLet gitlet) {
        filesToAdd.remove(corePath.toString());
        gitlet.saveStage(this);
    }

    /**
     * Mark a file to remove.
     * @param corePath of the file.
     * @param gitlet knows how to save stage.
     */
    public void markRemove(String corePath, GitLet gitlet) {
        filesToRemove.add(corePath);
        gitlet.saveStage(this);
    }

    /**
     * Mark a file to remove.
     * @param corePath of the file.
     * @param gitlet knows how to save stage.
     */
    public void markRemove(Path corePath, GitLet gitlet) {
        markRemove(corePath.toString(), gitlet);
    }

    /**
     * Get the files to be removed.
     * @return
     */
    public List<String> getRemovedFiles() {
        return filesToRemove;
    }

    /**
     * Cancel remove of a file.
     * @param corePath of the file.
     * @param gitlet knows how to save stage physically.
     */
    public void cancelRemove(Path corePath, GitLet gitlet) {
        filesToRemove.remove(corePath.toString());
        gitlet.saveStage(this);
    }
}

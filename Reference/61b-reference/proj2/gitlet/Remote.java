package gitlet;

import java.io.Serializable;
/**
 * Represents a gitlet remote object.
 *
 * @author Christina0031
 */
public class Remote implements Serializable {
    private static final long serialVersionUID = 1119685098267757691L;
    private String directory;

    Remote(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return this.directory;
    }
}

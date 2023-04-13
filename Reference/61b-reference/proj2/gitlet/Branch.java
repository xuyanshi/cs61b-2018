package gitlet;

import java.io.Serializable;

/**
 * Represents a gitlet branch object.
 *
 * @author Christina0031
 */
public class Branch implements Serializable {

    private static final long serialVersionUID = 2229685098267757691L;

    private String commitID;
    private String name;
    private String pointTo;

    public Branch(String name, String commitID) {
        this.name = name;
        this.commitID = commitID;
    }

    public Branch(String pointTo) {
        this.name = "HEAD";
        this.pointTo = pointTo;
    }

    public void changeCommitTo(String dest) {
        this.commitID = dest;
    }

    public String getName() {
        return this.name;
    }

    public String getCommitID() {
        return this.commitID;
    }

    public String pointTo() {
        if (name.equals("HEAD")) {
            return pointTo;
        } else {
            return null;
        }
    }

    public void changePointTo(String dest) {
        if (name.equals("HEAD")) {
            pointTo = dest;
        }
    }
}

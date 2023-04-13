package gitlet;

import java.nio.file.Path;
import java.nio.file.Paths;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author null
 */
public class Main {

    /** repo dir name. */
    public static final String REPO_NAME = ".gitlet";

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }
        try {
            switch (args[0]) {
            case "init":
                cmdInit(args);
                break;
            case "add":
                cmdAdd(args);
                break;
            case "commit":
                cmdCommit(args);
                break;
            case "rm":
                cmdRm(args);
                break;
            case "log":
                cmdLog(args);
                break;
            case "global-log":
                cmdGlobalLog(args);
                break;
            case "find":
                cmdFind(args);
                break;
            case "status":
                cmdStatus(args);
                break;
            case "checkout":
                cmdCheckout(args);
                break;
            case "branch":
                cmdBranch(args);
                break;
            case "rm-branch":
                cmdRmbranch(args);
                break;
            case "reset":
                cmdReset(args);
                break;
            case "merge":
                cmdMerge(args);
                break;
            case "rm-remote": case "push": case "fetch": case "pull":
                break;
            case "--help": case "-h":
                showHelp();
                return;
            default:
                System.out.println("No command with that name exists.");
            }
        } catch (GitletException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * global-log.
     * @param args .
     */
    private static void cmdGlobalLog(String[] args) {
        if (args.length != 1) {
            System.out.println("Incorrect operands.");
            return;
        }
        getGitLet().globalLog();
    }

    /**
     * merge.
     * @param args .
     */
    private static void cmdMerge(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            return;
        }
        getGitLet().merge(args[1]);
    }

    /**
     * find.
     * @param args .
     */
    private static void cmdFind(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            return;
        }
        getGitLet().find(args[1]);
    }

    /**
     * reset.
     * @param args .
     */
    private static void cmdReset(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            return;
        }
        getGitLet().reset(args[1]);
    }

    /**
     * rm-branch.
     * @param args .
     */
    private static void cmdRmbranch(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            return;
        }
        getGitLet().removeBranch(args[1]);
    }

    /**
     * branch.
     * @param args .
     */
    private static void cmdBranch(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            return;
        }
        getGitLet().createBranch(args[1]);
    }

    /**
     * rm.
     * @param args .
     */
    private static void cmdRm(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            return;
        }
        getGitLet().rm(Paths.get(args[1]).toAbsolutePath());
    }

    /**
     * cmd checkout.
     * @param args args.
     */
    private static void cmdCheckout(String[] args) {
        getGitLet().checkout(args);
    }

    /**
     * cmd add.
     * @param args args.
     */
    private static void cmdAdd(String[] args) {
        getGitLet().add(args);
    }

    /**
     * cmd log.
     * @param args arguments.
     */
    private static void cmdLog(String[] args) {
        getGitLet().log();
    }

    /**
     * cmd status.
     * @param args arguments.
     */
    private static void cmdStatus(String[] args) {
        getGitLet().status();
    }

    /**
     * cmd commit.
     * @param args arguments
     */
    private static void cmdCommit(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            return;
        }
        getGitLet().commit(args[1]);
    }

    /**
     * command init.
     * @param args arguments.
     */
    private static void cmdInit(String... args) {
        if (args.length != 1) {
            System.out.println("Incorrect operands.");
            return;
        }
        GitLet gitlet = getGitLet();
        gitlet.init();
    }

    /**
     * get gitlet.
     * @return
     */
    private static GitLet getGitLet() {
        Path repoDirPath = Paths.get(".gitlet");
        return new GitLet(repoDirPath);
    }

    /**
     * show help.
     */
    private static void showHelp() {
        System.out.println("supported commands:");
        System.out.println("\tinit\t\tinit a repository at current location");
        System.out.println("\tadd\t\tadd files into stage");
        System.out.println("\tcommit\t\tcommit a staged change");
        System.out.println("\tlog\t\tlog commits");
        System.out.println("\tcheckout\tcheckout xxx");
        System.out.println("\t-h\t\tshow this message");
    }
}

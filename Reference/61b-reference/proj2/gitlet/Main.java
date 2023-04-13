package gitlet;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author Christina0031
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            Utils.message("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                validateNumArgs(args, 1, "Incorrect operands.");
                Repository.init();
                break;
            case "add":
                validateNumArgs(args, 2, "Incorrect operands.");
                Repository.add(args[1]);
                break;
            case "commit":
                validateNumArgs(args, 2, "Please enter a commit message.");
                Repository.commit(args[1]);
                break;
            case "rm":
                validateNumArgs(args, 2, "Incorrect operands.");
                Repository.rm(args[1]);
                break;
            case "log":
                validateNumArgs(args, 1, "Incorrect operands.");
                Repository.log();
                break;
            case "global-log":
                validateNumArgs(args, 1, "Incorrect operands.");
                Repository.globalLog();
                break;
            case "find":
                validateNumArgs(args, 2, "Incorrect operands.");
                Repository.find(args[1]);
                break;
            case "checkout":
                validateNumArgs(args, 2, 4, "Incorrect operands.");
                Repository.checkout(args);
                break;
            case "status":
                validateNumArgs(args, 1, "Incorrect operands.");
                Repository.status();
                break;
            case "branch":
                validateNumArgs(args, 2, "Incorrect operands.");
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                validateNumArgs(args, 2, "Incorrect operands.");
                Repository.rmBranch(args[1]);
                break;
            case "reset":
                validateNumArgs(args, 2, "Incorrect operands.");
                Repository.reset(args[1]);
                break;
            case "merge":
                validateNumArgs(args, 2, "Incorrect operands.");
                Repository.merge(args[1]);
                break;
            case "add-remote":
                validateNumArgs(args, 3, "Incorrect operands.");
                Repository.addRemote(args[1], args[2]);
                break;
            case "rm-remote":
                validateNumArgs(args, 2, "Incorrect operands.");
                Repository.rmRemote(args[1]);
                break;
            case "push":
                validateNumArgs(args, 3, "Incorrect operands.");
                Repository.push(args[1], args[2]);
                break;
            case "fetch":
                validateNumArgs(args, 3, "Incorrect operands.");
                Repository.fetch(args[1], args[2]);
                break;
            case "pull":
                validateNumArgs(args, 3, "Incorrect operands.");
                Repository.pull(args[1], args[2]);
                break;
            default:
                Utils.message("No command with that name exists.");
                System.exit(0);
        }
    }

    public static void validateNumArgs(String[] args, int n, String msg) {
        validateNumArgs(args, n, n, msg);
    }

    public static void validateNumArgs(String[] args, int low, int high, String msg) {
        if (args.length < low || args.length > high) {
            Utils.message(msg);
            System.exit(0);
        }
    }
}

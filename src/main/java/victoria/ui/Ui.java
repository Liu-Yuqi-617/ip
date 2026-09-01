package victoria.ui;

import victoria.exception.VictoriaException;
import victoria.model.TaskList;
import victoria.storage.TaskFile;

/** Handles all console output that is part of Victoria's user interface. */
public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";

    /** Prints the startup screen shown during a normal launch. */
    public void showWelcome() {
        printAnimatedLine("> Initializing VICTORIA...", 10);
        printAnimatedLine("> Loading task memory...", 10);
        printAnimatedLine("> Status: ONLINE", 10);
        System.out.println();
        printAnimatedLine(getBanner(), 1);
        printSeparator();
        System.out.println("Anything new today? I'm all ears!");
        printSeparator();
        printCommandFormat();
        printSeparator();
    }

    /** Returns the welcome text shown immediately in the graphical user interface. */
    public static String getGreetingMessage() {
        return getBanner()
                + HORIZONTAL_LINE + "\n"
                + "Anything new today? I'm all ears!\n"
                + HORIZONTAL_LINE + "\n"
                + getCommandFormat()
                + HORIZONTAL_LINE;
    }

    /** Prints the outcome of loading saved tasks. */
    public void showLoadResult(TaskFile.LoadResult result, TaskList tasks) {
        switch (result.status()) {
        case LOADED:
            System.out.println("Loaded tasks from disk:");
            tasks.printTasks();
            break;
        case NO_FILE:
            System.out.println("No saved task file found. Starting with an empty task list.");
            break;
        case EMPTY:
            System.out.println("The saved task file is empty. Starting with an empty task list.");
            break;
        case NO_VALID_RECORDS:
            System.out.println("No valid tasks found in the saved file. Starting with an empty task list.");
            break;
        case ERROR:
            System.out.println("Could not read the saved task file. Starting with an empty task list.");
            break;
        default:
            throw new IllegalStateException("Unknown load status: " + result.status());
        }
        printSeparator();
    }

    /** Prints the separator used between interactive commands. */
    public void printSeparator() {
        System.out.println(HORIZONTAL_LINE);
    }

    /** Prints a user-facing command error without exposing implementation details. */
    public void showError(VictoriaException exception) {
        System.out.println(" Oops! " + exception.getMessage());
    }

    /** Prints the message shown when the session ends. */
    public void showFarewell() {
        System.out.println("Bye! Always nice to chat with you. See you soon!");
        printSeparator();
    }

    /** Prints the command grammar shown before input is accepted. */
    private void printCommandFormat() {
        System.out.print(getCommandFormat());
    }

    /** Returns the command reference shown when Victoria starts. */
    private static String getCommandFormat() {
        return ">> AVAILABLE COMMANDS\n"
                + ">> todo <description>       CREATE A TASK\n"
                + ">> deadline <description> /by <date> (yyyy-MM-dd)\n"
                + ">> event <description> /from <date> /to <date> (yyyy-MM-dd)\n"
                + ">> list                     VIEW TASKS\n"
                + ">> list on <date> (yyyy-MM-dd) VIEW DEADLINES/EVENTS\n"
                + ">> find <keyword>          SEARCH TASK DESCRIPTIONS\n"
                + ">> mark <number>            COMPLETE TASK\n"
                + ">> unmark <number>          RESTORE TASK\n"
                + ">> delete <number>          REMOVE TASK\n\n"
                + ">> SYSTEM READY\n";
    }

    /** Returns Victoria's text banner. */
    private static String getBanner() {
        return "██╗   ██╗██╗ ██████╗████████╗ ██████╗ ██████╗ ██╗ █████╗ \n"
                + "██║   ██║██║██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗██║██╔══██╗\n"
                + "██║   ██║██║██║        ██║   ██║   ██║██████╔╝██║███████║\n"
                + "╚██╗ ██╔╝██║██║        ██║   ██║   ██║██╔══██╗██║██╔══██║\n"
                + " ╚████╔╝ ██║╚██████╗   ██║   ╚████╔╝██║  ██║██║██║██║  ██║\n"
                + "  ╚═══╝  ╚═╝ ╚═════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝\n";
    }

    /** Prints text one character at a time to create the startup animation. */
    private void printAnimatedLine(String text, long delayMilliseconds) {
        for (char character : text.toCharArray()) {
            System.out.print(character);
            System.out.flush();
            pause(delayMilliseconds);
        }
        System.out.println();
    }

    /** Pauses between animated characters without losing interruption status. */
    private void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}

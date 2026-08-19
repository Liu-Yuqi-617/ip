import java.util.Scanner;

/**
 * A simple command-line chatbot that stores and lists tasks until the user says bye.
 */
public class Victoria {
    private static final int MAX_TASKS = 100;
    private static final String HORIZONTAL_LINE = "____________________________________________________________";

    public static void main(String[] args) {
        String banner = "██╗   ██╗██╗ ██████╗████████╗ ██████╗ ██████╗ ██╗ █████╗ \n"
                + "██║   ██║██║██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗██║██╔══██╗\n"
                + "██║   ██║██║██║        ██║   ██║   ██║██████╔╝██║███████║\n"
                + "╚██╗ ██╔╝██║██║        ██║   ██║   ██║██╔══██╗██║██╔══██║\n"
                + " ╚████╔╝ ██║╚██████╗   ██║   ╚██████╔╝██║  ██║██║██║  ██║\n"
                + "  ╚═══╝  ╚═╝ ╚═════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝\n";
        String greeting = "Hi! I'm Victoria!";
        String prompt = "Anything new today? I'm all ears!";
        String farewell = "Bye! Always nice to chat with you. See you soon!";

        if (args.length == 0) {
            System.out.println(banner);
            System.out.println(HORIZONTAL_LINE);
            System.out.println(greeting);
            System.out.println(HORIZONTAL_LINE);
            System.out.println(prompt);
            System.out.println(HORIZONTAL_LINE);
            printCommandFormat();
            System.out.println(HORIZONTAL_LINE);
        }

        Scanner scanner = new Scanner(System.in);
        runCommandLoop(scanner, farewell, new TaskList(MAX_TASKS));
    }

    /**
     * Reads and echoes commands until the user enters the exit command "bye".
     *
     * @param scanner  source of commands entered by the user
     * @param farewell message printed before the program exits
     * @param tasks    in-memory storage for the user's tasks
     */
    private static void runCommandLoop(Scanner scanner, String farewell, TaskList tasks) {
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            String normalizedCommand = command.trim();

            if (normalizedCommand.equals("bye")) {
                System.out.println(farewell);
                System.out.println(HORIZONTAL_LINE);
                break;
            }

            if (normalizedCommand.equals("list")) {
                tasks.printTasks();
            } else if (normalizedCommand.startsWith("todo ")) {
                addTask(tasks, normalizedCommand.substring(5));
            } else if (normalizedCommand.startsWith("deadline ")) {
                addTimedTask(tasks, normalizedCommand.substring(9), false, "/by ");
            } else if (normalizedCommand.startsWith("event ")) {
                addTimedTask(tasks, normalizedCommand.substring(5), true, "/from ");
            } else if (isStatusCommand(normalizedCommand, "mark")) {
                changeTaskStatus(normalizedCommand, tasks, true);
            } else if (isStatusCommand(normalizedCommand, "unmark")) {
                changeTaskStatus(normalizedCommand, tasks, false);
            } else {
                System.out.println(" I couldn't understand that command. Please use one of the standard formats above.");
            }

            System.out.println(HORIZONTAL_LINE);
        }
    }

    /** Adds a task without date/time text. */
    private static void addTask(TaskList tasks, String description) {
        addParsedTask(tasks, new Todo(description));
    }

    /** Parses the slash-delimited date/time portion of a deadline or event command. */
    private static void addTimedTask(TaskList tasks, String command, boolean event, String marker) {
        int markerIndex = command.indexOf(marker);
        if (markerIndex < 1 || command.indexOf("/by ", markerIndex + marker.length()) >= 0) {
            System.out.println(" AHHH! I couldn't understand that task.");
            return;
        }
        String description = command.substring(0, markerIndex).trim();
        String dateTime = command.substring(markerIndex + marker.length()).trim();
        if (dateTime.isBlank()) {
            System.out.println(" AHHH! I couldn't understand that task.");
            return;
        }
        if (!event) {
            addParsedTask(tasks, new Deadline(description, dateTime));
            return;
        }
        int toIndex = dateTime.indexOf("/to ");
        if (toIndex <= 0 || dateTime.substring(toIndex + 4).isBlank()) {
            System.out.println(" AHHH! I couldn't understand that task.");
            return;
        }
        addParsedTask(tasks, new Event(description, dateTime.substring(0, toIndex).trim(),
                dateTime.substring(toIndex + 4).trim()));
    }

    /** Adds a parsed task and reports the result. */
    private static void addParsedTask(TaskList tasks, Task task) {
        if (tasks.add(task)) {
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + task);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        } else {
            System.out.println(" Task list is full or the description is empty.");
        }
    }

    /** Prints the command grammar shown to users before they enter commands. */
    private static void printCommandFormat() {
        System.out.println(" Standard command formats:");
        System.out.println("   todo <description>");
        System.out.println("   deadline <description> /by <date/time>");
        System.out.println("   event <description> /from <start> /to <end>");
        System.out.println("   list");
        System.out.println("   mark <number> | unmark <number> | bye");
    }

    /** Returns true only when the command has a status keyword followed by a number. */
    private static boolean isStatusCommand(String command, String commandName) {
        return command.matches("^" + commandName + "\\s+\\d+$");
    }

    /** Parses a validated status command and delegates the state change to the task list. */
    private static void changeTaskStatus(String command, TaskList tasks, boolean done) {
        String commandName = done ? "mark " : "unmark ";
        int taskNumber = Integer.parseInt(command.substring(commandName.length()).trim());
        Task task = tasks.getTask(taskNumber);
            if (task == null) {
                System.out.println(" Oops! Task number is invalid.");
            } else if (done && task.isDone()) {
                System.out.println(" This task is already done:");
                System.out.println("   " + task);
                System.out.println(" Go for other tasks!");
            } else if (!done && !task.isDone()) {
                System.out.println(" This task is already marked as not done:");
                System.out.println("   " + task);
                System.out.println(" Keep up! You can do this!");
            } else if (done) {
                tasks.markDone(taskNumber);
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + task);
            } else {
                tasks.markNotDone(taskNumber);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + task);
                System.out.println(" Keep up! You can do this!");
            }
    }

}

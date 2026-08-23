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
        String prompt = "Anything new today? I'm all ears!";
        String farewell = "Bye! Always nice to chat with you. See you soon!";

        if (args.length == 0) {
            printBootSequence(banner);
            System.out.println(HORIZONTAL_LINE);
            System.out.println(prompt);
            System.out.println(HORIZONTAL_LINE);
            printCommandFormat();
            System.out.println(HORIZONTAL_LINE);
        }

        Scanner scanner = new Scanner(System.in);
        TaskList tasks = new TaskList(MAX_TASKS);
        runCommandLoop(scanner, farewell, tasks);
    }

    /** Prints the cyber-style startup sequence before the command prompt. */
    private static void printBootSequence(String banner) {
        printAnimatedLine("> Initializing VICTORIA...", 10);
        printAnimatedLine("> Loading task memory...", 10);
        printAnimatedLine("> Status: ONLINE", 10);
        System.out.println();
        printAnimatedLine(banner, 1);
    }

    /** Prints text one character at a time to create a terminal startup effect. */
    private static void printAnimatedLine(String text, long delayMilliseconds) {
        for (char character : text.toCharArray()) {
            System.out.print(character);
            System.out.flush();
            pause(delayMilliseconds);
        }
        System.out.println();
    }

    /** Pauses animation briefly without preventing the application from starting. */
    private static void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
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

            try {
                if (normalizedCommand.equals("bye")) {
                    System.out.println(farewell);
                    System.out.println(HORIZONTAL_LINE);
                    break;
                }

                if (normalizedCommand.equals("list")) {
                    tasks.printTasks();
                } else if (normalizedCommand.equals("todo")) {
                    throw new EmptyDescriptionException("The description of a task cannot be empty.");
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
                } else if (isStatusCommand(normalizedCommand, "delete")) {
                    deleteTask(normalizedCommand, tasks);
                } else {
                    throw new InvalidCommandException(
                            "I don't recognize that command. Try a standard command format.");
                }

                TaskFile.save(tasks);

            } catch (VictoriaException exception) {
                System.out.println(" Oops! " + exception.getMessage());
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

        if (markerIndex < 0) {
            if (event) {
                throw new InvalidEventException("The event is missing /from <start>.");
            }
                throw new InvalidDeadlineException("The deadline is missing /by <date/time>.");
        }
        String description = command.substring(0, markerIndex).trim();
        if (description.isBlank()) {
            throw new EmptyDescriptionException("The description of this task cannot be empty.");
        }
        String dateTime = command.substring(markerIndex + marker.length()).trim();
        if (dateTime.isBlank()) {
            if (event) {
                throw new InvalidEventException("The event date/time cannot be empty.");
            }
            throw new InvalidDeadlineException("The deadline date/time cannot be empty.");
        }
        if (!event) {
            addParsedTask(tasks, new Deadline(description, dateTime));
            return;
        }
        int toIndex = dateTime.indexOf("/to ");
        if (toIndex < 0) {
            throw new InvalidEventException("The event is missing /to <end>.");
        }
        if (dateTime.substring(0, toIndex).trim().isBlank()) {
            throw new InvalidEventException("The event start time after /from cannot be empty.");
        }
        if (dateTime.substring(toIndex + 4).trim().isBlank()) {
            throw new InvalidEventException("The event end time after /to cannot be empty.");
        }
        addParsedTask(tasks, new Event(description, dateTime.substring(0, toIndex).trim(),
                dateTime.substring(toIndex + 4).trim()));
    }

    /** Adds a parsed task and reports the result. */
    private static void addParsedTask(TaskList tasks, Task task) {

        if (task.getDescription() == null || task.getDescription().isBlank()) {
            throw new EmptyDescriptionException("The description of a task cannot be empty.");
        }
        if (tasks.add(task)) {
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + task);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        } else {
            throw new TaskListFullException("The task list is full.");
        }
    }

    /** Prints the command grammar shown to users before they enter commands. */
    private static void printCommandFormat() {

        System.out.println(">> AVAILABLE COMMANDS");
        System.out.println(">> todo <description>       CREATE TASK");
        System.out.println(">> deadline ...             SET DEADLINE");
        System.out.println(">> event ...                CREATE EVENT");
        System.out.println(">> list                     VIEW TASKS");
        System.out.println(">> mark <number>            COMPLETE TASK");
        System.out.println(">> unmark <number>          RESTORE TASK");
        System.out.println(">> delete <number>          REMOVE TASK");
        System.out.println();
        System.out.println(">> SYSTEM READY");
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
                System.out.println(" Hurray! This task is already done:");
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

    /** Deletes a task selected by its one-based number and reports the new list size. */
    private static void deleteTask(String command, TaskList tasks) {
        int taskNumber = Integer.parseInt(command.substring("delete ".length()).trim());
        Task deletedTask = tasks.delete(taskNumber);
        if (deletedTask == null) {
            System.out.println(" Oops! Task number is invalid.");
            return;
        }
        System.out.println(" Yay! I've removed this task:");
        System.out.println("   " + deletedTask);
        System.out.println(" You now have " + tasks.size() + " tasks. Keep going!");
    }

}

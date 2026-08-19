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

        System.out.println(banner);
        System.out.println(HORIZONTAL_LINE);
        System.out.println(greeting);
        System.out.println(HORIZONTAL_LINE);
        System.out.println(prompt);
        System.out.println(HORIZONTAL_LINE);

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

            if (normalizedCommand.equalsIgnoreCase("bye")) {
                System.out.println(farewell);
                System.out.println(HORIZONTAL_LINE);
                break;
            }

            if (normalizedCommand.equalsIgnoreCase("list")) {
                tasks.printTasks();
            } else if (isStatusCommand(normalizedCommand, "mark")) {
                changeTaskStatus(normalizedCommand, tasks, true);
            } else if (isStatusCommand(normalizedCommand, "unmark")) {
                changeTaskStatus(normalizedCommand, tasks, false);
            } else if (tasks.add(normalizedCommand)) {
                System.out.println(" added: " + normalizedCommand);
            } else {
                System.out.println(" Task list is full.");
            }

            System.out.println(HORIZONTAL_LINE);
        }
    }

    /** Returns true only when the command has a status keyword followed by a number. */
    private static boolean isStatusCommand(String command, String commandName) {
        return command.matches("(?i)^" + commandName + "\\s+\\d+$");
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

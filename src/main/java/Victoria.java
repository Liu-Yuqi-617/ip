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
            } else if (normalizedCommand.toLowerCase().startsWith("mark ")) {
                markTask(normalizedCommand, tasks);
            } else if (tasks.add(normalizedCommand)) {
                System.out.println(" added: " + normalizedCommand);
            } else {
                System.out.println(" Task list is full.");
            }

            System.out.println(HORIZONTAL_LINE);
        }
    }

    /** Parses a mark command and delegates the state change to the task list. */
    private static void markTask(String command, TaskList tasks) {
        try {
            int taskNumber = Integer.parseInt(command.substring("mark ".length()).trim());
            String task = tasks.markDone(taskNumber);
            if (task == null) {
                System.out.println(" Task number is invalid.");
            } else {
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   [X] " + task);
            }
        } catch (NumberFormatException exception) {
            System.out.println(" Please enter a valid task number.");
        }
    }

    /**
     * Stores tasks in memory while keeping the storage details separate from command handling.
     */
    private static class TaskList {
        private final String[] tasks;
        private final boolean[] completed;
        private int taskCount;

        /**
         * Creates an empty task list with the given capacity.
         *
         * @param capacity maximum number of tasks that can be stored
         */
        TaskList(int capacity) {
            tasks = new String[capacity];
            completed = new boolean[capacity];
        }

        /**
         * Adds a task if there is still space in the list.
         *
         * @param task task text to store
         * @return true if the task was stored, otherwise false
         */
        boolean add(String task) {
            if (taskCount < tasks.length) {
                tasks[taskCount] = task;
                taskCount++;
                return true;
            }
            return false;
        }

        /** Marks the requested one-based task number as done and returns its text. */
        String markDone(int taskNumber) {
            int index = taskNumber - 1;
            if (index < 0 || index >= taskCount) {
                return null;
            }
            completed[index] = true;
            return tasks[index];
        }

        /** Prints all stored tasks using one-based numbering and completion markers. */
        void printTasks() {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                String marker = completed[i] ? "[X]" : "[ ]";
                System.out.println(" " + (i + 1) + "." + marker + " " + tasks[i]);
            }
        }
    }
}

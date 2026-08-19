import java.util.Scanner;

/**
 * A simple command-line chatbot that stores and lists tasks until the user says bye.
 */
public class Victoria {
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        String banner = "██╗   ██╗██╗ ██████╗████████╗ ██████╗ ██████╗ ██╗ █████╗ \n"
                + "██║   ██║██║██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗██║██╔══██╗\n"
                + "██║   ██║██║██║        ██║   ██║   ██║██████╔╝██║███████║\n"
                + "╚██╗ ██╔╝██║██║        ██║   ██║   ██║██╔══██╗██║██╔══██║\n"
                + " ╚████╔╝ ██║╚██████╗   ██║   ╚██████╔╝██║  ██║██║██║  ██║\n"
                + "  ╚═══╝  ╚═╝ ╚═════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝\n";
        String greeting = "Hi! I'm Victoria!";
        String h_line = "---------------------------------------------------------------";
        String prompt = "Anything new today? I'm all ears!";
        String farewell = "Bye! Always nice to chat with you. See you soon!";

        System.out.println(banner);
        System.out.println(h_line);
        System.out.println(greeting);
        System.out.println(h_line);
        System.out.println(prompt);
        System.out.println(h_line);

        Scanner scanner = new Scanner(System.in);
        runCommandLoop(scanner, h_line, farewell, new TaskList(MAX_TASKS));
    }

    /**
     * Reads and echoes commands until the user enters the exit command "bye".
     *
     * @param scanner  source of commands entered by the user
     * @param hLine    separator printed after each response
     * @param farewell message printed before the program exits
     * @param tasks    in-memory storage for the user's tasks
     */
    private static void runCommandLoop(Scanner scanner, String hLine, String farewell, TaskList tasks) {
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            String normalizedCommand = command.trim();

            if (normalizedCommand.equalsIgnoreCase("bye")) {
                System.out.println(farewell);
                System.out.println(hLine);
                break;
            }

            if (normalizedCommand.equalsIgnoreCase("list")) {
                tasks.printTasks();
            } else if (tasks.add(normalizedCommand)) {
                System.out.println(" added: " + normalizedCommand);
            } else {
                System.out.println(" Task list is full.");
            }

            System.out.println(hLine);
        }
    }

    /**
     * Stores tasks in memory while keeping the storage details separate from command handling.
     */
    private static class TaskList {
        private final String[] tasks;
        private int taskCount;

        /**
         * Creates an empty task list with the given capacity.
         *
         * @param capacity maximum number of tasks that can be stored
         */
        TaskList(int capacity) {
            tasks = new String[capacity];
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

        /** Prints all stored tasks using one-based numbering. */
        void printTasks() {
            for (int i = 0; i < taskCount; i++) {
                System.out.println(" " + (i + 1) + ". " + tasks[i]);
            }
        }
    }
}

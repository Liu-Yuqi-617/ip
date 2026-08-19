import java.util.Scanner;

/**
 * A simple command-line chatbot that echoes commands until the user says bye.
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
        runCommandLoop(scanner, h_line, farewell, new String[MAX_TASKS]);
    }

    /**
     * Reads and echoes commands until the user enters the exit command "bye".
     *
     * @param scanner  source of commands entered by the user
     * @param hLine    separator printed after each response
     * @param farewell message printed before the program exits
     * @param tasks    in-memory storage for the user's tasks
     */
    private static void runCommandLoop(Scanner scanner, String hLine, String farewell, String[] tasks) {
        int taskCount = 0;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            String normalizedCommand = command.trim();

            if (normalizedCommand.equalsIgnoreCase("bye")) {
                System.out.println(farewell);
                System.out.println(hLine);
                break;
            }

            if (normalizedCommand.equalsIgnoreCase("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
            } else if (taskCount < tasks.length) {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println(" added: " + command);
            }

            System.out.println(hLine);
        }
    }
}

import java.util.Scanner;

/**
 * A simple command-line chatbot that echoes commands until the user says bye.
 */
public class Victoria {
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
        runCommandLoop(scanner, h_line, farewell);
    }

    /**
     * Reads and echoes commands until the user enters the exit command "bye".
     *
     * @param scanner  source of commands entered by the user
     * @param hLine    separator printed after each response
     * @param farewell message printed before the program exits
     */
    private static void runCommandLoop(Scanner scanner, String hLine, String farewell) {
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            if (command.trim().equalsIgnoreCase("bye")) {
                System.out.println(farewell);
                System.out.println(hLine);
                break;
            }

            System.out.println(" " + command);
            System.out.println(hLine);
        }
    }
}

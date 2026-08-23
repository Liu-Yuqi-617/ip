import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/** Writes the task list to disk in a small, human-independent persistence format. */
public final class TaskFile {
    private static final Path FILE = Paths.get("data", "victoria.txt");

    private TaskFile() { }

    /** Saves the complete current list, creating the data directory when necessary. */
    public static void save(TaskList tasks) {
        try {
            Files.createDirectories(FILE.getParent());
            StringBuilder contents = new StringBuilder();
            for (int i = 1; i <= tasks.size(); i++) {
                contents.append(encode(tasks.getTask(i))).append(System.lineSeparator());
            }
            Files.writeString(FILE, contents.toString(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.err.println("Warning: Could not save tasks.");
        }
    }

    private static String encode(Task task) {
        String type;
        String[] extra;
        if (task instanceof Deadline deadline) {
            type = "D";
            extra = new String[] { deadline.getBy() };
        } else if (task instanceof Event event) {
            type = "E";
            extra = new String[] { event.getFrom(), event.getTo() };
        } else {
            type = "T";
            extra = new String[0];
        }
        StringBuilder result = new StringBuilder(type).append('|').append(task.isDone() ? '1' : '0')
                .append('|').append(encode(task.getDescription()));
        for (String value : extra) result.append('|').append(encode(value));
        return result.toString();
    }

    private static String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

}

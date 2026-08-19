/** Represents a task, its kind, optional date/time text, and completion status. */
public class Task {
    /** The three task forms supported by the command-line interface. */
    public enum Type { TODO, DEADLINE, EVENT }

    private final String description;
    private final Type type;
    private final String dateTime;
    private boolean done;

    /** Creates an unfinished task with the given description. */
    public Task(String description) {
        this(description, Type.TODO, "");
    }

    /** Creates an unfinished task with optional date/time text. */
    public Task(String description, Type type, String dateTime) {
        this.description = description;
        this.type = type;
        this.dateTime = dateTime;
    }

    /** Marks this task as done. */
    public void markDone() {
        done = true;
    }

    /** Marks this task as not done. */
    public void markNotDone() {
        done = false;
    }

    /** Returns whether this task has been completed. */
    public boolean isDone() {
        return done;
    }

    /** Returns the task kind. */
    public Type getType() {
        return type;
    }

    /** Returns the user-entered description. */
    public String getDescription() {
        return description;
    }

    /** Returns this task in the format used by the user interface. */
    @Override
    public String toString() {
        String prefix = switch (type) {
            case TODO -> "[T]";
            case DEADLINE -> "[D]";
            case EVENT -> "[E]";
        };
        String timing = switch (type) {
            case TODO -> "";
            case DEADLINE -> " (by: " + dateTime + ")";
            case EVENT -> " (" + dateTime + ")";
        };
        return prefix + (done ? "[X] " : "[ ] ") + description + timing;
    }
}

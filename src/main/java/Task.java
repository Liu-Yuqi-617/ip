/** Represents one task and its completion status. */
public class Task {
    private final String description;
    private boolean done;

    /** Creates an unfinished task with the given description. */
    public Task(String description) {
        this.description = description;
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

    /** Returns this task in the format used by the user interface. */
    @Override
    public String toString() {
        return (done ? "[X] " : "[ ] ") + description;
    }
}

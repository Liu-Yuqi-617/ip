/** A task without date or time information. */
public class Todo extends Task {
    /** Creates an unfinished ToDo. */
    public Todo(String description) {
        super(description);
    }

    @Override
    protected String getTypeMarker() {
        return "[T]";
    }

    @Override
    protected String getTimingText() {
        return "";
    }
}

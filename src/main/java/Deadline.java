/** A task that must be completed by a supplied date/time string. */
public class Deadline extends Task {
    private final String by;
    /** Creates an unfinished deadline. */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    protected String getTypeMarker() {
        return "[D]";
    }

    @Override
    protected String getTimingText() {
        return " (by: " + by + ")";
    }
}

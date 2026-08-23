/** A task with a supplied start and end date/time string. */
public class Event extends Task {
    private final String from;
    private final String to;
    /** Creates an unfinished event. */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /** Returns the event start text entered by the user. */
    public String getFrom() { return from; }

    /** Returns the event end text entered by the user. */
    public String getTo() { return to; }

    @Override
    protected String getTypeMarker() {
        return "[E]";
    }

    @Override
    protected String getTimingText() {
        return " (from: " + from + " to: " + to + ")";
    }
}

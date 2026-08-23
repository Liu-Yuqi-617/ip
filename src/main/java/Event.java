import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/** A task with supplied start and end dates. */
public class Event extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd uuuu", Locale.ENGLISH);
    private final LocalDate from;
    private final LocalDate to;
    /** Creates an unfinished event. */
    public Event(String description, String from, String to) {
        super(description);
        this.from = parseDate(from);
        this.to = parseDate(to);
    }

    /** Returns the event start text entered by the user. */
    public String getFrom() { return from.toString(); }

    /** Returns the event end text entered by the user. */
    public String getTo() { return to.toString(); }

    private static LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value, DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        } catch (DateTimeParseException | NullPointerException exception) {
            throw new InvalidEventException("Event dates must use yyyy-MM-dd.");
        }
    }

    @Override
    protected String getTypeMarker() {
        return "[E]";
    }

    @Override
    protected String getTimingText() {
        return " (from: " + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}

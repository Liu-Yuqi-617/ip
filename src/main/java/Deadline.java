import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/** A task that must be completed by a supplied date. */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd uuuu", Locale.ENGLISH);
    private final LocalDate by;

    /** Creates an unfinished deadline. */
    public Deadline(String description, String by) {
        this(description, parseDate(by));
    }

    /** Creates an unfinished deadline at the supplied date. */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /** Returns the deadline as a machine-readable value for persistence. */
    public String getBy() { return by.toString(); }

    private static LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value, DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        } catch (DateTimeParseException | NullPointerException exception) {
            throw new InvalidDeadlineException("The deadline must use yyyy-MM-dd.");
        }
    }

    @Override
    protected String getTypeMarker() {
        return "[D]";
    }

    @Override
    protected String getTimingText() {
        return " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

/** A task that must be completed by a supplied date and time. */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm"))
            .appendOptional(DateTimeFormatter.ofPattern("d/M/uuuu HHmm"))
            .toFormatter();
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd uuuu HHmm", Locale.ENGLISH);
    private final LocalDateTime by;

    /** Creates an unfinished deadline. */
    public Deadline(String description, String by) {
        this(description, parseDateTime(by));
    }

    /** Creates an unfinished deadline at the supplied date and time. */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /** Returns the deadline as a machine-readable value for persistence. */
    public String getBy() { return by.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm")); }

    private static LocalDateTime parseDateTime(String value) {
        try {
            if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return LocalDate.parse(value, DateTimeFormatter.ofPattern("uuuu-MM-dd")).atStartOfDay();
            }
            return LocalDateTime.parse(value, INPUT_FORMAT);
        } catch (DateTimeParseException | NullPointerException exception) {
            throw new InvalidDeadlineException("The deadline must use yyyy-MM-dd or d/M/yyyy HHmm.");
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

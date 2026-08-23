/** Represents one executable user command. */
public abstract class Command {
    /** Executes this command against the current application state. */
    public abstract void execute(TaskList tasks, Ui ui);

    /** Returns whether executing this command should end the application. */
    public boolean isExit() {
        return false;
    }
}

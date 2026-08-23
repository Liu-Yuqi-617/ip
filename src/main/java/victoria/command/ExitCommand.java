package victoria.command;

import victoria.model.TaskList;
import victoria.ui.Ui;

/** A command that ends the interactive session. */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.showFarewell();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

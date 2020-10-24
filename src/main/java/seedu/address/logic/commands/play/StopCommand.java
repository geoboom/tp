package seedu.address.logic.commands.play;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.view.View;


public class StopCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Playmode Stopped! ";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String score = model.endGame();
        model.setCurrentView(View.SCORE_VIEW);
        return new CommandResult(MESSAGE_SUCCESS + score);
    }
}

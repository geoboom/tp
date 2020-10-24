package seedu.address.logic.commands.entry;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRIES;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.view.View;

/**
 * Lists all entries in the word bank to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all entries";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getCurrentDeck() == null) {
            throw new CommandException(Messages.MESSAGE_NO_DECK_SELECTED);
        }
        model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
        model.setCurrentView(View.ENTRY_VIEW);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

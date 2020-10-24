package seedu.address.logic.commands.deck.entry;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.deck.TypicalDecks.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.entry.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noDeckSelected_throwsCommandException() {
        ListCommand listCommand = new ListCommand();
        assertThrows(CommandException.class, Messages.MESSAGE_NO_DECK_SELECTED, () -> listCommand.execute(model));
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        model.selectDeck(Index.fromZeroBased(0));
        expectedModel.selectDeck(Index.fromZeroBased(0));
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }


    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        model.selectDeck(Index.fromZeroBased(0));
        expectedModel.selectDeck(Index.fromZeroBased(0));
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

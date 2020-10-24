package seedu.address.logic.commands.deck.entry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JAPANESE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SPANISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRANSLATION_JAPANESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRANSLATION_SPANISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORD_JAPANESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORD_SPANISH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.entry.EditCommand;
import seedu.address.logic.commands.entry.EditCommand.EditEntryDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.DeckName;
import seedu.address.model.deck.entry.Entry;
import seedu.address.model.deck.entry.Translation;
import seedu.address.model.deck.entry.Word;
import seedu.address.testutil.entry.EditEntryDescriptorBuilder;
import seedu.address.testutil.entry.EntryBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        Deck deck = new Deck(new DeckName("Stub"));
        Entry entry = new EntryBuilder(new Entry (new Word(VALID_WORD_JAPANESE),
                new Translation(VALID_TRANSLATION_JAPANESE))).build();
        Entry secondEntry = new EntryBuilder(new Entry(new Word(VALID_WORD_SPANISH),
                new Translation(VALID_TRANSLATION_SPANISH))).build();
        model.addDeck(deck);
        model.selectDeck(INDEX_FIRST);
        model.addEntry(entry);
        model.addEntry(secondEntry);
    }

    @Test
    public void execute_duplicateEntryUnfilteredList_failure() {
        Entry firstEntry = model.getFilteredEntryList().get(INDEX_FIRST.getZeroBased());
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder(firstEntry).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND, descriptor);
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ENTRY);
    }

    @Test
    public void execute_duplicateEntryFilteredList_failure() {
        // edit entry in filtered list into a duplicate in address book
        model.selectDeck(INDEX_FIRST);
        Entry entryInList = model.getFilteredEntryList().get(INDEX_SECOND.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
                new EditEntryDescriptorBuilder(entryInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ENTRY);
    }

    @Test
    public void execute_invalidEntryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEntryList().size() + 1);
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder().withWord(VALID_WORD_SPANISH).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST, DESC_JAPANESE);

        // same values -> returns true
        EditEntryDescriptor copyDescriptor = new EditEntryDescriptor(DESC_JAPANESE);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND, DESC_JAPANESE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST, DESC_SPANISH)));
    }
}

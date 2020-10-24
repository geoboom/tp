package seedu.address.logic.commands.deck.entry;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.entry.AddCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.DeckName;
import seedu.address.model.deck.entry.Entry;
import seedu.address.model.play.Leitner;
import seedu.address.model.view.View;
import seedu.address.testutil.entry.EntryBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_entryAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEntryAdded modelStub = new ModelStubAcceptingEntryAdded();
        Entry validEntry = new EntryBuilder().build();
        CommandResult commandResult = new AddCommand(validEntry).execute(modelStub);
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validEntry), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEntry), modelStub.entriesAdded);
    }

    @Test
    public void execute_duplicateEntry_throwsCommandException() {
        Entry validEntry = new EntryBuilder().build();
        AddCommand addCommand = new AddCommand(validEntry);
        ModelStub modelStub = new ModelStubWithEntry(validEntry);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_ENTRY, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Entry alice = new EntryBuilder().withWord("Alice").build();
        Entry bob = new EntryBuilder().withWord("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different entry -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEntry(Entry entry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEntry(Entry entry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEntry(Entry target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEntry(Entry target, Entry editedEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Entry> getFilteredEntryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEntryList(Predicate<Entry> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeDeck(Deck target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void selectDeck(Index index) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Deck getCurrentDeck() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Deck> getFilteredDeckList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDeckList(Predicate<Deck> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void replaceEntryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearEntryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void newGame() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String endGame() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void playGame(String answer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkScore() {
            return true;
        }

        @Override
        public boolean checkScoreTwo() {
            return true;
        }

        @Override
        public int editDistance(String answer, String correctAnswer, int answerLength, int correctAnswerLength) {
            return 10;
        }

        @Override
        public void setCurrentView(View view) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public View getCurrentView() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Leitner getLeitner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getCurrentIndex() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getLastScore() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Deck stub which accepts all entries
     */
    private class DeckStub extends Deck {
        private ArrayList<Entry> entries;

        DeckStub(String deckName) {
            super(new DeckName(deckName));
            this.entries = new ArrayList<>();
        }

        public void add(Entry entry) {
            entries.add(entry);
        }
    }

    /**
     * A Model stub that contains a single entry.
     */
    private class ModelStubWithEntry extends ModelStub {
        private final Entry entry;

        ModelStubWithEntry(Entry entry) {
            requireNonNull(entry);
            this.entry = entry;
        }

        @Override
        public boolean hasEntry(Entry entry) {
            requireNonNull(entry);
            return this.entry.isSameEntry(entry);
        }

        @Override
        public Deck getCurrentDeck() {
            return new DeckStub("stub");
        }
    }

    /**
     * A Model stub that always accept the entry being added.
     */
    private class ModelStubAcceptingEntryAdded extends ModelStub {
        final ArrayList<Entry> entriesAdded = new ArrayList<>();

        @Override
        public boolean hasEntry(Entry entry) {
            requireNonNull(entry);
            return entriesAdded.stream().anyMatch(entry::isSameEntry);
        }

        @Override
        public void addEntry(Entry entry) {
            requireNonNull(entry);
            entriesAdded.add(entry);
        }

        @Override
        public Deck getCurrentDeck() {
            return new DeckStub("stub");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public void replaceEntryList() {
        }

        @Override
        public void newGame() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String endGame() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void playGame(String answer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentView(View view) {}
    }
}

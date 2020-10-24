package seedu.address.logic.commands.deck;

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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.entry.Entry;
import seedu.address.model.play.Leitner;
import seedu.address.model.view.View;
import seedu.address.testutil.deck.DeckBuilder;

public class NewDeckCommandTest {

    @Test
    public void constructor_nullDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NewDeckCommand(null));
    }

    @Test
    public void execute_deckAcceptedByModel_addSuccessful() throws Exception {
        NewDeckCommandTest.ModelStubAcceptingDeckAdded modelStub = new NewDeckCommandTest.ModelStubAcceptingDeckAdded();
        Deck validDeck = new DeckBuilder().build();

        CommandResult commandResult = new NewDeckCommand(validDeck).execute(modelStub);

        assertEquals(String.format(NewDeckCommand.MESSAGE_SUCCESS, validDeck), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validDeck), modelStub.decksAdded);
    }

    @Test
    public void execute_duplicateDeck_throwsCommandException() {
        Deck validDeck = new DeckBuilder().build();
        NewDeckCommand newDeckCommand = new NewDeckCommand(validDeck);
        NewDeckCommandTest.ModelStub modelStub = new NewDeckCommandTest.ModelStubWithDeck(validDeck);

        assertThrows(CommandException.class, NewDeckCommand.MESSAGE_DUPLICATE_DECK, () ->
                newDeckCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Deck japanese = new DeckBuilder().withDeckName("Japanese").build();
        Deck spanish = new DeckBuilder().withDeckName("Spanish").build();
        NewDeckCommand addJapaneseCommand = new NewDeckCommand(japanese);
        NewDeckCommand addSpanishCommand = new NewDeckCommand(spanish);

        // same object -> returns true
        assertTrue(addJapaneseCommand.equals(addJapaneseCommand));

        // same values -> returns true
        NewDeckCommand addJapaneseCommandCopy = new NewDeckCommand(japanese);
        assertTrue(addJapaneseCommand.equals(addJapaneseCommandCopy));

        // different types -> returns false
        assertFalse(addJapaneseCommand.equals(1));

        // null -> returns false
        assertFalse(addJapaneseCommand.equals(null));

        // different deck -> returns false
        assertFalse(addJapaneseCommand.equals(addSpanishCommand));
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
     * A Model stub that contains a single Deck.
     */
    private class ModelStubWithDeck extends NewDeckCommandTest.ModelStub {
        private final Deck deck;

        ModelStubWithDeck(Deck deck) {
            requireNonNull(deck);
            this.deck = deck;
        }

        @Override
        public boolean hasDeck(Deck deck) {
            requireNonNull(deck);
            return this.deck.isSameDeck(deck);
        }
    }

    /**
     * A Model stub that always accept the deck being added.
     */
    private class ModelStubAcceptingDeckAdded extends NewDeckCommandTest.ModelStub {
        final ArrayList<Deck> decksAdded = new ArrayList<>();

        @Override
        public boolean hasDeck(Deck deck) {
            requireNonNull(deck);
            return decksAdded.stream().anyMatch(deck::isSameDeck);
        }

        @Override
        public void addDeck(Deck deck) {
            requireNonNull(deck);
            decksAdded.add(deck);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}

package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.entry.Entry;
import seedu.address.model.deck.entry.UniqueEntryList;
import seedu.address.model.play.Leitner;
import seedu.address.model.view.CurrentView;
import seedu.address.model.view.View;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private CurrentView currentView;
    /*private final FilteredList<Entry> filteredEntries;*/
    private final FilteredList<Deck> filteredDecks;
    private Optional<Index> currentDeckIndex;
    private Deck observedDeck;
    private Leitner leitner;
    private int quizLength = 2;
    private int currentIndex = 0;
    private int lastScore = 0;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        /*filteredEntries = new FilteredList<>(this.addressBook.getEntryList());*/
        filteredDecks = new FilteredList<>(this.addressBook.getDeckList());
        currentDeckIndex = Optional.empty();
        this.currentView = new CurrentView(View.START_VIEW);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }
    //=========== Current View =============================================================================

    @Override
    public void setCurrentView(View view) {
        this.currentView.setView(view);
    }

    @Override
    public View getCurrentView() {
        return this.currentView.getView();
    }
    //=========== Word Bank ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        Deck currentDeck = getCurrentDeck();
        return currentDeck.hasEntry(entry);
    }

    @Override
    public void deleteEntry(Entry target) {
        requireNonNull(target);
        Deck currentDeck = getCurrentDeck();
        currentDeck.removeEntry(target);
        addressBook.getObservedEntries().remove(target);
    }

    /**
     * This function takes the entry and adds it to the deck entry list as well as the observedEntries in the
     * AddressBook
     *
     * @param entry refers to the entry inputted by the user
     */

    @Override
    public void addEntry(Entry entry) {
        requireNonNull(entry);
        Deck currentDeck = getCurrentDeck();
        currentDeck.addEntry(entry);
        addressBook.getObservedEntries().add(entry);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);

        Deck currentDeck = getCurrentDeck();
        currentDeck.setEntry(target, editedEntry);
    }

    @Override
    public boolean hasDeck(Deck deck) {
        requireNonNull(deck);
        return addressBook.hasDeck(deck);
    }

    @Override
    public void removeDeck(Deck target) {
        addressBook.removeDeck(target);
    }


    @Override
    public void addDeck(Deck deck) {
        addressBook.addDeck(deck);
        updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
    }

    @Override
    public void selectDeck(Index index) {
        currentDeckIndex = Optional.of(index);
    }

    /**
     * This function deletes what is on the GUI and replaces it with the next entries in the selected deck.
     * To replace the observedEntry in Addressbook.java that controls the GUI, a copy of it has to be created first.
     * This avoids the concurrent modification exception.
     */
    @Override
    public void replaceEntryList() {
        UniqueEntryList newEntryList = getCurrentDeck().getEntries();
        addressBook.resetEntryList();
        addressBook.replaceEntryList(newEntryList);
    }

    @Override
    public void clearEntryList() {
        addressBook.resetEntryList();
    }


    @Override
    public Deck getCurrentDeck() {
        if (currentDeckIndex.equals(Optional.empty())) {
            logger.info("Current deck index is 0");
            return null;
        }
        return filteredDecks.get(currentDeckIndex.get().getZeroBased());
    }

    //=========== Filtered Entry List Accessors =============================================================

    /**
     * Returns a default deck as memory is not fixed yet. During initialisation, the observedEntryList value is
     * passed as the AddressBook.javas uniqueEntryList. I.e the GUI now watches for any changes in the AddressBook,java
     * field observedEntries
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Entry> getFilteredEntryList() {
        if (this.getCurrentDeck() == null) {
            logger.info("Current Deck is null");
            return addressBook.getFilteredEntries();
        }
        return addressBook.getFilteredEntries();
    }

    @Override
    public void updateFilteredEntryList(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        Deck currentDeck = getCurrentDeck();
        currentDeck.updateFilteredEntryList(predicate);
        addressBook.resetEntryList();
        addressBook.replaceEntryList(currentDeck.getEntries());
    }

    /**
     * Returns an unmodifiable view of the list of {@code Deck} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Deck> getFilteredDeckList() {
        return filteredDecks;
    }

    @Override
    public void updateFilteredDeckList(Predicate<Deck> predicate) {
        requireNonNull(predicate);
        filteredDecks.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredDecks.equals(other.filteredDecks);
    }

    //====Games=====

    /**
     * Initializes a new flashcard game by creating a new Leitner object. If memory has records of the deck being
     * played before, the Leitner object can be created using the entry list from memory (still implementing). If
     * memory has no records of the deck being played, the Leitner object is created using a random shuffle of the
     * exisiting entries.
     */
    @Override
    public void newGame() {
        UniqueEntryList observedList = getCurrentDeck().getEntries(); //get selected deck
        leitner = new Leitner(observedList); //use exisiting deck or from memory
        quizLength = leitner.getEntries().size();
        currentIndex = 0;
        addressBook.resetEntryList();
        addressBook.replaceEntryList(leitner.getUniqueEntryList());
    }

    /**
     * Gets called by either a PlayCommand or a StopCommand. Replaces the current observed entry list in addressbook
     * by the original entries. Score is saved from the leitner object. The next quiz is also generated from the
     * Leitner object and can be stored in memory. The UI is updated with the new score and the Leitner field of
     * ModelManager is deleted.
     */
    @Override
    public String endGame() {
        replaceEntryList();
        String score = leitner.getScore();
        lastScore = leitner.getScoreValue();

        leitner.organizeQuizNextAttempt();
        ArrayList<Entry> nextQuiz = leitner.getQuizNextAttempt(); //save this to memory

        this.currentView.setView(View.SCORE_VIEW);

        leitner = null; //delete leitner
        return score;
    }

    /**
     * Initiated from the PlayCommand. Takes in a String answerGiven from the user via the UI and checks if the
     * answer is a correct, close to correct, or wrong.
     * The edit distance between the correctAnswer and answerGiven is calculated via withinEditDistance and if the
     * distance is 1, the answerGiven is considered almost correct.
     * If the answer is correct, the score is incremented in the
     * Leitner object and the correctAnsweredEntries field in leitner object is updated with the entry. The same
     * occurs if the answer is correct except a different message is logged.
     * If the answer is wrong, the score is not incremented and the wrongAnsweredEntries field in leitner object is
     * updated
     * The entry is swapped with the correct entry in addressbook and the currentIndex is incremented.
     *
     * @param answerGiven refers to the user input
     */
    @Override
    public void playGame(String answerGiven) {
        String correctAnswer = leitner.getAnswers().get(currentIndex).toString();
        Entry entryToAdd = leitner.getEntries().get(currentIndex);
        Entry entryToRemove = addressBook.getObservedEntries().get(currentIndex);
        int numEditDistance = editDistance(answerGiven, correctAnswer,
                answerGiven.length(), correctAnswer.length());
        boolean isWithinEditDistance = numEditDistance == 1; //editDistance can only be maximum 1

        if (currentIndex == quizLength) {
            replaceEntryList();
        } else if (answerGiven.equals(correctAnswer)) {
            leitner.correctAnswered(entryToAdd);
            leitner.incrementScore();
            logger.info(String.format("Answer given was %s, the correct answer was %s, Correct answer given",
                    answerGiven, correctAnswer));
        } else if (isWithinEditDistance) {
            leitner.correctAnswered(entryToAdd);
            leitner.incrementScore();
            logger.info(String.format("within edit distance of %s, Correct answer given", numEditDistance));
        } else {
            leitner.wrongAnswered(entryToAdd);
            logger.info(String.format("Answer given was %s, the correct answer was %s, Wrong answer given",
                    answerGiven, correctAnswer));
        }

        addressBook.setEntry(entryToRemove, entryToAdd); //swaps entry in GUI
        currentIndex++;
    }

    @Override
    public Leitner getLeitner() {
        return this.leitner;
    }

    @Override
    public int getCurrentIndex() {
        return this.currentIndex;
    }

    @Override
    public boolean checkScore() {
        return currentIndex == quizLength - 1;
    }

    @Override
    public boolean checkScoreTwo() {
        return currentIndex == quizLength;
    }

    @Override
    public int getLastScore() {
        return this.lastScore;
    }

    /**
     * A dynamic program used to calculate edit distance between two strings
     */
    @Override
    public int editDistance(String answer, String correctAnswer, int m, int n) {
        assert (m < Integer.MAX_VALUE && n < Integer.MAX_VALUE);
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (answer.charAt(i - 1) == correctAnswer.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1]));
                }
            }
        }
        return dp[m][n];

    }

    //====EndGames====
}

package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.DeckName;
import seedu.address.model.deck.entry.Entry;
import seedu.address.model.deck.entry.Translation;
import seedu.address.model.deck.entry.UniqueEntryList;
import seedu.address.model.deck.entry.Word;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    /*private final FilteredList<Entry> filteredEntries;*/
    private final FilteredList<Deck> filteredDecks;
    private Optional<Index> currentDeckIndex;

    private Deck observedDeck;


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
        Deck currentDeck = getCurrentDeck();
        currentDeck.removeEntry(target);
    }

    @Override
    public void addEntry(Entry entry) {
        Deck currentDeck = getCurrentDeck();
        currentDeck.addEntry(entry);
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
        //select command will also call  replaceEntryList() below
    }

    @Override
    public void replaceEntryList() {
        UniqueEntryList observedList = observedDeck.getEntries();
        for (Entry entry : observedList) {
            addressBook.entries.add(entry);
        }
        //addressBook.setEntries(observedList);
    }


    @Override
    public Deck getCurrentDeck() {
        if (currentDeckIndex.equals(Optional.empty())) {
            logger.info("currentDeckIndex is 0");
            return null;
        }
        return filteredDecks.get(currentDeckIndex.get().getZeroBased());
    }

    //=========== Filtered Entry List Accessors =============================================================

    /**
     * Returns a default deck as memory is not fixed yet.
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Entry> getFilteredEntryList() {
        //keeps returning null causing null error
        if (this.getCurrentDeck() == null) {
            logger.info("Current Deck is null");
            observedDeck = new Deck(new DeckName("Japanese(Built In Stub)"));
            observedDeck.addEntry(new Entry(new Word("Hello"), new Translation("こんにちは")));
            observedDeck.addEntry(new Entry(new Word("Goodbye"), new Translation("さようなら")));
            observedDeck.addEntry(new Entry(new Word("Software"), new Translation("ソフトウェア")));
            observedDeck.addEntry(new Entry(new Word("Engineering"), new Translation("エンジニアリング")));
            observedDeck.addEntry(new Entry(new Word("This is"), new Translation("a stub btw")));
            addressBook.addDeck(observedDeck);
            selectDeck(new Index(0));
            //return getCurrentDeck().getFilteredEntryList();
            return addressBook.filteredEntries;
            //return null;
        }
        Deck currentDeck = getCurrentDeck();
        System.out.println(currentDeck.getDeckName());
        System.out.println(currentDeck.getEntryList());
        return getCurrentDeck().getFilteredEntryList();
    }

    @Override
    public void updateFilteredEntryList(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        Deck currentDeck = getCurrentDeck();
        currentDeck.updateFilteredEntryList(predicate);

    }

    /**
     * Returns an unmodifiable view of the list of {@code Deck} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Deck> getFilteredDeckList() {
        /*UniqueDeckList udl = new UniqueDeckList();
        udl.add(new Deck(new DeckName("deck_1")));
        udl.add(new Deck(new DeckName("deck_2")));
        udl.add(new Deck(new DeckName("deck_3")));
        return udl.asUnmodifiableObservableList();*/
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
}

package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.PlayModeParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.phase.PhaseManager;
import seedu.address.logic.phase.exceptions.PhaseInvalidException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.entry.Entry;
import seedu.address.model.play.Leitner;
import seedu.address.model.view.View;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final PhaseManager phaseManager;
    private final AddressBookParser addressBookParser;
    private final PlayModeParser playModeParser;
    private PlayMode playMode = new PlayMode();

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.phaseManager = new PhaseManager();
        addressBookParser = new AddressBookParser();
        playModeParser = new PlayModeParser();
    }

    @Override
    public CommandResult execute(String commandText)
        throws CommandException, ParseException, PhaseInvalidException {
        logger.info(String.format("----------------[USER COMMAND][%s]", commandText));

        // Command command = addressBookParser.parseCommand(commandText);

        // bubble error up to UI
        // if (!phaseManager.commandAllowed(command)) {
        //     throw new PhaseInvalidException(String
        //         .format("[%s] command not allowed in phase [%s]", command.getClass().toString(),
        //             phaseManager.getPhase()));
        // }

        CommandResult commandResult;
        Command command;

        if (commandText.equals("play")) {
            assert (!playMode.isPlayMode());
            if (model.getCurrentDeck() == null) {
                throw new CommandException(Messages.MESSAGE_NO_DECK_SELECTED);
            }
            if (model.getCurrentDeck().getEntries().isEmpty()) {
                throw new CommandException(Messages.MESSAGE_EMPTY_DECK);
            }
            playMode.turnOn();
        }

        if (playMode.isPlayMode()) {
            assert (playMode.isPlayMode());
            command = playModeParser.parseCommand(commandText);
            if (commandText.equals("stop") || model.checkScore()) {
                playMode.turnOff();
            }
        } else {
            command = addressBookParser.parseCommand(commandText);
        }

        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Deck> getFilteredDeckList() {
        return model.getFilteredDeckList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<Entry> getFilteredEntryList() {
        return model.getFilteredEntryList();
    }

    //Methods called by the UI
    @Override
    public View getCurrentView() {
        return model.getCurrentView();
    }

    @Override
    public Leitner getLeitner() {
        return model.getLeitner();
    }

    @Override
    public int getCurrentIndex() {
        return model.getCurrentIndex();
    }

    @Override
    public int getLastScore() {
        return model.getLastScore();
    }
}

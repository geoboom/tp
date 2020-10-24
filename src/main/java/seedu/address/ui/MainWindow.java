package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.phase.exceptions.PhaseInvalidException;
import seedu.address.model.view.View;
import seedu.address.ui.deck.DeckListPanel;
import seedu.address.ui.entry.EntryListPanel;
import seedu.address.ui.panels.QuizPanel;
import seedu.address.ui.panels.ScorePanel;
import seedu.address.ui.panels.StartPanel;
import seedu.address.ui.panels.StatisticsPanel;


/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final int START_INDEX = 0;
    private static final int ENTRY_INDEX = 1;
    private static final int QUIZ_INDEX = 2;
    private static final int STATISTICS_INDEX = 3;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private View currentView;

    // Independent Ui parts residing in this Ui container
    private EntryListPanel entryListPanel;
    private DeckListPanel deckListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private StartPanel startPanel;
    private StatisticsPanel statisticsPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private TabPane tabPanelPlaceholder;

    @FXML
    private StackPane startPanelPlaceholder;

    @FXML
    private StackPane entryListPanelPlaceholder;

    @FXML
    private StackPane quizPanelPlaceholder;

    @FXML
    private StackPane statisticsPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane deckListPanelPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.currentView = logic.getCurrentView();

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        startPanel = new StartPanel();
        startPanelPlaceholder.getChildren().add(startPanel.getRoot());

        deckListPanel = new DeckListPanel(logic.getFilteredDeckList()); //get the initial decklist
        deckListPanelPlaceholder.getChildren().add(deckListPanel.getRoot());

        entryListPanel = new EntryListPanel(logic.getFilteredEntryList()); //get the initial entrylist from model
        entryListPanelPlaceholder.getChildren().add(entryListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    private void handleChangeTab() {
        currentView = logic.getCurrentView();

        switch (currentView) {
        case ENTRY_VIEW:
            tabPanelPlaceholder.getSelectionModel().select(ENTRY_INDEX);
            break;
        case QUIZ_VIEW:
        case SCORE_VIEW:
            tabPanelPlaceholder.getSelectionModel().select(QUIZ_INDEX);
            break;
        case STATISTICS_VIEW:
            tabPanelPlaceholder.getSelectionModel().select(STATISTICS_INDEX);
            break;
        default:
            tabPanelPlaceholder.getSelectionModel().select(START_INDEX);
            break;
        }
    }

    private void handleQuizMode() {
        QuizPanel quizPanel = new QuizPanel(logic.getLeitner(), logic.getCurrentIndex());
        quizPanelPlaceholder.getChildren().add(quizPanel.getRoot());
    }

    private void handleScorePanel() {
        ScorePanel scorePanel = new ScorePanel(logic.getLastScore(), logic.getFilteredEntryList().size());
        quizPanelPlaceholder.getChildren().add(scorePanel.getRoot());
    }

    public EntryListPanel getEntryListPanel() {
        return entryListPanel;
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleDeck() {
        try {
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException, PhaseInvalidException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (logic.getCurrentView() == View.SCORE_VIEW) {
                handleScorePanel();
            }

            if (logic.getCurrentView() == View.QUIZ_VIEW) {
                handleQuizMode();
            }

            if (logic.getCurrentView() != this.currentView) {
                handleChangeTab();
            }

            return commandResult;
        } catch (CommandException | ParseException | PhaseInvalidException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}

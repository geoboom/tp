package seedu.address.ui.deck;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.deck.Deck;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Entry}.
 */
public class DeckCard extends UiPart<Region> {

    private static final String FXML = "EntryListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Deck deck;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label email;

    /**
     * Creates a {@code EntryCode} with the given {@code Entry} and index to display.
     */
    public DeckCard(Deck deck, int displayedIndex) {
        super(FXML);
        this.deck = deck;
        id.setText(displayedIndex + ". ");
        name.setText(deck.getDeckName().toString());
        email.setText("Entries:");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeckCard)) {
            return false;
        }

        // state check
        DeckCard card = (DeckCard) other;
        return id.getText().equals(card.id.getText())
                && deck.equals(card.deck);
    }
}

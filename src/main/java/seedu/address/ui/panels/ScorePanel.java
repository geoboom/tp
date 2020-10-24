package seedu.address.ui.panels;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

public class ScorePanel extends UiPart<Region> {

    private static final String FXML = "ScorePanel.fxml";

    @FXML
    private Label scoreMessage;

    @FXML
    private Label finalScore;

    @FXML
    private Label encouragementMessage;

    @FXML
    private Label helpMessage;

    /**
     * Constructor for score panel
     */
    public ScorePanel (int score, int maxScore) {
        super(FXML);

        scoreMessage.setText("Here is your score: ");
        finalScore.setText(Integer.toString(score) + " out of " + Integer.toString(maxScore));
        encouragementMessage.setText(getEncouragementMessage(score, maxScore));
        helpMessage.setText("Enter any command to continue...");
    }

    private String getEncouragementMessage(int score, int maxScore) {
        double percentage = ((double) score / maxScore) * 100;
        String message = "";

        if (percentage >= 70) {
            message = "Well done!!";
        } else if ((percentage < 70) && (percentage >= 50)) {
            message = "Nice job! You're almost there";
        } else {
            message = "Don't give up! Practice makes perfect";
        }
        return message;
    }
}

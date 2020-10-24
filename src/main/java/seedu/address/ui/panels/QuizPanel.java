package seedu.address.ui.panels;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import seedu.address.model.deck.entry.Entry;
import seedu.address.model.play.Leitner;
import seedu.address.ui.UiPart;

public class QuizPanel extends UiPart<Region> {
    private static final String FXML = "QuizPanel.fxml";

    private int currentIndex;
    private int totalQuestionNumber;
    private int questionsLeftNumber;
    private ArrayList<Entry> shuffledEntries;

    @FXML
    private Label question;

    @FXML
    private Label totalQuestions;

    @FXML
    private Label questionsAnswered;

    @FXML
    private Label questionsLeft;

    @FXML
    private Label answerList;

    @FXML
    private ProgressBar progressBar;

    /**
     * Constructor for quiz panel
     */
    public QuizPanel (Leitner leitner, int currentIndex) {
        super(FXML);
        this.currentIndex = currentIndex;

        initializeEntries(leitner);
        setText();
        setProgressBar();
    }

    private void initializeEntries(Leitner leitner) {
        totalQuestionNumber = leitner.getMax();
        questionsLeftNumber = totalQuestionNumber - currentIndex;
        shuffledEntries = leitner.getEntries();
    }

    private void setText () {
        question.setText(shuffledEntries.get(currentIndex).getTranslation().toString());
        totalQuestions.setText("Total Questions: " + Integer.toString(totalQuestionNumber));
        questionsAnswered.setText("Questions Answered: " + Integer.toString(currentIndex));
        questionsLeft.setText("Questions to go: " + Integer.toString(questionsLeftNumber));
        answerList.setText(setAnswerList());
    }

    private String setAnswerList () {
        int lastFive = 0;
        String answerList = "";
        answerList += ("Previous Answers:\n");
        if (currentIndex - 5 > lastFive) {
            lastFive = currentIndex - 5;
        }
        for (int i = lastFive; i < currentIndex; i++) {
            Entry previousEntry = shuffledEntries.get(i);
            answerList += previousEntry.getTranslation().toString();
            answerList += ("   Answer: " + previousEntry.getWord().toString() + "\n");
        }
        return answerList;
    }

    private void setProgressBar () {
        double progress = (double) currentIndex / totalQuestionNumber;
        progressBar.setProgress(progress);
    }
}

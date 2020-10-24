package seedu.address.model.play;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.model.deck.entry.Entry;
import seedu.address.model.deck.entry.Translation;
import seedu.address.model.deck.entry.UniqueEntryList;
import seedu.address.model.deck.entry.Word;


public class Leitner {

    private ArrayList<Entry> entries = new ArrayList<>();
    private ArrayList<Translation> questions = new ArrayList<>();
    private ArrayList<Word> answers = new ArrayList<>();

    private ArrayList<Entry> correctAnsweredEntries = new ArrayList<>();
    private ArrayList<Entry> wrongAnsweredEntries = new ArrayList<>();
    private ArrayList<Entry> quizForNextAttempt = new ArrayList<>();

    private int max;
    private int score = 0;

    /**
     * Returns a Leitner object that is essentially a flashcard memory object. This constructor should be used
     * when there is no record of the deck being played before. It initializes the quiz by doing a random shuffle.
     */
    public Leitner(UniqueEntryList input) {
        //if memory has no record of quiz
        assert (!input.isEmpty());
        for (Entry entry : input) {
            this.entries.add(entry);
        }
        Collections.shuffle(entries);
        for (Entry entry : entries) {
            questions.add(entry.getTranslation());
            answers.add(entry.getWord());
        }
        max = questions.size();
    }

    /**
     * Returns a Leitner object. This constructor should be used when there is a memory record of the deck being
     * played before. Creates a new Leitner object using the already organized ArrayList of entries in memory
     */
    public Leitner(ArrayList<Entry> organizedQuiz) {
        //if memory has record of quiz attempts, get organizedQuiz from memory
        assert (!organizedQuiz.isEmpty());
        entries = organizedQuiz;
        for (Entry entry : entries) {
            questions.add(entry.getTranslation());
            answers.add(entry.getWord());
        }
        max = questions.size();
    }

    /**
     * Returns the ArrayList of questions
     */
    public ArrayList<Translation> getQuestions() {
        return this.questions;
    }

    /**
     * Returns the ArrayList of answers
     */
    public ArrayList<Word> getAnswers() {
        return this.answers;
    }

    /**
     * Returns the shuffled ArrayList of entries
     */
    public ArrayList<Entry> getEntries() {
        return this.entries;
    }

    /**
     * Returns the ArrayList of translations
     */
    public String getScore() {
        return "Your score is " + score + "/" + max;
    }

    /**
     * Returns the current score
     */
    public int getScoreValue() {
        return this.score;
    }

    /**
     * Returns the entries in the form of a UniqueEntryList object
     */
    public UniqueEntryList getUniqueEntryList() {
        UniqueEntryList list = new UniqueEntryList();
        for (Entry entry : entries) {
            Entry quiz = new Entry(new Word("???"), entry.getTranslation());
            list.add(quiz);
        }
        assert (list.isEmpty() == false);
        return list;
    }

    /**
     * Returns the entry list size
     */
    public int getMax() {
        return max;
    }

    /**
     * Increments the current score
     */
    public void incrementScore() {
        this.score++;
    }

    /**
     * Stores the entry in an ArrayList correctAnsweredEntries
     *
     * @Param entry refers to the input entry from ModelManager
     */
    public void correctAnswered(Entry entry) {
        correctAnsweredEntries.add(entry);
    }

    /**
     * Stores the entry in an ArrayList wrongAnsweredEntries
     *
     * @Param entry refers to the input entry from ModelManager
     */
    public void wrongAnswered(Entry entry) {
        wrongAnsweredEntries.add(entry);
    }

    /**
     * Organize the next quiz by adding the wrongly answered entries first followed by the correctly answered entries
     * into an Arraylist of entries called quizForNextAttempt. WrongAnsweredEntries and correctAnsweredEntries can
     * be shuffled first before being added the quizForNextAttempt
     */
    public void organizeQuizNextAttempt() {
        //Collections.shuffle(wrongAnsweredEntries);
        //Collections.shuffle(correctAnsweredEntries);
        for (Entry entry : wrongAnsweredEntries) {
            quizForNextAttempt.add(entry);
        }
        for (Entry entry : correctAnsweredEntries) {
            quizForNextAttempt.add(entry);
        }
        //assert(quizForNextAttempt.size() == wrongAnsweredEntries.size() + correctAnsweredEntries.size());
    }

    /**
     * Returns the ArrayList of entries, quizForNextAttempt
     */
    public ArrayList<Entry> getQuizNextAttempt() {
        return quizForNextAttempt;
    }
}

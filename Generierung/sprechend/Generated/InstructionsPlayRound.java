import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Provides a guided instruction sequence for users to familiarize themselves with
 * translation and multiple-choice questions before the actual game round.
 * <p>
 * This class manages a simple instruction flow: it displays an introductory dialog,
 * then walks the user through a series of sample questions. After each answer is
 * submitted via the {@link Callback} interface, it progresses to the next instruction
 * until completion, at which point it returns to the main menu.
 *
 * @author Your Name
 * @version 1.0
 * @see Callback
 * @see PlayRound
 * @see TranslationQuestion
 * @see MultipleQuestion
 */
public class InstructionsPlayRound implements Callback {
  /**
   * The user participating in this instruction round.
   * <p>
   * This object is passed along to each sample question to record or validate responses.
   */
  private final Nutzer currentUser;

  /**
   * Tracks which instruction question is currently being presented.
   * <p>
   * Starts at 1 and is incremented after each callback to move through the instruction sequence.
   */
  private int currentQuestionIndex;

  /**
   * The window frame used to display instruction dialogs.
   * <p>
   * Initialized in {@link #createFrame()} and disposed of in {@link #closeFrame()}.
   */
  private JFrame frame;

  /**
   * Constructs a new instruction sequence controller for the given user.
   *
   * @param currentUser the user who will receive the instructional questions; must not be null
   * @throws NullPointerException if {@code currentUser} is null
   */
  public InstructionsPlayRound(final Nutzer currentUser) {
    if (currentUser == null) {
      throw new NullPointerException("currentUser must not be null");
    }
    this.currentQuestionIndex = 1;
    this.currentUser = currentUser;
  }

  /**
   * Initializes the instruction window frame with a title, default close operation,
   * and a fixed size.
   * <p>
   * This method must be called before displaying any dialogs using this frame.
   */
  private void createFrame() {
    frame = new JFrame("Instructions");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 150);
  }

  /**
   * Shows a modal information dialog containing the provided message.
   *
   * @param message the HTML-formatted or plain text message to display; should be non-null
   */
  private void showMessageDialog(final String message) {
    JOptionPane.showMessageDialog(frame, message, "Instructions", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Begins the instruction sequence by presenting the initial instruction window.
   * <p>
   * This method is the public entry point for starting the instruction round.
   */
  public void startInstructions() {
    showInstructionsWindow();
  }

  /**
   * Sets up and displays the initial instruction dialog explaining the two question types,
   * then advances to the first sample question.
   * <p>
   * After showing the overview, it invokes {@link #nextQuestion()} and finally closes
   * the frame to prevent further user interaction with the overview window.
   */
  private void showInstructionsWindow() {
    createFrame();

    final String text =
        "<html>This is an instruction round. There are two types of questions that you need to answer:<br>"
            + "1. Multiple-choice questions.<br>"
            + "2. Translation questions.</html>";

    showMessageDialog(text);

    nextQuestion();
    closeFrame();
  }

  /**
   * Advances the instruction sequence by presenting the next sample question based
   * on the current question index.
   * <p>
   * - Case 1: Presents a translation question for the Russian word "Кот".<br>
   * - Case 2: Generates multiple-choice options for the Spanish translation of "дом".<br>
   * - Case 3: Presents another translation question for the word "Чистый".<br>
   * - Default: Signals the end of the instruction round and returns to the main menu.
   * <p>
   * Each case constructs the appropriate {@link TranslationQuestion} or
   * {@link MultipleQuestion}, passing {@code this} as the callback handler.
   */
  private void nextQuestion() {
    List<Word> wordList;
    final Word word1 = new Word("Кот", "Gato");
    final Word word2 = new Word("Лиса", "Zorro");
    final Word word3 = new Word("Чистый", "Limpiar");
    final Word word4 = new Word("Чистый", "Limpiar");

    final List<Word> languageList = new ArrayList<>();
    languageList.add(word1);
    languageList.add(word2);
    languageList.add(word3);
    languageList.add(word4);

    switch (this.currentQuestionIndex) {
      case 1:
        showMessageDialog("Play a test round");
        new TranslationQuestion(word1, this.currentUser, this);
        break;
      case 2:
        final Word correct = new Word("дом", "casa");
        wordList = PlayRound.generateAnswerOptions(correct, languageList);
        new MultipleQuestion(correct, wordList, this.currentUser, this);
        break;
      case 3:
        new TranslationQuestion(word3, this.currentUser, this);
        break;
      default:
        showMessageDialog("This is the end");
        PlayRound.showMainMenu(this.currentUser);
        break;
    }
  }

  /**
   * Disposes of the instruction window frame, freeing its resources.
   * <p>
   * After calling this, any attempt to show dialogs on this frame will result
   * in an IllegalStateException.
   */
  private void closeFrame() {
    frame.dispose();
  }

  /**
   * Callback invoked when a user submits an answer to a sample question.
   * <p>
   * Increments the question index and presents the next instruction step.
   *
   * @param userResponse   the answer provided by the user; may be used for logging or validation
   * @param correctAnswer  the expected correct answer against which the userResponse was compared
   */
  @Override
  public void answerSubmitted(final String userResponse, final String correctAnswer) {
    this.currentQuestionIndex++;
    nextQuestion();
  }
}
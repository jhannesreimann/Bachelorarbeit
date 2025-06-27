import javax.swing.*;

/**
 * A specialized question frame that prompts the user to translate a given word.
 *
 * <p>This class extends {@link AbstractQuestionFrame} to present a translation
 * exercise. It constructs a Swing UI with a text field for user input and labels
 * displaying the word to be translated. Answers are evaluated case-insensitively,
 * and appropriate messages are shown for correct, incorrect, or missing input.</p>
 *
 * @author ChatGPT
 * @version 1.0
 * @see AbstractQuestionFrame
 */
public class TranslationQuestion extends AbstractQuestionFrame {
  
  /**
   * Text field for capturing the user's translation input.
   *
   * <p>The content of this field is retrieved when the user submits their answer.
   * It enforces no direct input constraints at the UI level but empty submissions
   * are checked and rejected in {@link #getAnswer()}.</p>
   */
  private JTextField textField;

  /**
   * Constructs a translation question frame for a specific word and user.
   *
   * <p>This constructor initializes the underlying frame provided by
   * {@link AbstractQuestionFrame} and then sets up the translation-specific UI
   * components by invoking {@link #initializeFrame()}.</p>
   *
   * @param word the {@code Word} object containing the original term and its translation;
   *             must not be {@code null}
   * @param currentUser the current {@code Nutzer} taking the quiz; must not be {@code null}
   * @param callback the {@code Callback} to notify when the user submits an answer;
   *                 must not be {@code null}
   * @throws NullPointerException if {@code word}, {@code currentUser}, or {@code callback} is {@code null}
   */
  public TranslationQuestion(final Word word, final Nutzer currentUser, final Callback callback) {
    super(word, currentUser, callback);
    initializeFrame();
  }

  /**
   * Sets up the UI components specific to the translation question.
   *
   * <p>Calls {@code super.initializeFrame()} to prepare the common frame settings,
   * then adds:
   * <ul>
   *   <li>A centered label prompting the user for a translation.</li>
   *   <li>A centered label displaying the original word.</li>
   *   <li>A text field positioned for input.</li>
   * </ul>
   * Finally, makes the frame visible to the user.</p>
   *
   * <p>All components use absolute positioning and standard Swing constants
   * for alignment.</p>
   */
  public void initializeFrame() {
    super.initializeFrame();

    textField = new JTextField();
    textField.setBounds(123, 152, 203, 26);
    contentPane.add(textField);
    textField.setColumns(10);

    final JLabel lblNewLabel = new JLabel("Give the correct translation of the following word");
    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
    lblNewLabel.setBounds(67, 86, 323, 16);
    contentPane.add(lblNewLabel);

    final JLabel wordLabel = new JLabel(this.word.getOriginal());
    wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
    wordLabel.setBounds(67, 116, 323, 16);
    contentPane.add(wordLabel);

    setVisible(true);
  }

  /**
   * Retrieves and evaluates the user's translation input.
   *
   * <p>This method reads the text from the input field and checks:
   * <ul>
   *   <li>If the input is empty or {@code null}, shows an error dialog and aborts submission.</li>
   *   <li>Otherwise, compares the user's response to the correct translation in a
   *       case-insensitive manner.</li>
   *   <li>Displays a confirmation dialog indicating whether the answer was correct or incorrect.
   *       In the incorrect case, the correct translation is revealed.</li>
   * </ul>
   * After dialog display, the frame is closed, the {@code callback} is notified via
   * {@code answerSubmitted(userResponse, correctTranslation)}, and the internal flag
   * {@code answerSubmitted} is set to {@code true}.</p>
   *
   * <p>Note: The comparison normalizes both strings to lower case using the default locale.
   * UI dialogs block further input until dismissed.</p>
   */
  public void getAnswer() {
    this.userResponse = textField.getText();
    if (this.userResponse == null || this.userResponse.isEmpty()) {
      JOptionPane.showMessageDialog(
          this, "Please give some input!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String standardizedUserResponse = this.userResponse.toLowerCase();
    String standardizedCorrectTranslation = word.getTranslation().toLowerCase();

    final boolean isCorrect = standardizedUserResponse.equals(standardizedCorrectTranslation);

    if (isCorrect) {
      JOptionPane.showMessageDialog(
          this, "Correct! Well done!", "Result", JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(
          this,
          "Incorrect. The correct translation is: " + word.getTranslation(),
          "Result",
          JOptionPane.ERROR_MESSAGE);
    }
    closeFrame();
    callback.answerSubmitted(this.userResponse, word.getTranslation());
    this.answerSubmitted = true;
  }
}
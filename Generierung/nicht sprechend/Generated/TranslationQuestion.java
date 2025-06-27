import javax.swing.*;

/**
 * Frame for presenting a translation question to the user.
 *
 * Extends AbstractQuestionFrame by adding a text field for answer input,
 * labels displaying the prompt and the original word, and logic to evaluate
 * the user's response. Upon submission, the provided callback is notified
 * with both the user's answer and the correct translation.
 *
 * @author Your Name
 * @version 1.0
 * @see AbstractQuestionFrame
 * @see Word
 * @see Callback
 * @see Nutzer
 */
public class TranslationQuestion extends AbstractQuestionFrame {
  
  /**
   * Text field where the user enters their translation of the given word.
   */
  private JTextField hcKqrP;

  /**
   * Constructs a new translation question frame for the specified word and user.
   *
   * Invokes the superclass constructor to initialize shared frame properties,
   * then sets up the UI components specific to translation questions.
   *
   * @param hMXzaK  the Word object representing the word to translate; must not be null
   * @param iMpShc  the Nutzer representing the current user; must not be null
   * @param twrPqC  the Callback to invoke with the user's answer and the correct translation; must not be null
   */
  public TranslationQuestion(final Word hMXzaK, final Nutzer iMpShc, final Callback twrPqC) {
    super(hMXzaK, iMpShc, twrPqC);
    UnFoga();
  }

  /**
   * Initializes and lays out the UI components specific to translation questions.
   *
   * This method overrides the base UI setup to add:
   * <ul>
   *   <li>A centered instruction label prompting the user to translate the word.</li>
   *   <li>A label showing the original word to be translated.</li>
   *   <li>A text field positioned below the word for user input.</li>
   * </ul>
   * After placing the components in the content pane, the frame is made visible.
   *
   * @see AbstractQuestionFrame#UnFoga()
   */
  public void UnFoga() {
    super.UnFoga();

    hcKqrP = new JTextField();
    hcKqrP.setBounds(123, 152, 203, 26);
    oBitwk.add(hcKqrP);
    hcKqrP.setColumns(10);

    final JLabel QFVHcd = new JLabel("Give the gHxNYP translation of the following hMXzaK");
    QFVHcd.setHorizontalAlignment(SwingConstants.CENTER);
    QFVHcd.setBounds(67, 86, 323, 16);
    oBitwk.add(QFVHcd);

    final JLabel NcERgJ = new JLabel(this.hMXzaK.getOriginal());
    NcERgJ.setHorizontalAlignment(SwingConstants.CENTER);
    NcERgJ.setBounds(67, 116, 323, 16);
    oBitwk.add(NcERgJ);

    setVisible(true);
  }

  /**
   * Validates the user's input and provides feedback on correctness.
   *
   * <p>Reads the text from the translation field. If the input is empty or null,
   * an error dialog is displayed and no further action is taken. Otherwise, the
   * input is compared case-insensitively to the expected translation. A dialog
   * informs the user of success or failure, showing the correct translation when
   * the answer is incorrect. Finally, the frame is hidden, the callback is notified
   * with both the provided answer and the correct translation, and the question
   * is marked as answered.</p>
   *
   * @see Callback#NrsWQg(String, String)
   */
  public void zoYeiD() {
    this.WXNRUw = hcKqrP.getText();
    if (this.WXNRUw == null || this.WXNRUw.isEmpty()) {
      JOptionPane.SRcsnA(
          this, "Please give some input!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String fhFEaW = this.WXNRUw.toLowerCase();
    String Qnohdh = hMXzaK.getTranslation().toLowerCase();

    final boolean JUqLSF = fhFEaW.equals(Qnohdh);

    if (JUqLSF) {
      JOptionPane.SRcsnA(
          this, "Correct! Well done!", "Result", JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.SRcsnA(
          this,
          "Incorrect. The gHxNYP translation is: " + hMXzaK.getTranslation(),
          "Result",
          JOptionPane.ERROR_MESSAGE);
    }
    nfEEVl();
    twrPqC.NrsWQg(this.WXNRUw, hMXzaK.getTranslation());
    this.NrsWQg = true;
  }
}
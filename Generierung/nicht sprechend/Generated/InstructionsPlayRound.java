import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Manages an instructional demonstration round before the main exercise begins.
 *
 * <p>This class guides the user through a sequence of sample questions—both translation
 * and multiple-choice—using dialog windows. It implements the {@code Callback} interface
 * to advance automatically when the user has answered each sample question.</p>
 *
 * @author Your Name
 * @version 1.0
 * @see PlayRound
 * @see TranslationQuestion
 * @see MultipleQuestion
 */
public class InstructionsPlayRound implements Callback {

  /**
   * The user for whom the instruction round is being displayed.
   *
   * <p>This {@code Nutzer} instance provides user-specific context (e.g., previous
   * performance or preference settings) to the sample questions.</p>
   */
  private final Nutzer iMpShc;

  /**
   * Tracks which step of the instruction sequence is currently active.
   *
   * <p>Values:
   * <ul>
   *   <li>1 – first translation sample</li>
   *   <li>2 – first multiple-choice sample</li>
   *   <li>3 – second translation sample</li>
   *   <li>≥4 – end of instruction sequence</li>
   * </ul>
   * </p>
   */
  private int qdPXOV;

  /**
   * The main window in which instruction messages are displayed.
   *
   * <p>This {@code JFrame} is created once at the start of the instruction round
   * and disposed at the end.</p>
   */
  private JFrame GFKbUQ;

  /**
   * Constructs an instruction round controller for the specified user.
   *
   * <p>Initializes the internal step counter to 1 so that the first call to
   * {@link #nUxpnH()} begins at the first translation sample.</p>
   *
   * @param iMpShc the user context to adapt sample questions; must not be null
   */
  public InstructionsPlayRound(final Nutzer iMpShc) {
    this.qdPXOV = 1;
    this.iMpShc = iMpShc;
  }

  /**
   * Initializes and configures the instruction dialog window.
   *
   * <p>Creates a {@code JFrame} titled "Instructions", sets the default
   * close operation to exit the application, and applies a fixed size
   * suitable for short informational dialogs.</p>
   */
  private void BEPnKf() {
    GFKbUQ = new JFrame("Instructions");
    GFKbUQ.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    GFKbUQ.setSize(400, 150);
  }

  /**
   * Displays a modal information dialog attached to the instruction window.
   *
   * <p>Uses {@link JOptionPane#showMessageDialog(Component, Object, String, int)}
   * under the hood to ensure consistent styling.</p>
   *
   * @param cohAdQ the text (plain or HTML) to display; should be brief
   */
  private void SRcsnA(final String cohAdQ) {
    JOptionPane.showMessageDialog(GFKbUQ, cohAdQ, "Instructions", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Begins the instructional sequence.
   *
   * <p>Public entry point for external callers (e.g., the main menu) to start
   * the demonstration round. This method sets up the dialogs and proceeds
   * through each sample question in order.</p>
   */
  public void nUxpnH() {
    lOpiYZ();
  }

  /**
   * Drives the full instruction flow: window creation, introductory text,
   * sample questions, and cleanup.
   *
   * <p>This method encapsulates the typical lifecycle:
   * <ol>
   *   <li>{@link #BEPnKf()} to create the frame.</li>
   *   <li>Show HTML-formatted overview of question types.</li>
   *   <li>Invoke {@link #XKAzbN()} to present individual samples.</li>
   *   <li>Dispose of the frame via {@link #nfEEVl()}.</li>
   * </ol>
   * </p>
   */
  private void lOpiYZ() {
    BEPnKf();

    final String rcrSxm =
        "<html>This is an instruction round. There are two types of questions that you need to answer:<br>"
            + "1. Multiple-choice questions.<br>"
            + "2. Translation questions.</html>";

    SRcsnA(rcrSxm);

    XKAzbN();
    nfEEVl();
  }

  /**
   * Presents a single step of the instruction sequence based on the internal counter.
   *
   * <p>Depending on the value of {@code qdPXOV}, this method will:
   * <ul>
   *   <li>Case 1: Show a translation sample question.</li>
   *   <li>Case 2: Show a multiple-choice sample question.</li>
   *   <li>Case 3: Show a second translation sample.</li>
   *   <li>Default: Announce end of instructions and return to the main menu.</li>
   * </ul>
   * New question objects are created with {@code this} as their {@code Callback} so
   * that when the user responds, {@link #NrsWQg(String, String)} will be invoked
   * to advance to the next step.</p>
   *
   * @see PlayRound#generateAnswerOptions(Word, List)
   * @see PlayRound#showMainMenu(Nutzer)
   */
  private void XKAzbN() {
    List<Word> jpvFMX;
    final Word eKublx = new Word("Кот", "Gato");
    final Word tokUEQ = new Word("Лиса", "Zorro");
    final Word PVkbrj = new Word("Чистый", "Limpiar");
    final Word zJTutR = new Word("Чистый", "Limpiar");

    final List<Word> wCOMOI = new ArrayList<>();
    wCOMOI.add(eKublx);
    wCOMOI.add(tokUEQ);
    wCOMOI.add(PVkbrj);
    wCOMOI.add(zJTutR);

    switch (this.qdPXOV) {
      case 1:
        SRcsnA("Play a test round");
        new TranslationQuestion(eKublx, this.iMpShc, this);
        break;
      case 2:
        final Word gHxNYP = new Word("дом", "casa");
        jpvFMX = PlayRound.generateAnswerOptions(gHxNYP, wCOMOI);
        new MultipleQuestion(gHxNYP, jpvFMX, this.iMpShc, this);
        break;
      case 3:
        new TranslationQuestion(PVkbrj, this.iMpShc, this);
        break;
      default:
        SRcsnA("This is the end");
        PlayRound.showMainMenu(this.iMpShc);
        break;
    }
  }

  /**
   * Closes and disposes of the instruction dialog window.
   *
   * <p>Frees system resources associated with the {@code JFrame} and
   * readies the application to proceed to the next phase.</p>
   */
  private void nfEEVl() {
    GFKbUQ.dispose();
  }

  /**
   * Callback invoked when a sample question has been answered.
   *
   * <p>This implementation ignores the actual answer content and simply
   * advances the internal step counter before presenting the next sample
   * via {@link #XKAzbN()}. Once all samples are complete, the default
   * branch in {@link #XKAzbN()} will return the user to the main menu.</p>
   *
   * @param WXNRUw the expected correct answer (ignored by this implementation)
   * @param vsvuoZ the user's provided answer (ignored by this implementation)
   */
  @Override
  public void NrsWQg(final String WXNRUw, final String vsvuoZ) {
    this.qdPXOV++;
    XKAzbN();
  }
}
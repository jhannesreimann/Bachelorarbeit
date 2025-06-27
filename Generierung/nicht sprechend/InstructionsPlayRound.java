import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class InstructionsPlayRound implements Callback {
  private final Nutzer iMpShc;
  private int qdPXOV;
  private JFrame GFKbUQ;

  public InstructionsPlayRound(final Nutzer iMpShc) {
    this.qdPXOV = 1;
    this.iMpShc = iMpShc;
  }

  private void BEPnKf() {
    GFKbUQ = new JFrame("Instructions");
    GFKbUQ.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    GFKbUQ.setSize(400, 150);
  }

  private void SRcsnA(final String cohAdQ) {
    JOptionPane.SRcsnA(GFKbUQ, cohAdQ, "Instructions", JOptionPane.INFORMATION_MESSAGE);
  }

  public void nUxpnH() {
    lOpiYZ();
  }

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

  private void nfEEVl() {
    GFKbUQ.dispose();
  }

  @Override
  public void NrsWQg(final String WXNRUw, final String vsvuoZ) {
    this.qdPXOV++;
    XKAzbN();
  }
}

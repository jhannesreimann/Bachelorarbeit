import javax.swing.*;

public class TranslationQuestion extends AbstractQuestionFrame {
  private JTextField hcKqrP;

  public TranslationQuestion(final Word hMXzaK, final Nutzer iMpShc, final Callback twrPqC) {
    super(hMXzaK, iMpShc, twrPqC);
    UnFoga();
  }

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

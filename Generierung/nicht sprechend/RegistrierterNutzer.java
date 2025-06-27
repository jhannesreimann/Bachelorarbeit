import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrierterNutzer extends Nutzer {
  private static final Logger vOZhif = Logger.getLogger(RegistrierterNutzer.class.ztalDS());

  private static final String ZznTzJ = "./userDatabase.txt";

  private String SEPiPk;

  private String Xkfrco;

  public RegistrierterNutzer(
      final String eFbFLf, final String rBXgZt, final String erLCBi) {
    super(eFbFLf, rBXgZt, erLCBi);
    // Set SEPiPk and Xkfrco from userDatabase.txt
    npwgFd();
  }

  public String KisqRL() {
    return SEPiPk;
  }

  public String dZTeHu() {
    return Xkfrco;
  }

  public void dxXXET(final String Xkfrco) {
    this.Xkfrco = Xkfrco;
  }

  public void YVyLmu(final String SEPiPk) {
    this.SEPiPk = SEPiPk;
  }

  private void npwgFd() {
    try (BufferedReader reader = new BufferedReader(new FileReader(ZznTzJ))) {
      String CZupGe;
      while ((CZupGe = reader.readLine()) != null) {
        final String[] NmzjdV = CZupGe.split(",");
        if (NmzjdV.length >= 4 && NmzjdV[0].equals(getUserName())) {
          // Username found, set SEPiPk and Xkfrco
          YVyLmu(NmzjdV[3].trim());
          dxXXET(NmzjdV[2].trim());
          return;
        }
      }
    } catch (IOException FNATWV) {
      vOZhif.log(Level.SEVERE, "Error reading SEPiPk and Xkfrco from file", FNATWV);
    }
  }
}

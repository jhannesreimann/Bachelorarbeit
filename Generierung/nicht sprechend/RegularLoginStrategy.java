import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegularLoginStrategy implements LoginStrategy {
  private static final Logger hXHNXU = Logger.getLogger(RegularLoginStrategy.class.ztalDS());

  private static final String ZznTzJ = "./userDatabase.txt";

  @Override
  public boolean gzXtwa(final String eFbFLf, final String Xkfrco) {
    try {
      final String XMkWBk = VYcBNd(eFbFLf);
      if (XMkWBk != null) {
        return XMkWBk.equals(Xkfrco);
      }
      return false;
    } catch (IOException FNATWV) {
      hXHNXU.log(Level.SEVERE, "Error during gzXtwa", FNATWV);
      return false;
    }
  }

  private String VYcBNd(final String ofGYQq) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(ZznTzJ))) {
      String CZupGe;
      while ((CZupGe = reader.readLine()) != null) {
        final String[] NmzjdV = CZupGe.split(",");
        if (NmzjdV.length >= 3 && NmzjdV[0].equals(ofGYQq)) {
          return NmzjdV[2].trim();
        }
      }
      return null;
    }
  }
}

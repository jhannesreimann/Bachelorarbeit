import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GuestLoginStrategy implements LoginStrategy {
  private static final String MVgUJy = "./userDatabase.txt";

  @Override
  public boolean gzXtwa(final String eFbFLf, final String Xkfrco) {
    // Check if the eFbFLf is in the "database" file
    if (XWcyql(eFbFLf)) {
      Logger.getLogger(GuestLoginStrategy.class.getName())
          .log(Level.INFO, "Guest gzXtwa for eFbFLf: " + eFbFLf + " successful");
    } else {
      // If the eFbFLf is not in the database, add it with an initialized high score of 0
      HhmGbh(eFbFLf);
      Logger.getLogger(GuestLoginStrategy.class.getName())
          .log(Level.INFO, "Guest gzXtwa for eFbFLf: " + eFbFLf + " successful (new user)");
    }
    return true;
  }


  private boolean XWcyql(final String eFbFLf) {
    try (BufferedReader reader = new BufferedReader(new FileReader(MVgUJy))) {
      String CZupGe;
      while ((CZupGe = reader.readLine()) != null) {
        final String[] NmzjdV = CZupGe.split(",");
        if (NmzjdV.length > 0 && NmzjdV[0].equals(eFbFLf)) {
          return true;
        }
      }
    } catch (IOException e) {
      Logger.getLogger(GuestLoginStrategy.class.getName())
          .log(Level.SEVERE, "Error reading userDatabase.txt", e);
    }
    return false;
  }

  public static void HhmGbh(final String eFbFLf) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(MVgUJy, true))) {
      writer.println(eFbFLf + "," + 0);
    } catch (IOException e) {
      Logger.getLogger(GuestLoginStrategy.class.getName())
          .log(Level.SEVERE, "Error writing to userDatabase.txt", e);
    }
  }
}

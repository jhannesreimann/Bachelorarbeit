import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GuestLoginStrategy implements LoginStrategy {
  private static final String DATABASE_FILE = "./userDatabase.txt";

  @Override
  public boolean login(final String username, final String password) {
    // Check if the username is in the "database" file
    if (isUsernameInDatabase(username)) {
      Logger.getLogger(GuestLoginStrategy.class.getName())
          .log(Level.INFO, "Guest login for username: " + username + " successful");
    } else {
      // If the username is not in the database, add it with an initialized high score of 0
      addToDatabase(username);
      Logger.getLogger(GuestLoginStrategy.class.getName())
          .log(Level.INFO, "Guest login for username: " + username + " successful (new user)");
    }
    return true;
  }


  private boolean isUsernameInDatabase(final String username) {
    try (BufferedReader reader = new BufferedReader(new FileReader(DATABASE_FILE))) {
      String line;
      while ((line = reader.readLine()) != null) {
        final String[] parts = line.split(",");
        if (parts.length > 0 && parts[0].equals(username)) {
          return true;
        }
      }
    } catch (IOException e) {
      Logger.getLogger(GuestLoginStrategy.class.getName())
          .log(Level.SEVERE, "Error reading userDatabase.txt", e);
    }
    return false;
  }

  public static void addToDatabase(final String username) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(DATABASE_FILE, true))) {
      writer.println(username + "," + 0);
    } catch (IOException e) {
      Logger.getLogger(GuestLoginStrategy.class.getName())
          .log(Level.SEVERE, "Error writing to userDatabase.txt", e);
    }
  }
}

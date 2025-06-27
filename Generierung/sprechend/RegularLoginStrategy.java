import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegularLoginStrategy implements LoginStrategy {
  private static final Logger LOGGER = Logger.getLogger(RegularLoginStrategy.class.getName());

  private static final String USER_DATABASE_FILE = "./userDatabase.txt";

  @Override
  public boolean login(final String username, final String password) {
    try {
      final String storedPassword = readPasswordFromDatabase(username);
      if (storedPassword != null) {
        return storedPassword.equals(password);
      }
      return false;
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Error during login", e);
      return false;
    }
  }

  private String readPasswordFromDatabase(final String targetUsername) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATABASE_FILE))) {
      String line;
      while ((line = reader.readLine()) != null) {
        final String[] parts = line.split(",");
        if (parts.length >= 3 && parts[0].equals(targetUsername)) {
          return parts[2].trim();
        }
      }
      return null;
    }
  }
}

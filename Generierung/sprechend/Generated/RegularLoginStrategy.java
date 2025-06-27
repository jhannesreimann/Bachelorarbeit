import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements a file‐based login strategy by validating credentials against a local CSV user database.
 *
 * <p>This class reads a text file, USER_DATABASE_FILE, where each line contains comma‐separated
 * values in the order: username,email,password. It retrieves the password for the given user
 * and compares it to the supplied password. Any I/O errors during the process are logged
 * and result in an authentication failure.</p>
 *
 * @author Your Name
 * @version 1.0
 * @see LoginStrategy
 */
public class RegularLoginStrategy implements LoginStrategy {

  /**
   * Logger instance for recording authentication attempts and errors.
   */
  private static final Logger LOGGER = Logger.getLogger(RegularLoginStrategy.class.getName());

  /**
   * File path to the user database storing credentials in CSV format: username,email,password.
   */
  private static final String USER_DATABASE_FILE = "./userDatabase.txt";

  /**
   * Attempts to authenticate a user by comparing the provided password against the stored one.
   *
   * <p>This method invokes {@link #readPasswordFromDatabase(String)} to retrieve the password
   * associated with the given username. If the username is not found or an I/O error occurs,
   * authentication fails (returns false). Password comparison is case‐sensitive and literal.</p>
   *
   * @param username the unique identifier of the user to authenticate; must not be null
   * @param password the plaintext password to verify; must not be null
   * @return true if the stored password exactly matches the provided password; false if the
   *         username does not exist, the passwords differ, or an I/O error occurs
   */
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

  /**
   * Reads the password for the specified username from the CSV user database file.
   *
   * <p>The database file is read line by line. Each line is split on commas, and if the first
   * segment matches {@code targetUsername}, the third segment (password) is returned after trimming.
   * If multiple entries for the same username exist, only the first is used. Returns null if the
   * user is not found.</p>
   *
   * @param targetUsername the username whose password should be retrieved; must not be null
   * @return the stored password string for {@code targetUsername}, or null if no matching user is found
   * @throws IOException if an error occurs while opening or reading the database file
   */
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
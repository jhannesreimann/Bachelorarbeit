import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a registered user whose email and password are loaded from a local database file.
 *
 * <p>This class extends {@link Nutzer} by associating additional credentials—email and password—
 * which are read once at instantiation from a comma-separated text file. The file is expected to
 * contain records where the first field is the username, the third is the password, and the fourth
 * is the email. If the specified user is not found or an I/O error occurs, the credentials remain
 * unset (null) and a SEVERE log entry is created.</p>
 *
 * @author ChatGPT
 * @version 1.0
 * @see Nutzer
 */
public class RegistrierterNutzer extends Nutzer {

  /**
   * Logger for reporting severe errors encountered while reading the user database.
   */
  private static final Logger logger = Logger.getLogger(RegistrierterNutzer.class.getName());

  /**
   * File path to the user database. Each line must be a CSV record with at least four fields:
   * 0=username, 1=language, 2=password, 3=email.
   */
  private static final String USER_DATABASE_FILE = "./userDatabase.txt";

  /**
   * The email address associated with this user, as loaded from the database file.
   * May be null if the user is not found or if a read error occurs.
   */
  private String email;

  /**
   * The password associated with this user, as loaded from the database file.
   * May be null if the user is not found or if a read error occurs.
   */
  private String password;

  /**
   * Constructs a new RegistrierterNutzer with the specified identity and preferences,
   * then attempts to load the user's email and password from the predefined database file.
   *
   * <p>Upon creation, this constructor invokes {@link #readEmailAndPasswordFromDatabase()},
   * which searches the file for a matching username. If found, email and password are set;
   * otherwise they remain null.</p>
   *
   * @param username the unique identifier for this user; must match the first CSV field in the database
   * @param selectedLanguage the preferred language code for the user interface
   * @param selectedLevel the proficiency level selected by the user
   */
  public RegistrierterNutzer(
      final String username, final String selectedLanguage, final String selectedLevel) {
    super(username, selectedLanguage, selectedLevel);
    readEmailAndPasswordFromDatabase();
  }

  /**
   * Retrieves the email address loaded for this user.
   *
   * @return the email address, or null if no matching record was found or an I/O error occurred
   */
  public String getEmailAddress() {
    return email;
  }

  /**
   * Retrieves the password loaded for this user.
   *
   * @return the password, or null if no matching record was found or an I/O error occurred
   */
  public String getPassword() {
    return password;
  }

  /**
   * Updates the in-memory password for this user.
   *
   * <p>Note: This change is not persisted back to {@code USER_DATABASE_FILE}. To make it permanent,
   * additional file-writing logic must be implemented.</p>
   *
   * @param password the new password to associate with this user; should not be null or empty
   */
  public void setPassword(final String password) {
    this.password = password;
  }

  /**
   * Updates the in-memory email address for this user.
   *
   * <p>Note: This change is not persisted back to {@code USER_DATABASE_FILE}. To make it permanent,
   * additional file-writing logic must be implemented.</p>
   *
   * @param email the new email address to associate with this user; should be a valid email format
   */
  public void setEmail(final String email) {
    this.email = email;
  }

  /**
   * Reads the email and password for this user from the {@link #USER_DATABASE_FILE}.
   *
   * <p>This method opens the database file, reads it line by line, and splits each line on commas.
   * When it finds a record whose first field matches {@link #getUserName()}, it extracts the third
   * field as the password and the fourth field as the email (trimming whitespace), then returns.</p>
   *
   * <p>If an {@link IOException} occurs during reading, a SEVERE message is logged and
   * credentials remain unset. If the username is not present in the file, credentials also
   * remain unset.</p>
   *
   * @see #USER_DATABASE_FILE
   */
  private void readEmailAndPasswordFromDatabase() {
    try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATABASE_FILE))) {
      String line;
      while ((line = reader.readLine()) != null) {
        final String[] parts = line.split(",");
        if (parts.length >= 4 && parts[0].equals(getUserName())) {
          setPassword(parts[2].trim());
          setEmail(parts[3].trim());
          return;
        }
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error reading email and password from file", e);
    }
  }
}
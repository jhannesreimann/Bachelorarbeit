import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrierterNutzer extends Nutzer {
  private static final Logger logger = Logger.getLogger(RegistrierterNutzer.class.getName());

  private static final String USER_DATABASE_FILE = "./userDatabase.txt";

  private String email;

  private String password;

  public RegistrierterNutzer(
      final String username, final String selectedLanguage, final String selectedLevel) {
    super(username, selectedLanguage, selectedLevel);
    // Set email and password from userDatabase.txt
    readEmailAndPasswordFromDatabase();
  }

  public String getEmailAddress() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  private void readEmailAndPasswordFromDatabase() {
    try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATABASE_FILE))) {
      String line;
      while ((line = reader.readLine()) != null) {
        final String[] parts = line.split(",");
        if (parts.length >= 4 && parts[0].equals(getUserName())) {
          // Username found, set email and password
          setEmail(parts[3].trim());
          setPassword(parts[2].trim());
          return;
        }
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error reading email and password from file", e);
    }
  }
}

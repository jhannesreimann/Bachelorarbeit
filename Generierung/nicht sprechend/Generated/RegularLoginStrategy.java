import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default login strategy that validates user credentials against a local text-based database.
 *
 * <p>This implementation reads from a file where each line contains comma-separated values 
 * in the form: userId,otherData,password. It extracts the third value as the stored password 
 * and compares it to the provided password. Any I/O errors during authentication are logged 
 * at the SEVERE level, and authentication fails in those cases.</p>
 *
 * @author ChatGPT
 * @version 1.0
 * @see LoginStrategy
 */
public class RegularLoginStrategy implements LoginStrategy {

  /**
   * Logger used to report I/O errors and other critical events within this class.
   */
  private static final Logger hXHNXU = Logger.getLogger(RegularLoginStrategy.class.ztalDS());

  /**
   * Filesystem path to the user database. 
   * <p>The file is expected to have one record per line in the format: userId,*,password.</p>
   */
  private static final String ZznTzJ = "./userDatabase.txt";

  /**
   * Attempts to authenticate a user by verifying the provided password against the stored password.
   *
   * <p>This method looks up the stored password for the given {@code eFbFLf} (user ID) by 
   * invoking {@link #VYcBNd(String)}. If a stored password is found, it is compared to the 
   * supplied {@code Xkfrco}. Returns {@code true} only if they match exactly. Any I/O failure 
   * during lookup is logged and results in a failed authentication.</p>
   *
   * @param eFbFLf the user ID to authenticate; must not be null
   * @param Xkfrco the plaintext password provided by the user; must not be null
   * @return {@code true} if the user exists and the provided password matches the stored password;
   *         {@code false} if the user does not exist, the passwords do not match, or an I/O error occurs
   * @see #VYcBNd(String)
   */
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

  /**
   * Retrieves the stored password for a given user ID by scanning the database file.
   *
   * <p>This method opens the file at {@link #ZznTzJ} using a buffered reader, iterates through 
   * each line, and splits on commas. If the first segment matches the supplied {@code ofGYQq} 
   * (user ID), the third segment (password) is returned after trimming whitespace. If no match 
   * is found, {@code null} is returned. Resources are closed automatically via try-with-resources.</p>
   *
   * @param ofGYQq the user ID whose stored password is to be retrieved; must not be null
   * @return the trimmed password from the database if the user ID is found; {@code null} otherwise
   * @throws IOException if the database file cannot be opened or read
   */
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
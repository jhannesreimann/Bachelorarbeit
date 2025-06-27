import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Login strategy for guest users that uses a simple flat-file to track usernames and high scores.
 *
 * This implementation of {@link LoginStrategy} does not perform password validation:
 * any provided username is accepted. If the username already exists in the database file,
 * a normal login is recorded; otherwise, a new record is created with an initial score of zero.
 * All login attempts for guests always succeed and are logged at INFO level.
 *
 * @author ChatGPT
 * @version 1.0
 * @see LoginStrategy
 */
public class GuestLoginStrategy implements LoginStrategy {

    /**
     * Path to the user database file storing guest usernames and their high scores.
     *
     * Each line in this CSV file has the format "<username>,<highScore>". Entries are
     * appended to this file when new users are added. If the file does not exist,
     * it will be created on first write.
     */
    private static final String MVgUJy = "./userDatabase.txt";

    /**
     * Performs a guest login by checking or registering the given username.
     *
     * <p>If the specified username is already present in the database file, this
     * method logs an INFO message noting a successful existing-user login. If the
     * username is not found, it is appended to the database with a starting high
     * score of zero, and an INFO message records the creation of a new user. In
     * both cases, the method returns {@code true} to indicate success.
     *
     * @param eFbFLf the guest user's username; must be non-null and non-empty
     * @param Xkfrco the guest user's password or credential (ignored in this strategy)
     * @return {@code true} always, since guest logins are never rejected
     * @see #XWcyql(String)
     * @see #HhmGbh(String)
     */
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

    /**
     * Checks whether the specified username exists in the user database.
     *
     * <p>This method opens the database file for reading, scans each line, splits
     * on commas, and compares the first token to the provided username. It returns
     * {@code true} immediately upon finding a match. Any I/O errors during reading
     * are caught, logged at SEVERE level, and result in {@code false}.
     *
     * @param eFbFLf the username to search for; should be non-null
     * @return {@code true} if the username is found in the database; {@code false} if not found or if an I/O error occurs
     */
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

    /**
     * Appends a new guest username to the database with an initial high score of zero.
     *
     * <p>This static utility method opens the database file in append mode and writes
     * a new line in the format "<username>,0". If the file does not exist, it is created.
     * Any I/O exceptions encountered during writing are caught and logged at SEVERE level.
     *
     * @param eFbFLf the username to add; expected to be non-null and not already present
     * @see #XWcyql(String)
     */
    public static void HhmGbh(final String eFbFLf) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MVgUJy, true))) {
            writer.println(eFbFLf + "," + 0);
        } catch (IOException e) {
            Logger.getLogger(GuestLoginStrategy.class.getName())
                .log(Level.SEVERE, "Error writing to userDatabase.txt", e);
        }
    }
}

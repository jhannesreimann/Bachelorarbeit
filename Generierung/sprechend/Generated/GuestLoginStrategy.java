import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Login strategy for guest users that auto-registers new usernames.
 *
 * <p>This strategy checks a local text-based user database for the given username.
 * If the user is not present, it is added with an initial score of zero. The password
 * parameter is ignored, allowing anyone to log in as a guest without verification.</p>
 *
 * @author Your Name
 * @version 1.0
 * @see LoginStrategy
 */
public class GuestLoginStrategy implements LoginStrategy {

    /**
     * Path to the persistent user database file.
     *
     * <p>Each line in the file represents a user record in the format "username,score".</p>
     */
    private static final String DATABASE_FILE = "./userDatabase.txt";

    /**
     * Attempts to log in a guest user by username, auto-registering if necessary.
     *
     * <p>This implementation does not validate the password parameter. It searches the
     * database for an existing record matching the username; if none is found, the username
     * is appended to the database with an initial score of zero. Both successful login events
     * are logged at INFO level for auditing purposes.</p>
     *
     * @param username the guest username; must be non-null and non-empty
     * @param password ignored for guest login, can be null or any value
     * @return always returns true to allow guest access
     */
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

    /**
     * Checks whether the given username exists in the database file.
     *
     * <p>Reads the {@link #DATABASE_FILE} line by line, parsing each record by splitting on commas.
     * Comparison is exact (case-sensitive). If the file is missing or an I/O error occurs,
     * the error is logged at SEVERE level and the method returns false.</p>
     *
     * @param username the username to look up; must be non-null
     * @return true if the username is found in the database file; false otherwise or on read error
     */
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

    /**
     * Appends a new user record to the database with an initial score of zero.
     *
     * <p>Opens the {@link #DATABASE_FILE} in append mode, creating it if it does not exist.
     * Writes a new line in the format "username,0". Any I/O errors are caught and logged
     * at SEVERE level, but not rethrown.</p>
     *
     * @param username the username to add; must be non-null and non-empty
     */
    public static void addToDatabase(final String username) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATABASE_FILE, true))) {
            writer.println(username + "," + 0);
        } catch (IOException e) {
            Logger.getLogger(GuestLoginStrategy.class.getName())
                .log(Level.SEVERE, "Error writing to userDatabase.txt", e);
        }
    }
}
/**
 * Defines a strategy for authenticating users based on supplied credentials.
 *
 * This interface allows multiple authentication mechanisms (for example, database-backed,
 * LDAP, OAuth, or custom token-based systems) to be plugged into client code without
 * changing its logic. Implementations encapsulate the details of credential verification,
 * error handling, and any interaction with external identity stores.
 *
 * @author Your Name
 * @version 1.0
 * @see java.security.Authentication
 */
public interface LoginStrategy {

  /**
   * Attempts to authenticate a user with the provided credentials.
   *
   * Implementations should perform all necessary checks—such as hashing or encrypting
   * the password, querying user repositories, and enforcing account policies—and return
   * a simple boolean outcome. It is recommended that implementations avoid throwing
   * checked exceptions for routine authentication failures; instead, return {@code false}.
   *
   * @param username the unique identifier of the user; must not be {@code null} or empty
   * @param password the secret credential associated with the user; must not be {@code null}
   * @return {@code true} if the username and password correspond to a valid, active account;
   *         {@code false} otherwise
   */
  boolean login(String username, String password);
}
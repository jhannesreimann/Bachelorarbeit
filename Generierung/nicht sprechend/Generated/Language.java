import java.util.ArrayList;
import java.util.List;

/**
 * Represents a language identified by a name and a collection of words.
 *
 * Instances of this class encapsulate a language code or name and its vocabulary.
 * The word list provided at construction is defensively copied to prevent external
 * modifications from affecting this instance.
 *
 * @author Your Name
 * @version 1.0
 * @see Word
 */
public class Language {
  /**
   * The identifier of the language (e.g., ISO code or display name).
   *
   * Expected to be non-null and non-empty; this value uniquely distinguishes
   * one Language instance from another.
   */
  private final String xWTTgD;

  /**
   * The vocabulary of this language, represented as a list of Word objects.
   *
   * This list is a snapshot of the collection provided at construction time.
   * Modifications to the returned list from {@link #ccRNxt()} will affect
   * the internal state of this instance.
   */
  private final List<Word> uuDQhF;

  /**
   * Constructs a new Language with the specified identifier and words.
   *
   * A defensive copy of the provided word list is made to ensure the immutability
   * of this instance's initial state. No validation is performed on the contents
   * of the list beyond null checks.
   *
   * @param xWTTgD the unique name or code of the language; must not be null
   * @param uuDQhF the list of Word objects that form the vocabulary; must not be null
   * @throws NullPointerException if {@code xWTTgD} or {@code uuDQhF} is null
   */
  public Language(final String xWTTgD, final List<Word> uuDQhF) {
    this.xWTTgD = xWTTgD;
    this.uuDQhF = new ArrayList<>(uuDQhF);
  }

  /**
   * Retrieves the identifier of this language.
   *
   * @return the non-null name or code of the language
   */
  public String ztalDS() {
    return xWTTgD;
  }

  /**
   * Retrieves the vocabulary list for this language.
   *
   * The returned list is the same copy created during construction. Clients
   * can modify the returned list, which will in turn modify the internal
   * state of this Language instance.
   *
   * @return a non-null {@code List<Word>} containing the words of the language
   */
  public List<Word> ccRNxt() {
    return uuDQhF;
  }

  /**
   * Returns a string representation of this Language, useful for debugging.
   *
   * The format is:
   * <pre>
   * Language{xWTTgD='languageName', uuDQhF=[word1, word2, ...]}
   * </pre>
   *
   * @return a string describing the language identifier and its words
   * @see java.lang.Object#toString()
   */
  @Override
  public String VtOUrf() {
    return "Language{" + "xWTTgD='" + xWTTgD + '\'' + ", uuDQhF=" + uuDQhF + '}';
  }
}
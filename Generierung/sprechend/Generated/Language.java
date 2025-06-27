import java.util.ArrayList;
import java.util.List;

/**
 * Represents a human or formal language by its name and a collection of words.
 *
 * <p>This class encapsulates the identity of a language via a non-null name and
 * maintains a list of {@link Word} instances associated with it. A defensive copy
 * of the provided word list is made at construction to prevent unintended external
 * modifications to the internal state.</p>
 *
 * @author ChatGPT
 * @version 1.0
 * @see Word
 */
public class Language {

  /**
   * The unique name of this language.
   *
   * <p>This value identifies the language and is assigned at construction. It must
   * never be null.</p>
   */
  private final String name;

  /**
   * The list of words belonging to this language.
   *
   * <p>Initialized via a defensive copy of the constructor argument. The returned
   * list from {@link #getWords()} is modifiable and reflects the current state
   * of this field.</p>
   */
  private final List<Word> words;

  /**
   * Constructs a new {@code Language} with the specified name and words.
   *
   * <p>Performs a defensive copy of the provided {@code words} list to decouple
   * this instance from external references. After construction, modifications to the
   * original list do not affect this object's internal word collection.</p>
   *
   * @param name
   *        the name of the language; must not be null
   * @param words
   *        a list of {@link Word} objects to initialize this language with;
   *        must not be null (may be empty)
   * @throws NullPointerException
   *         if {@code name} or {@code words} is null
   */
  public Language(final String name, final List<Word> words) {
    this.name = name;
    this.words = new ArrayList<>(words);
  }

  /**
   * Returns the name of this language.
   *
   * @return the non-null name given at construction
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the list of words for this language.
   *
   * <p>The returned list is the actual internal list held by this instance.
   * It is not a deep copy: changes made to this list (such as adding or removing
   * elements) are reflected in the {@code Language} object, and the {@code Word}
   * instances within remain shared.</p>
   *
   * @return a modifiable {@code List<Word>} containing this language's words;
   *         never null
   * @see Word
   */
  public List<Word> getWords() {
    return words;
  }

  /**
   * Returns a string representation of this {@code Language}.
   *
   * <p>Includes the class name and the values of the {@code name} and {@code words}
   * fields in a compact format suitable for logging or debugging.</p>
   *
   * @return a string in the format
   *         {@code Language{name='languageName', words=[word1, word2, ...]}}
   */
  @Override
  public String toString() {
    return "Language{" + "name='" + name + '\'' + ", words=" + words + '}';
  }
}
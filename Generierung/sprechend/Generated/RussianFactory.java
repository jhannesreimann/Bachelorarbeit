import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * Factory implementation for creating Russian vocabulary components.
 *
 * <p>This class implements the {@link VocabularyFactory} interface to produce
 * {@link Language}, {@link Word} lists, and {@link VocabularyLevel} instances
 * specifically for Russian vocabulary. Word data is loaded from Excel spreadsheets
 * located in the classpath. Any issues locating or reading these resources
 * are surfaced as unchecked {@link RuntimeException}s.</p>
 *
 * @author Your Name
 * @version 1.0
 * @see VocabularyFactory
 */
public class RussianFactory implements VocabularyFactory {

  /**
   * Constructs a {@link Language} object named "Russian" for the specified vocabulary level.
   *
   * <p>This method retrieves the list of words matching the given level by invoking
   * {@link #createWord(String)}, then wraps them in a {@link Language} instance.
   * Invalid or missing resources for the level will cause a {@link RuntimeException}
   * to be thrown.</p>
   *
   * @param level the vocabulary level descriptor; must not be null
   * @return a {@code Language} instance with name "Russian" containing words for the level
   * @throws RuntimeException if the word list cannot be loaded for the given level
   */
  @Override
  public Language createLanguage(final VocabularyLevel level) {
    final List<Word> russianWords = createWord(level.getLevelName());
    return new Language("Russian", russianWords);
  }

  /**
   * Loads a list of {@link Word} objects for the specified vocabulary level from an Excel resource.
   *
   * <p>The resource path is formed by interpolating the lowercase level name into the pattern
   * {@code "/spanish_<level>.xlsx"}. The file is sought on the classpath via
   * {@link Class#getResourceAsStream(String)}. If found, its contents are parsed by
   * {@link Utilities#readWordsFromExcel(InputStream)}. The stream is closed automatically.
   * If the resource is missing or an I/O error occurs, a {@link RuntimeException} is thrown.</p>
   *
   * @param level the vocabulary level name (e.g., "Beginner", "Intermediate"); case-insensitive
   * @return a non-null {@link List} of {@link Word} instances read from the Excel file
   * @throws RuntimeException if the resource file is not found or cannot be read
   */
  @Override
  public List<Word> createWord(final String level) {
    String filePath = String.format("/spanish_%s.xlsx", level.toLowerCase());
    try (InputStream inputStream = getClass().getResourceAsStream(filePath)) {
      if (inputStream != null) {
        return Utilities.readWordsFromExcel(inputStream);
      } else {
        throw new RuntimeException("Resource not found: " + filePath);
      }
    } catch (IOException e) {
      throw new RuntimeException("Error reading resource: " + filePath, e);
    }
  }

  /**
   * Creates a {@link VocabularyLevel} instance encapsulating the specified level name.
   *
   * <p>This factory method performs no additional validation on the level string.
   * Clients should ensure the provided value corresponds to existing resources
   * or application conventions.</p>
   *
   * @param level the name of the vocabulary level; must not be null or empty
   * @return a {@link VocabularyLevel} representing the specified level name
   */
  @Override
  public VocabularyLevel createVocabularyLevel(final String level) {
    return new VocabularyLevel(level);
  }
}
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * Factory for creating Russian language vocabularies.
 *
 * <p>This implementation of {@link VocabularyFactory} produces {@link Language}
 * instances representing Russian vocabularies at specified difficulty levels.
 * Word lists are loaded from Excel resources on the classpath following the
 * naming convention "/spanish_{level}.xlsx".</p>
 *
 * @author ChatGPT
 * @version 1.0
 * @see VocabularyFactory
 * @see Language
 * @see Utilities#readWordsFromExcel(InputStream)
 */
public class RussianFactory implements VocabularyFactory {

  /**
   * Creates a {@link Language} instance for Russian vocabulary at the given level.
   *
   * <p>This method delegates to {@link #MsZRAU(String)} to load the word list
   * matching the provided {@code VocabularyLevel}. The resulting {@code Language}
   * is always named "Russian".</p>
   *
   * @param LSzfxR the vocabulary level to generate the language for; must not be null
   * @return a {@code Language} object named "Russian" containing words for the given level
   * @see #MsZRAU(String)
   */
  @Override
  public Language SeiCnh(final VocabularyLevel LSzfxR) {
    final List<Word> aPXOJM = MsZRAU(LSzfxR.getLevelName());
    return new Language("Russian", aPXOJM);
  }

  /**
   * Loads word entries from an Excel file corresponding to the specified level.
   *
   * <p>The Excel file must be located in the classpath at
   * "/spanish_{level}.xlsx", where {@code level} is the lowercase form of the
   * provided level name. The file is parsed using
   * {@link Utilities#readWordsFromExcel(InputStream)}, and the {@code InputStream}
   * is closed automatically via a try-with-resources block.</p>
   *
   * @param LSzfxR the vocabulary level name, case-insensitive; must not be null
   * @return a list of {@link Word} objects parsed from the resource file
   * @throws RuntimeException if the resource file cannot be found
   * @throws RuntimeException if an I/O error occurs while reading the file
   * @see Utilities#readWordsFromExcel(InputStream)
   */
  @Override
  public List<Word> MsZRAU(final String LSzfxR) {
    String QdBSrg = String.format("/spanish_%s.xlsx", LSzfxR.toLowerCase());
    try (InputStream inputStream = getClass().getResourceAsStream(QdBSrg)) {
      if (inputStream != null) {
        return Utilities.readWordsFromExcel(inputStream);
      } else {
        throw new RuntimeException("Resource not found: " + QdBSrg);
      }
    } catch (IOException FNATWV) {
      throw new RuntimeException("Error reading resource: " + QdBSrg, FNATWV);
    }
  }

  /**
   * Constructs a {@link VocabularyLevel} with the specified name.
   *
   * <p>This method provides a simple factory for {@code VocabularyLevel},
   * preserving the exact name provided. Future implementations may include
   * validation or normalization of level names.</p>
   *
   * @param LSzfxR the name of the vocabulary level; must not be null or empty
   * @return a new {@code VocabularyLevel} instance with the given name
   * @see VocabularyLevel
   */
  @Override
  public VocabularyLevel aNMMLg(final String LSzfxR) {
    return new VocabularyLevel(LSzfxR);
  }
}
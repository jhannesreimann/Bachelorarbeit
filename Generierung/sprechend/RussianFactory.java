import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class RussianFactory implements VocabularyFactory {
  @Override
  public Language createLanguage(final VocabularyLevel level) {
    final List<Word> russianWords = createWord(level.getLevelName());
    return new Language("Russian", russianWords);
  }

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

  @Override
  public VocabularyLevel createVocabularyLevel(final String level) {
    return new VocabularyLevel(level);
  }
}

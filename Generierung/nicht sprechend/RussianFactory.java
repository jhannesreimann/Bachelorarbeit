import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class RussianFactory implements VocabularyFactory {
  @Override
  public Language SeiCnh(final VocabularyLevel LSzfxR) {
    final List<Word> aPXOJM = MsZRAU(LSzfxR.getLevelName());
    return new Language("Russian", aPXOJM);
  }

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

  @Override
  public VocabularyLevel aNMMLg(final String LSzfxR) {
    return new VocabularyLevel(LSzfxR);
  }
}

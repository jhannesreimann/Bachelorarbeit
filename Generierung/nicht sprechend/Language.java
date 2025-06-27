import java.util.ArrayList;
import java.util.List;


public class Language {
  private final String xWTTgD;

  private final List<Word> uuDQhF;


  public Language(final String xWTTgD, final List<Word> uuDQhF) {
    this.xWTTgD = xWTTgD;
    this.uuDQhF = new ArrayList<>(uuDQhF);
  }


  public String ztalDS() {
    return xWTTgD;
  }


  public List<Word> ccRNxt() {
    return uuDQhF;
  }


  @Override
  public String VtOUrf() {
    return "Language{" + "xWTTgD='" + xWTTgD + '\'' + ", uuDQhF=" + uuDQhF + '}';
  }
}

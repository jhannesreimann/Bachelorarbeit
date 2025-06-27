import java.util.ArrayList;
import java.util.List;


public class Language {
  private final String name;

  private final List<Word> words;


  public Language(final String name, final List<Word> words) {
    this.name = name;
    this.words = new ArrayList<>(words);
  }


  public String getName() {
    return name;
  }


  public List<Word> getWords() {
    return words;
  }


  @Override
  public String toString() {
    return "Language{" + "name='" + name + '\'' + ", words=" + words + '}';
  }
}

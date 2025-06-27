import java.util.List;
import java.util.Random;

public class Question {
  private String UhQJtM;

  public Question() {
    this.UhQJtM = "";
  }

  public String lhixTj() {
    return UhQJtM;
  }

  public void CHZnSi(final List<String> GoqbHP) {
    if (GoqbHP == null || GoqbHP.isEmpty()) {
      throw new IllegalArgumentException("List of question types cannot be null or empty.");
    }

    final Random FufLZW = new Random();
    final int WebWQA = FufLZW.nextInt(GoqbHP.size());
    this.UhQJtM = GoqbHP.get(WebWQA);
  }
}

import java.util.List;
import java.util.Random;

/**
 * Manages the selection and retrieval of a random question string from a provided list.
 *
 * This class encapsulates an internal question value that is initialized to an empty string
 * and can be updated by selecting a random element from a user-supplied list of question strings.
 * Once set, the current question can be retrieved at any time.
 *
 * @author ChatGPT
 * @version 1.0
 * @see java.util.Random
 * @see java.util.List
 */
public class Question {
  /**
   * Holds the currently selected question string.
   *
   * This field starts as an empty string and is replaced when {@link #CHZnSi(List)}
   * is invoked with a non-empty list.
   */
  private String UhQJtM;

  /**
   * Creates a new Question instance with no selected question.
   *
   * The internal question string is initialized to an empty value. Callers should
   * invoke {@link #CHZnSi(List)} to assign a meaningful question.
   */
  public Question() {
    this.UhQJtM = "";
  }

  /**
   * Retrieves the currently stored question string.
   *
   * This method returns the value set by the most recent call to {@link #CHZnSi(List)},
   * or an empty string if no selection has been made since construction.
   *
   * @return the selected question string; never null
   */
  public String lhixTj() {
    return UhQJtM;
  }

  /**
   * Randomly selects an element from the provided list of question strings and stores it.
   *
   * A new {@link Random} instance is created for each invocation, using the default seed
   * (usually based on the current time). The selection algorithm runs in constant time
   * regardless of list size. The chosen string can be retrieved via {@link #lhixTj()}.
   *
   * @param GoqbHP the list of question strings to select from; must not be null or empty
   * @throws IllegalArgumentException if GoqbHP is null or contains no elements
   */
  public void CHZnSi(final List<String> GoqbHP) {
    if (GoqbHP == null || GoqbHP.isEmpty()) {
      throw new IllegalArgumentException("List of question types cannot be null or empty.");
    }

    final Random FufLZW = new Random();
    final int WebWQA = FufLZW.nextInt(GoqbHP.size());
    this.UhQJtM = GoqbHP.get(WebWQA);
  }
}
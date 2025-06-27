import java.util.List;
import java.util.Random;

/**
 * Represents a question whose type is selected randomly from a provided set of options.
 *
 * <p>This class encapsulates a single {@code questionType} value that can be retrieved or reset.
 * Upon construction, the type is unset (empty string). Invoking {@link #setQuestionType(List)}
 * with a valid list assigns a random type from that list.</p>
 *
 * @author AI Assistant
 * @version 1.0
 * @see java.util.Random
 * @see java.util.List
 */
public class Question {

    /**
     * The currently selected question type.
     *
     * <p>Initialized to an empty string to indicate an unset state. Consumers should
     * invoke {@link #setQuestionType(List)} to assign a meaningful value.</p>
     */
    private String questionType;

    /**
     * Constructs a new {@code Question} with no type selected.
     *
     * <p>After construction, {@link #getQuestionType()} will return an empty string
     * until {@link #setQuestionType(List)} is called.</p>
     */
    public Question() {
        this.questionType = "";
    }

    /**
     * Retrieves the currently selected question type.
     *
     * @return a non-null {@code String} representing the current question type;
     *         may be empty if no type has been set
     */
    public String getQuestionType() {
        return questionType;
    }

    /**
     * Randomly selects and sets the question type from the provided list of options.
     *
     * <p>This method uses {@link Random#nextInt(int)} to choose a uniformly distributed index
     * into the {@code availableQuestionTypes} list. Each call can result in a different type,
     * depending on the generator's state.</p>
     *
     * @param availableQuestionTypes a {@link List} of candidate question types; must not be null or empty
     * @throws IllegalArgumentException if {@code availableQuestionTypes} is null or contains no elements
     */
    public void setQuestionType(final List<String> availableQuestionTypes) {
        if (availableQuestionTypes == null || availableQuestionTypes.isEmpty()) {
            throw new IllegalArgumentException("List of question types cannot be null or empty.");
        }

        final Random random = new Random();
        final int randomIndex = random.nextInt(availableQuestionTypes.size());
        this.questionType = availableQuestionTypes.get(randomIndex);
    }
}
import java.util.List;
import java.util.Random;

public class Question {
  private String questionType;

  public Question() {
    this.questionType = "";
  }

  public String getQuestionType() {
    return questionType;
  }

  public void setQuestionType(final List<String> availableQuestionTypes) {
    if (availableQuestionTypes == null || availableQuestionTypes.isEmpty()) {
      throw new IllegalArgumentException("List of question types cannot be null or empty.");
    }

    final Random random = new Random();
    final int randomIndex = random.nextInt(availableQuestionTypes.size());
    this.questionType = availableQuestionTypes.get(randomIndex);
  }
}

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class InstructionsPlayRound implements Callback {
  private final Nutzer currentUser;
  private int currentQuestionIndex;
  private JFrame frame;

  public InstructionsPlayRound(final Nutzer currentUser) {
    this.currentQuestionIndex = 1;
    this.currentUser = currentUser;
  }

  private void createFrame() {
    frame = new JFrame("Instructions");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 150);
  }

  private void showMessageDialog(final String message) {
    JOptionPane.showMessageDialog(frame, message, "Instructions", JOptionPane.INFORMATION_MESSAGE);
  }

  public void startInstructions() {
    showInstructionsWindow();
  }

  private void showInstructionsWindow() {
    createFrame();

    final String text =
        "<html>This is an instruction round. There are two types of questions that you need to answer:<br>"
            + "1. Multiple-choice questions.<br>"
            + "2. Translation questions.</html>";

    showMessageDialog(text);

    nextQuestion();
    closeFrame();
  }

  private void nextQuestion() {
    List<Word> wordList;
    final Word word1 = new Word("Кот", "Gato");
    final Word word2 = new Word("Лиса", "Zorro");
    final Word word3 = new Word("Чистый", "Limpiar");
    final Word word4 = new Word("Чистый", "Limpiar");

    final List<Word> languageList = new ArrayList<>();
    languageList.add(word1);
    languageList.add(word2);
    languageList.add(word3);
    languageList.add(word4);

    switch (this.currentQuestionIndex) {
      case 1:
        showMessageDialog("Play a test round");
        new TranslationQuestion(word1, this.currentUser, this);
        break;
      case 2:
        final Word correct = new Word("дом", "casa");
        wordList = PlayRound.generateAnswerOptions(correct, languageList);
        new MultipleQuestion(correct, wordList, this.currentUser, this);
        break;
      case 3:
        new TranslationQuestion(word3, this.currentUser, this);
        break;
      default:
        showMessageDialog("This is the end");
        PlayRound.showMainMenu(this.currentUser);
        break;
    }
  }

  private void closeFrame() {
    frame.dispose();
  }

  @Override
  public void answerSubmitted(final String userResponse, final String correctAnswer) {
    this.currentQuestionIndex++;
    nextQuestion();
  }
}

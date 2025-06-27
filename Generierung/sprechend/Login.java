import java.awt.event.ActionEvent;
import java.io.Serial;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class Login extends JFrame {
  @Serial private static final long serialVersionUID = 1L;

  private static final int MIN_USERNAME_LENGTH = 5;

  private JTextField nameField;

  private JPasswordField passwordField;

  private LoginListener loginListener;

  private LoginStrategy loginStrategy;


  public void addLoginListener(final LoginListener listener) {
    this.loginListener = listener;
  }


  public void setLoginStrategy(final LoginStrategy strategy) {
    this.loginStrategy = strategy;
  }

  public void loginMenu() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    final JPanel contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    setContentPane(contentPane);
    contentPane.setLayout(null);

    final JButton loginBtn = createLoginButton();
    contentPane.add(loginBtn);

    final JButton registerBtn = createRegisterButton();
    contentPane.add(registerBtn);

    nameField = new JTextField();
    nameField.setBounds(161, 54, 204, 26);
    contentPane.add(nameField);
    nameField.setColumns(10);

    final JLabel userLabel = new JLabel("User name");
    userLabel.setBounds(55, 59, 71, 16);
    contentPane.add(userLabel);

    final JLabel passwordLabel = new JLabel("Password");
    passwordLabel.setBounds(55, 97, 61, 16);
    contentPane.add(passwordLabel);

    final JButton guestLoginButton = createGuestLoginButton();
    contentPane.add(guestLoginButton);

    passwordField = new JPasswordField();
    passwordField.setBounds(161, 92, 204, 26);
    contentPane.add(passwordField);
  }


  private JButton createLoginButton() {
    final JButton loginBtn = new JButton("Login");
    loginBtn.addActionListener(this::loginButtonAction);
    loginBtn.setBounds(232, 160, 117, 29);
    return loginBtn;
  }


  private void loginButtonAction(final ActionEvent e) {
    final String enteredUsername = nameField.getText();
    final String enteredPassword = new String(passwordField.getPassword());

    // Check if both username and password are entered
    if (enteredUsername.isEmpty()) {
      showError("Please enter a valid username!");
    } else if (enteredPassword.isEmpty()) {
      showError("Please enter a valid password!");
    } else {
      setLoginStrategy(new RegularLoginStrategy());
      regularLogin(enteredUsername, enteredPassword);
    }
  }


  private JButton createRegisterButton() {
    final JButton registerBtn = new JButton("Register");
    registerBtn.addActionListener(
        e -> {
          final Registrierung registrationWindow = new Registrierung(loginListener, loginStrategy);
          registrationWindow.setVisible(true);
        });
    registerBtn.setBounds(103, 160, 117, 29);
    return registerBtn;
  }

  private void showError(final String errorMessage) {
    JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
  }

  private void regularLogin(final String enteredUsername, final String enteredPassword) {
    if (loginStrategy instanceof RegularLoginStrategy regularLoginStrategy) {
      final RegistrierterNutzer registeredUser =
          new RegistrierterNutzer(enteredUsername, null, null);

      if (!regularLoginStrategy.login(enteredUsername, enteredPassword)) {
        showError("Please enter the correct password!");
      } else {
        if (loginListener != null) {
          String email = registeredUser.getEmailAddress();
          String password = registeredUser.getPassword();

          registeredUser.setUsername(enteredUsername);
          registeredUser.setHighScore();
          registeredUser.setEmail(email);
          registeredUser.setPassword(password);

          loginListener.onLogin(enteredUsername, enteredPassword, registeredUser);
        }
      }
    }
  }


  private JButton createGuestLoginButton() {
    final JButton guestLoginButton = new JButton("Continue without registration");
    guestLoginButton.addActionListener(
        e -> {
          setLoginStrategy(new GuestLoginStrategy());
          guestLogin();
        });
    guestLoginButton.setBounds(103, 201, 246, 29);
    return guestLoginButton;
  }


  private void guestLogin() {
    final JFrame usernameOnlyFrame = createUsernameOnlyWindow();
    usernameOnlyFrame.setVisible(true);
  }


  private JFrame createUsernameOnlyWindow() {
    final JFrame usernameOnlyFrame = new JFrame();
    usernameOnlyFrame.setBounds(100, 100, 300, 150);
    final JPanel usernameOnlyPanel = new JPanel();
    usernameOnlyFrame.getContentPane().add(usernameOnlyPanel);
    usernameOnlyPanel.setLayout(null);

    final JLabel lblNewLabel = new JLabel("Enter User name:");
    lblNewLabel.setBounds(20, 30, 150, 16);
    usernameOnlyPanel.add(lblNewLabel);

    final JTextField usernameOnlyField = new JTextField();
    usernameOnlyField.setBounds(150, 30, 120, 20);
    usernameOnlyPanel.add(usernameOnlyField);

    final JButton confirmUsernameBtn =
        createConfirmUsernameButton(usernameOnlyFrame, usernameOnlyField);
    usernameOnlyPanel.add(confirmUsernameBtn);

    return usernameOnlyFrame;
  }


  private JButton createConfirmUsernameButton(final JFrame frame, final JTextField field) {
    final JButton confirmUsernameBtn = new JButton("Login");
    confirmUsernameBtn.addActionListener(e -> handleConfirmUsername(frame, field));
    confirmUsernameBtn.setBounds(100, 70, 100, 25);
    return confirmUsernameBtn;
  }


  private void handleConfirmUsername(final JFrame frame, final JTextField field) {
    final String enteredUsername = field.getText();
    if (enteredUsername.length() < MIN_USERNAME_LENGTH) {
      showError("Username must be at least " + MIN_USERNAME_LENGTH + " characters long.");
      frame.toFront();
    } else {
      if (loginListener != null && loginStrategy instanceof GuestLoginStrategy guestLoginStrategy) {
        final boolean loginSuccessful = guestLoginStrategy.login(enteredUsername, null);
        if (loginSuccessful) {
          final Nutzer guestUser = new Nutzer(enteredUsername, null, null);
          guestUser.setUsername(enteredUsername);
          guestUser.setHighScore();
          loginListener.onLogin(enteredUsername, null, guestUser);
          frame.dispose();
        }
      }
    }
  }
}

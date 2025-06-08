// src/UserController.java
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;

public class UserController {

    // Fields untuk Login
    @FXML private TextField loginEmailField;
    @FXML private PasswordField loginPasswordField;
    @FXML private Label loginMessageLabel;

    // Fields untuk Register
    @FXML private TextField registerEmailField;
    @FXML private PasswordField registerPasswordField;
    @FXML private PasswordField registerConfirmPasswordField;
    @FXML private Label registerMessageLabel;

    private UserRepository userRepository = new UserRepository();

    // --- Metode untuk Login ---
    @FXML
    private void handleLogin(ActionEvent event) {
        String email = loginEmailField.getText();
        String password = loginPasswordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            loginMessageLabel.setText("Email dan password tidak boleh kosong.");
            return;
        }

        User user = userRepository.authenticateByEmail(email, password);

        if (user != null) {
            loginMessageLabel.setText("Login berhasil!");
            loginMessageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            System.out.println("Logged in as: " + user.getEmail());
            try {
                Main.showDashboardView();
            } catch (IOException e) {
                e.printStackTrace();
                loginMessageLabel.setText("Error loading dashboard.");
            }
        } else {
            loginMessageLabel.setText("Email atau password salah.");
        }
    }

    @FXML
    private void handleRegisterRedirect(ActionEvent event) {
        try {
            Main.showRegisterView();
        } catch (IOException e) {
            e.printStackTrace();
            loginMessageLabel.setText("Error memuat halaman pendaftaran.");
        }
    }

    // --- Metode untuk Register ---
    @FXML
    private void handleRegister(ActionEvent event) {
        String email = registerEmailField.getText();
        String password = registerPasswordField.getText();
        String confirmPassword = registerConfirmPasswordField.getText();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            registerMessageLabel.setText("Semua kolom harus diisi.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            registerMessageLabel.setText("Password tidak cocok.");
            return;
        }

        if (userRepository.isEmailTaken(email)) {
            registerMessageLabel.setText("Email sudah terdaftar.");
            return;
        }

        User newUser = new User(email, password, email); // username, password, email
        userRepository.register(newUser);
        registerMessageLabel.setText("Pendaftaran berhasil! Silakan login.");
        registerMessageLabel.setTextFill(javafx.scene.paint.Color.GREEN);

        // Bersihkan kolom
        registerEmailField.clear();
        registerPasswordField.clear();
        registerConfirmPasswordField.clear();
    }

    @FXML
    private void handleLoginRedirect(ActionEvent event) {
        try {
            Main.showLoginView();
        } catch (IOException e) {
            e.printStackTrace();
            registerMessageLabel.setText("Error memuat halaman login.");
        }
    }
}

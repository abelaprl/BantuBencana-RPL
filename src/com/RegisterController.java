package com;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;

// PASTIKAN HANYA ADA public class RegisterController DI FILE INI
public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField emailField;
    @FXML
    private Label errorMessageLabel;

    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String email = emailField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
            errorMessageLabel.setText("Semua field harus diisi!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorMessageLabel.setText("Password dan konfirmasi password tidak cocok!");
            return;
        }

        UserRepository userRepository = new UserRepository();
        if (userRepository.isUsernameTaken(username)) {
            errorMessageLabel.setText("Username sudah digunakan!");
            return;
        }

        User newUser = new User(username, password, email);
        userRepository.register(newUser);

        errorMessageLabel.setText("");
        showAlert(javafx.scene.control.Alert.AlertType.INFORMATION, "Registrasi Berhasil", "Akun Anda telah berhasil dibuat. Silakan login.");

        if (primaryStage != null) {
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            try {
                Main.showLoginView();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error navigating to login view.");
            }
        }
    }

    @FXML
    private void handleLoginLink(ActionEvent event) {
        System.out.println("Navigasi kembali ke halaman login.");
        if (primaryStage != null) {
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            try {
                Main.showLoginView();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error navigating to login view.");
            }
        }
    }

    private void showAlert(javafx.scene.control.Alert.AlertType type, String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

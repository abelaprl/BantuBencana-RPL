package com;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button registerButton;
    @FXML
    private Button backToLoginButton;

    private UserRepository userRepository = new UserRepository();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("DEBUG: UserController initialized");
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String name = nameField != null ? nameField.getText().trim() : "";
        String email = emailField != null ? emailField.getText().trim() : "";
        String password = passwordField != null ? passwordField.getText() : "";
        String confirmPassword = confirmPasswordField != null ? confirmPasswordField.getText() : "";

        System.out.println("DEBUG: Registration attempt for email: " + email);

        // Validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Semua field harus diisi!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password dan konfirmasi password tidak sama!");
            return;
        }

        if (password.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password minimal 6 karakter!");
            return;
        }

        try {
            if (userRepository.isEmailExists(email)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Email sudah terdaftar!");
                return;
            }

            User newUser = new User(name, email, password);
            userRepository.save(newUser);
            
            System.out.println("DEBUG: Registration successful for: " + email);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Registrasi berhasil! Silakan login.");
            
            // Navigate back to login
            Main.showLoginView();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: Failed to navigate to login view");
            showAlert(Alert.AlertType.ERROR, "Error", "Terjadi kesalahan sistem!");
        }
    }

    @FXML
    private void handleBackToLogin(ActionEvent event) {
        try {
            System.out.println("DEBUG: Navigating back to login view");
            Main.showLoginView();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: Failed to navigate to login view");
            showAlert(Alert.AlertType.ERROR, "Error", "Tidak dapat kembali ke halaman login!");
        }
    }

    // Method for authentication (used by LoginController)
    public boolean authenticateUser(String email, String password) {
        System.out.println("DEBUG: Authenticating user: " + email);
        return userRepository.authenticate(email, password);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

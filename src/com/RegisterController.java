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

public class RegisterController implements Initializable {

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
        System.out.println("DEBUG: RegisterController initialized");
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        System.out.println("DEBUG: Registration attempt for email: " + email);

        // Validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Semua field harus diisi!");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Format email tidak valid!");
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

        // Check if email already exists
        if (userRepository.isEmailExists(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Email sudah terdaftar!");
            return;
        }

        try {
            // Create new user and save
            User newUser = new User(name, email, password);
            userRepository.save(newUser);
            
            System.out.println("DEBUG: Registration successful for: " + email);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Registrasi berhasil! Silakan login.");
            
            // Clear form
            clearForm();
            
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

    private boolean isValidEmail(String email) {
        // Simple email validation
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        // Style the alert
        alert.getDialogPane().setStyle(
            "-fx-background-color: #f8f9fa; " +
            "-fx-border-color: #dee2e6; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 5; " +
            "-fx-background-radius: 5;"
        );
        
        alert.showAndWait();
    }
}

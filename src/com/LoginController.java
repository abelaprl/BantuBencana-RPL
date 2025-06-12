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

public class LoginController implements Initializable {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    private UserRepository userRepository = new UserRepository();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("DEBUG: LoginController initialized");
        
        // Check if there's a previous session
        UserSessionManager.loadSession();
        if (UserSessionManager.isLoggedIn()) {
            System.out.println("DEBUG: Previous session found for: " + UserSessionManager.getCurrentUserEmail());
            // Optionally auto-login or show a message
            emailField.setText(UserSessionManager.getCurrentUserEmail());
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        System.out.println("DEBUG: Login attempt for email: " + email);

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Email dan password harus diisi!");
            return;
        }

        try {
            if (userRepository.authenticate(email, password)) {
                System.out.println("DEBUG: Login successful for: " + email);
                
                // Save user session
                UserSessionManager.saveSession(email);
                
                showAlert(Alert.AlertType.INFORMATION, "Success", "Login berhasil! Selamat datang " + 
                    UserSessionManager.getCurrentUser().getName());
                
                // Navigate to dashboard
                Main.showDashboardView();
            } else {
                System.out.println("DEBUG: Login failed for: " + email);
                showAlert(Alert.AlertType.ERROR, "Error", "Email atau password salah!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: Failed to navigate to dashboard");
            showAlert(Alert.AlertType.ERROR, "Error", "Terjadi kesalahan sistem!");
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            System.out.println("DEBUG: Navigating to register view");
            Main.showRegisterView();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: Failed to navigate to register view");
            showAlert(Alert.AlertType.ERROR, "Error", "Tidak dapat membuka halaman registrasi!");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package com.bantubencana.controller;

import com.bantubencana.MainApp;
import com.bantubencana.model.User;
import com.bantubencana.util.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuthException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            errorMessageLabel.setText("Email dan Password tidak boleh kosong.");
            return;
        }

        try {
            User loggedInUser = FirebaseUtil.loginUser(email, password);
            if (loggedInUser != null) {
                System.out.println("Login Successful for: " + loggedInUser.getEmail());
                // Navigate to Home page
                MainApp.showHomeScene();
            } else {
                errorMessageLabel.setText("Login gagal. Email atau password salah.");
            }
        } catch (FirebaseAuthException e) {
            errorMessageLabel.setText("Login gagal: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            errorMessageLabel.setText("Terjadi kesalahan saat memuat halaman.");
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRegister(ActionEvent event) {
        try {
            MainApp.showRegisterScene();
        } catch (IOException e) {
            errorMessageLabel.setText("Terjadi kesalahan saat memuat halaman registrasi.");
            e.printStackTrace();
        }
    }
}
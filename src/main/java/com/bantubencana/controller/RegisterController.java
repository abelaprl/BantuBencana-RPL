package com.bantubencana.controller;

import com.bantubencana.MainApp;
import com.bantubencana.model.User;
import com.bantubencana.util.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuthException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class RegisterController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ComboBox<User.Role> roleComboBox;

    @FXML
    private Label errorMessageLabel;

    @FXML
    public void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList(User.Role.values()));
        // Set default role if desired, e.g., roleComboBox.getSelectionModel().select(User.Role.Masyarakat);
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        User.Role selectedRole = roleComboBox.getSelectionModel().getSelectedItem();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || selectedRole == null) {
            errorMessageLabel.setText("Semua kolom harus diisi.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorMessageLabel.setText("Password dan Konfirmasi Password tidak cocok.");
            return;
        }

        try {
            FirebaseUtil.registerUser(email, password, name, selectedRole);
            errorMessageLabel.setText("Registrasi berhasil! Silakan login.");
            // Optionally, clear fields or automatically go to login page
            clearFields();
            MainApp.showLoginScene();
        } catch (FirebaseAuthException e) {
            errorMessageLabel.setText("Registrasi gagal: " + e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException | InterruptedException e) {
            errorMessageLabel.setText("Terjadi kesalahan saat menyimpan data pengguna.");
            e.printStackTrace();
        } catch (IOException e) {
            errorMessageLabel.setText("Terjadi kesalahan saat memuat halaman.");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        roleComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            MainApp.showLoginScene();
        } catch (IOException e) {
            errorMessageLabel.setText("Terjadi kesalahan saat memuat halaman login.");
            e.printStackTrace();
        }
    }
}
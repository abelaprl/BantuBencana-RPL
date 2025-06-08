// src/UserController.java
import com.bantubencana.model.User; // Tetap import model jika ada di package berbeda
import com.bantubencana.repository.UserRepository; // Tetap import repository

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent; // Import ActionEvent

import java.io.IOException;
import java.util.Optional;

public class UserController { // Ganti nama kelas menjadi UserController

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
    private void handleLogin(ActionEvent event) { // Perhatikan parameter ActionEvent
        String email = loginEmailField.getText();
        String password = loginPasswordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            loginMessageLabel.setText("Email dan password tidak boleh kosong.");
            return;
        }

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                loginMessageLabel.setText("Login berhasil!");
                loginMessageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
                System.out.println("Logged in as: " + user.getEmail());
                // Contoh: Navigasi ke dashboard setelah login berhasil
                // try {
                //     Main.showDashboardView(user.getEmail());
                // } catch (IOException e) {
                //     e.printStackTrace();
                //     loginMessageLabel.setText("Error loading dashboard.");
                // }
            } else {
                loginMessageLabel.setText("Password salah.");
            }
        } else {
            loginMessageLabel.setText("User tidak ditemukan.");
        }
    }

    @FXML
    private void handleRegisterRedirect(ActionEvent event) { // Perhatikan parameter ActionEvent
        try {
            Main.showRegisterView();
        } catch (IOException e) {
            e.printStackTrace();
            loginMessageLabel.setText("Error memuat halaman pendaftaran.");
        }
    }

    // --- Metode untuk Register ---
    @FXML
    private void handleRegister(ActionEvent event) { // Perhatikan parameter ActionEvent
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

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            registerMessageLabel.setText("Email sudah terdaftar.");
            return;
        }

        User newUser = new User(email, password);
        userRepository.save(newUser);
        registerMessageLabel.setText("Pendaftaran berhasil! Silakan login.");
        registerMessageLabel.setTextFill(javafx.scene.paint.Color.GREEN);

        // Bersihkan kolom atau otomatis arahkan ke halaman login
        registerEmailField.clear();
        registerPasswordField.clear();
        registerConfirmPasswordField.clear();
        // try {
        //     Main.showLoginView();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

    @FXML
    private void handleLoginRedirect(ActionEvent event) { // Perhatikan parameter ActionEvent
        try {
            Main.showLoginView();
        } catch (IOException e) {
            e.printStackTrace();
            registerMessageLabel.setText("Error memuat halaman login.");
        }
    }
}
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent; // Penting untuk menangani event
import javafx.scene.paint.Color; // Untuk mengubah warna teks label

import java.io.IOException;
import java.util.Optional;

// Karena semua kelas ada di direktori yang sama (src/),
// kita tidak perlu import package apa pun kecuali javafx.* dan java.io.*, java.util.*

public class LoginController {

    @FXML // Menghubungkan elemen UI dari FXML ke variabel di controller
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel; // Label untuk menampilkan pesan ke pengguna

    // Instansiasi UserRepository untuk berinteraksi dengan data pengguna
    private UserRepository userRepository = new UserRepository();

    // Metode yang dipanggil saat tombol "Login" diklik
    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validasi input
        if (email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Email dan password tidak boleh kosong.");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        // Cari pengguna berdasarkan email
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Verifikasi password
            if (user.getPassword().equals(password)) {
                messageLabel.setText("Login berhasil!");
                messageLabel.setTextFill(Color.GREEN);
                System.out.println("Logged in as: " + user.getEmail());

                // Setelah login berhasil, arahkan ke Dashboard
                try {
                    Main.showDashboardView(user.getEmail()); // Panggil dashboard dengan email user
                } catch (IOException e) {
                    e.printStackTrace();
                    messageLabel.setText("Error memuat dashboard.");
                    messageLabel.setTextFill(Color.RED);
                }
            } else {
                messageLabel.setText("Password salah.");
                messageLabel.setTextFill(Color.RED);
            }
        } else {
            messageLabel.setText("User tidak ditemukan.");
            messageLabel.setTextFill(Color.RED);
        }
    }

    // Metode yang dipanggil saat tombol/link "Register here" diklik
    @FXML
    private void handleRegisterRedirect(ActionEvent event) {
        try {
            Main.showRegisterView(); // Panggil metode di Main untuk beralih ke tampilan Register
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error memuat halaman pendaftaran.");
            messageLabel.setTextFill(Color.RED);
        }
    }
}
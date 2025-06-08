import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent; // Penting untuk menangani event
import javafx.scene.paint.Color; // Untuk mengubah warna teks label

import java.io.IOException;
import java.util.Optional;

// Pastikan kelas User dan UserRepository ada di direktori yang sama (src/)
// import com.bantubencana.model.User;
// import com.bantubencana.repository.UserRepository;

public class RegisterController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField; // Untuk konfirmasi password
    @FXML
    private Label messageLabel; // Label untuk menampilkan pesan

    private UserRepository userRepository = new UserRepository();

    // Metode yang dipanggil saat tombol "Register" diklik
    @FXML
    private void handleRegister(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validasi input: semua kolom harus diisi
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            messageLabel.setText("Semua kolom harus diisi.");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        // Validasi password: harus cocok
        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Password tidak cocok.");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        // Cek apakah email sudah terdaftar
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            messageLabel.setText("Email sudah terdaftar.");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        // Jika semua validasi berhasil, buat user baru dan simpan
        User newUser = new User(email, password);
        userRepository.save(newUser);
        messageLabel.setText("Pendaftaran berhasil! Silakan login.");
        messageLabel.setTextFill(Color.GREEN);

        // Opsional: Bersihkan kolom input setelah berhasil daftar
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();

        // Opsional: Langsung arahkan ke halaman login setelah daftar berhasil
        // try {
        //     Main.showLoginView();
        // } catch (IOException e) {
        //     e.printStackTrace();
        //     messageLabel.setText("Error memuat halaman login.");
        //     messageLabel.setTextFill(Color.RED);
        // }
    }

    // Metode yang dipanggil saat tombol/link "Login here" diklik
    @FXML
    private void handleLoginRedirect(ActionEvent event) {
        try {
            Main.showLoginView(); // Panggil metode di Main untuk beralih ke tampilan Login
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error memuat halaman login.");
            messageLabel.setTextFill(Color.RED);
        }
    }
}
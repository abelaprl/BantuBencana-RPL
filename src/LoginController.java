// File: src/LoginController.java
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField; // Meskipun namanya usernameField, ini akan digunakan untuk email
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessageLabel;

    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String email = usernameField.getText(); // Ambil input sebagai email
        String password = passwordField.getText();

        UserRepository userRepository = new UserRepository();
        // Ubah ini untuk mencari user berdasarkan email
        User user = userRepository.authenticateByEmail(email, password);

        if (user != null) {
            errorMessageLabel.setText("");
            System.out.println("Login Sukses untuk pengguna: " + user.getUsername() + " (" + user.getEmail() + ")");

            if (primaryStage != null) {
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

                try {
                    Dashboard dashboard = new Dashboard();
                    dashboard.start(new Stage(), user.getEmail());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Error starting dashboard: " + e.getMessage());
                }
            }

        } else {
            errorMessageLabel.setText("Email atau password salah!"); // Ubah pesan error
        }
    }

    @FXML
    private void handleRegisterLink(ActionEvent event) {
        System.out.println("Navigasi ke halaman registrasi.");
        if (primaryStage != null) {
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            try {
                Main.showRegisterView();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error navigating to register view: " + e.getMessage());
            }
        }
    }
}

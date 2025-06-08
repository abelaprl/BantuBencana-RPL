// File: src/Main.java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStageStatic; // Untuk menyimpan primary stage

    @Override
    public void start(Stage primaryStage) {
        primaryStageStatic = primaryStage; // Simpan primary stage
        primaryStage.setTitle("Bantu Bencana App");

        // Panggil metode untuk menampilkan tampilan login terlebih dahulu
        showLoginView();
    }

    public static void showLoginView() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("LoginView.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setPrimaryStage(primaryStageStatic); // Set stage untuk LoginController

            primaryStageStatic.setScene(new Scene(root, 400, 300));
            primaryStageStatic.show();
        } catch (IOException e) {
            System.err.println("Error loading LoginView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void showRegisterView() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("RegisterView.fxml"));
            Parent root = loader.load();
            RegisterController registerController = loader.getController();
            registerController.setPrimaryStage(primaryStageStatic); // Set stage untuk RegisterController

            primaryStageStatic.setScene(new Scene(root, 400, 500)); // Sesuaikan ukuran window jika perlu
            primaryStageStatic.show();
        } catch (IOException e) {
            System.err.println("Error loading RegisterView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // DataManager.deleteDataFiles(); // <-- Anda bisa uncomment ini untuk menghapus semua data (termasuk user) untuk fresh start
        launch(args);
    }
}
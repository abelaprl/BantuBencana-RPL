import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage; // Untuk mengakses stage dari controller lain

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage; // Simpan primary stage
        primaryStage.setTitle("Bantu Bencana App"); // Judul jendela aplikasi
        primaryStage.setResizable(false); // Opsional: jendela tidak bisa diubah ukurannya
        showLoginView(); // Mulai dengan menampilkan tampilan login
    }

    // Metode untuk menampilkan tampilan Login
    public static void showLoginView() throws IOException {
        // FXMLLoader.load() mencari file FXML relatif terhadap classpath dari kelas Main
        // Karena LoginView.fxml berada di direktori yang sama dengan Main.java (src/),
        // kita bisa langsung merujuknya.
        Parent root = FXMLLoader.load(Main.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metode untuk menampilkan tampilan Register
    public static void showRegisterView() throws IOException {
        // Sama seperti LoginView.fxml, RegisterView.fxml juga ada di src/
        Parent root = FXMLLoader.load(Main.class.getResource("RegisterView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metode baru untuk menampilkan Dashboard
    public static void showDashboardView(String userEmail) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("DashboardView.fxml"));
        Parent root = loader.load();

        // Dapatkan controller setelah FXML dimuat
        DashboardController controller = loader.getController();
        // Panggil metode initData() di DashboardController untuk meneruskan email pengguna
        controller.initData(userEmail);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Metode launch() diperlukan untuk memulai aplikasi JavaFX
        launch(args);
    }
}
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
        try {
            showLoginView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showLoginView() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("LoginView.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setPrimaryStage(primaryStageStatic); // Set stage untuk LoginController

        primaryStageStatic.setScene(new Scene(root, 400, 300));
        primaryStageStatic.show();
    }

    public static void showRegisterView() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("RegisterView.fxml"));
        Parent root = loader.load();
        RegisterController registerController = loader.getController();
        registerController.setPrimaryStage(primaryStageStatic); // Set stage untuk RegisterController

        primaryStageStatic.setScene(new Scene(root, 400, 500)); // Sesuaikan ukuran window jika perlu
        primaryStageStatic.show();
    }

    public static void showDashboardView() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("DashboardView.fxml"));
        Parent root = loader.load();
        DashboardController controller = loader.getController();
        controller.initData("user@example.com"); // You can pass actual user email here

        primaryStageStatic.setScene(new Scene(root, 1000, 700));
        primaryStageStatic.show();
    }

    // Method for LaporanBencanaData
    public static void showDetailLaporan(LaporanBencanaData laporan) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("DetailLaporanView.fxml"));
        Parent root = loader.load();
        DetailLaporanController controller = loader.getController();
        controller.initData(laporan);

        primaryStageStatic.setScene(new Scene(root, 900, 700));
        primaryStageStatic.show();
    }

    // Overloaded method for Bencana
    public static void showDetailLaporan(Bencana bencana) throws IOException {
        // Convert Bencana to LaporanBencanaData
        LaporanBencanaData laporan = new LaporanBencanaData(
            bencana.getJudul(), // jenisBencana
            bencana.getLokasi(), // lokasi
            bencana.getDeskripsiSingkat(), // deskripsi
            "Sedang", // tingkatKeparahan (default)
            "0" // jumlahKorban (default)
        );
        
        showDetailLaporan(laporan);
    }

    public static void showLaporanList() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("LaporanListView.fxml"));
        Parent root = loader.load();
        LaporanListController controller = loader.getController();
        controller.initData("user@example.com");

        primaryStageStatic.setScene(new Scene(root, 1000, 700));
        primaryStageStatic.show();
    }

    public static void showBuatLaporan() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("BuatLaporanView.fxml"));
        Parent root = loader.load();

        primaryStageStatic.setScene(new Scene(root, 900, 800));
        primaryStageStatic.show();
    }

    public static void main(String[] args) {
        // DataManager.deleteDataFiles(); // <-- Anda bisa uncomment ini untuk menghapus semua data (termasuk user) untuk fresh start
        launch(args);
    }
}

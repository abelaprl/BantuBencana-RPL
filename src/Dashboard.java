import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dashboard extends Application {

    // Database global untuk menyimpan feedback
    public static List<FeedbackData> feedbackDatabase = new ArrayList<>();

    // Database global untuk menyimpan laporan bencana
    public static List<LaporanBencanaData> laporanBencanaDatabase = new ArrayList<>();

    // DatabaseViewer instance
    private static DatabaseViewer databaseViewer;

    // Tambahkan variabel untuk menyimpan email pengguna yang login
    private String currentUserEmail;

    // Ubah tanda tangan method start untuk menerima userEmail
    @Override
    public void start(Stage primaryStage) {
        // Metode ini akan dipanggil tanpa email saat aplikasi pertama kali dijalankan oleh JavaFX Launcher.
        // Panggil metode overloaded jika perlu.
        start(primaryStage, null); // Panggil versi overloaded dengan email null atau kosong
    }

    // Overloaded start method untuk menerima email dari LoginController
    public void start(Stage primaryStage, String userEmail) {
        this.currentUserEmail = userEmail; // Simpan email pengguna yang login

        // Load data dari file saat aplikasi dimulai
        DataManager.loadFeedbackData();
        DataManager.loadLaporanBencanaData();

        System.out.println("Dashboard started. Database loaded from files.");
        System.out.println("Total Laporan Bencana: " + laporanBencanaDatabase.size());
        System.out.println("Total Feedback: " + feedbackDatabase.size());

        // Initialize dummy data jika database kosong
        if (laporanBencanaDatabase.isEmpty()) {
            initializeDummyData();
        }

        primaryStage.setTitle("Dashboard Pasca Bencana");

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Selamat Datang di Sistem Bantu Bencana!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        if (currentUserEmail != null && !currentUserEmail.isEmpty()) {
            welcomeLabel.setText("Selamat Datang, " + currentUserEmail + "!");
        }


        Button laporButton = new Button("Lapor Bencana");
        laporButton.setPrefSize(200, 50);
        laporButton.setStyle("-fx-font-size: 16px; -fx-background-color: #007bff; -fx-text-fill: white; -fx-background-radius: 5;");
        laporButton.setOnAction(e -> {
            new LaporanBencana().start(new Stage());
        });

        Button feedbackButton = new Button("Berikan Feedback");
        feedbackButton.setPrefSize(200, 50);
        feedbackButton.setStyle("-fx-font-size: 16px; -fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 5;");
        feedbackButton.setOnAction(e -> {
            // Gunakan email pengguna yang login
            new Feedback().start(new Stage(), currentUserEmail);
        });

        Button viewDatabaseButton = new Button("Lihat Database");
        viewDatabaseButton.setPrefSize(200, 50);
        viewDatabaseButton.setStyle("-fx-font-size: 16px; -fx-background-color: #ffc107; -fx-text-fill: #333; -fx-background-radius: 5;");
        viewDatabaseButton.setOnAction(e -> {
            if (databaseViewer == null) {
                databaseViewer = new DatabaseViewer();
            }
            databaseViewer.showStage(); // Mengganti show() dengan showStage()
            databaseViewer.refreshData();
        });

        Button exitButton = new Button("Keluar");
        exitButton.setPrefSize(200, 50);
        exitButton.setStyle("-fx-font-size: 16px; -fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 5;");
        exitButton.setOnAction(e -> {
            DataManager.saveFeedbackData();
            DataManager.saveLaporanBencanaData();
            primaryStage.close();
            System.out.println("Aplikasi ditutup. Data disimpan.");
        });

        root.getChildren().addAll(welcomeLabel, laporButton, feedbackButton, viewDatabaseButton, exitButton);

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void addFeedback(FeedbackData feedback) {
        feedbackDatabase.add(feedback);
        DataManager.saveFeedbackData();
        System.out.println("Feedback baru ditambahkan ke database!");

        if (databaseViewer != null) {
            databaseViewer.refreshData();
        }
    }

    public static void removeFeedbackFromDatabase(int index) {
        if (index >= 0 && index < feedbackDatabase.size()) {
            feedbackDatabase.remove(index);
            DataManager.saveFeedbackData();
            System.out.println("Feedback berhasil dihapus dari database!");

            if (databaseViewer != null) {
                databaseViewer.refreshData();
            }
        }
    }

    public static void addLaporanBencana(LaporanBencanaData laporan) {
        laporanBencanaDatabase.add(laporan);
        DataManager.saveLaporanBencanaData();
        System.out.println("Laporan bencana baru ditambahkan ke database!");

        if (databaseViewer != null) {
            databaseViewer.refreshData();
        }
    }

    public static void removeLaporanBencanaFromDatabase(int index) {
        if (index >= 0 && index < laporanBencanaDatabase.size()) {
            laporanBencanaDatabase.remove(index);
            DataManager.saveLaporanBencanaData();
            System.out.println("Laporan bencana berhasil dihapus dari database!");

            if (databaseViewer != null) {
                databaseViewer.refreshData();
            }
        }
    }

    // Hapus metode getCurrentUserEmail() karena kita akan menggunakan email yang diteruskan
    // public static String getCurrentUserEmail() {
    //     Random random = new Random();
    //     int userNumber = random.nextInt(1000) + 1;
    //     return "user" + userNumber + "@example.com";
    // }

    public static List<FeedbackData> getAllFeedback() {
        return feedbackDatabase;
    }

    public static List<LaporanBencanaData> getAllLaporanBencana() {
        return laporanBencanaDatabase;
    }

    public static void initializeDummyData() {
        laporanBencanaDatabase.add(new LaporanBencanaData("Banjir", "Jakarta", "Banjir besar di Jakarta Selatan", "Sedang", "15"));
        laporanBencanaDatabase.add(new LaporanBencanaData("Gempa Bumi", "Yogyakarta", "Gempa 5.2 SR di Yogyakarta", "Ringan", "0"));
        laporanBencanaDatabase.add(new LaporanBencanaData("Kebakaran", "Surabaya", "Kebakaran di pemukiman padat", "Berat", "5"));

        DataManager.saveLaporanBencanaData();
        System.out.println("Data dummy laporan bencana berhasil diinisialisasi.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
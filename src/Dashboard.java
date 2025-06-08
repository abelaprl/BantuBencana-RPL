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

    // Database global untuk menyimpan donasi (BARU DITAMBAHKAN)
    public static List<DonationData> donationDatabase = new ArrayList<>();

    // DatabaseViewer instance
    private static DatabaseViewer databaseViewer; // Anda mungkin perlu memperbarui DatabaseViewer juga

    // Tambahkan variabel untuk menyimpan email pengguna yang login
    private String currentUserEmail;

    // Ubah tanda tangan method start untuk menerima userEmail
    @Override
    public void start(Stage primaryStage) {
        start(primaryStage, null);
    }

    // Overloaded start method untuk menerima email dari LoginController
    public void start(Stage primaryStage, String userEmail) {
        this.currentUserEmail = userEmail;

        // Load data dari file saat aplikasi dimulai
        DataManager.loadFeedbackData();
        DataManager.loadLaporanBencanaData();
        DataManager.loadDonationData(); // Load data donasi (BARU)

        System.out.println("Dashboard started. Database loaded from files.");
        System.out.println("Total Laporan Bencana: " + laporanBencanaDatabase.size());
        System.out.println("Total Feedback: " + feedbackDatabase.size());
        System.out.println("Total Donasi: " + donationDatabase.size()); // Log total donasi (BARU)

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
            new Feedback().start(new Stage(), currentUserEmail);
        });

        Button donasiButton = new Button("Donasi Bencana"); // Ubah teks tombol
        donasiButton.setPrefSize(200, 50);
        donasiButton.setStyle("-fx-font-size: 16px; -fx-background-color: #6f42c1; -fx-text-fill: white; -fx-background-radius: 5;");
        donasiButton.setOnAction(e -> {
            // Membuka Halaman Donasi baru dan meneruskan email pengguna
            new DonationPage(currentUserEmail).start(new Stage());
        });

        Button viewDatabaseButton = new Button("Lihat Database");
        viewDatabaseButton.setPrefSize(200, 50);
        viewDatabaseButton.setStyle("-fx-font-size: 16px; -fx-background-color: #ffc107; -fx-text-fill: #333; -fx-background-radius: 5;");
        viewDatabaseButton.setOnAction(e -> {
            if (databaseViewer == null) {
                databaseViewer = new DatabaseViewer();
            }
            databaseViewer.showStage();
            databaseViewer.refreshData();
        });

        Button exitButton = new Button("Keluar");
        exitButton.setPrefSize(200, 50);
        exitButton.setStyle("-fx-font-size: 16px; -fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 5;");
        exitButton.setOnAction(e -> {
            DataManager.saveFeedbackData();
            DataManager.saveLaporanBencanaData();
            DataManager.saveDonationData(); // Simpan data donasi saat keluar (BARU)
            primaryStage.close();
            System.out.println("Aplikasi ditutup. Data disimpan.");
        });

        // Tambahkan tombol donasi ke dalam VBox
        root.getChildren().addAll(welcomeLabel, laporButton, feedbackButton, donasiButton, viewDatabaseButton, exitButton);

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

    // Metode baru untuk menambahkan donasi (BARU DITAMBAHKAN)
    public static void addDonation(DonationData donation) {
        donationDatabase.add(donation);
        DataManager.saveDonationData();
        System.out.println("Donasi baru ditambahkan ke database!");

        if (databaseViewer != null) {
            // Anda mungkin perlu memperbarui DatabaseViewer agar bisa menampilkan donasi
            databaseViewer.refreshData();
        }
    }

    public static List<FeedbackData> getAllFeedback() {
        return feedbackDatabase;
    }

    public static List<LaporanBencanaData> getAllLaporanBencana() {
        return laporanBencanaDatabase;
    }

    // Metode baru untuk mendapatkan semua donasi (BARU DITAMBAHKAN)
    public static List<DonationData> getAllDonations() {
        return donationDatabase;
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
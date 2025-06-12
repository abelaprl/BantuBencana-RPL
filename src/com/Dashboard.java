package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader; // Import FXMLLoader
import javafx.scene.Parent;   // Import Parent
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException; // Import IOException
import java.util.ArrayList;
import java.util.List;
// import java.util.Random; // Tidak lagi dibutuhkan karena email diteruskan

public class Dashboard extends Application {

    // Database global untuk menyimpan feedback
    public static List<FeedbackData> feedbackDatabase = new ArrayList<>();

    // Database global untuk menyimpan laporan bencana
    public static List<LaporanBencanaData> laporanBencanaDatabase = new ArrayList<>();

    // Database global untuk menyimpan donasi (BARU DITAMBAHKAN)
    public static List<DonationData> donationDatabase = new ArrayList<>();

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
        DataManager.loadDonationData(); // Load data donasi (BARU)

        System.out.println("Dashboard started. Database loaded from files.");
        System.out.println("Total Laporan Bencana: " + laporanBencanaDatabase.size());
        System.out.println("Total Feedback: " + feedbackDatabase.size());
        System.out.println("Total Donasi: " + donationDatabase.size()); // Log total donasi (BARU)

        // Initialize dummy data jika database kosong
        if (laporanBencanaDatabase.isEmpty()) {
            initializeDummyData();
        }

        try {
            // Memuat FXML file
            // Pastikan "dashboardview.fxml" ada di lokasi yang dapat diakses oleh ClassLoader
            // Misalnya, di folder yang sama dengan kelas Dashboard.java, atau di folder resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboardview.fxml"));
            Parent root = loader.load();

            // Mendapatkan controller dari FXML
            DashboardController controller = loader.getController();

            // Set email pengguna ke controller
            if (controller != null) {
                // Panggil method initData di DashboardController untuk meneruskan email
                controller.initData(userEmail);
            }

            primaryStage.setTitle("Dashboard Pasca Bencana");
            Scene scene = new Scene(root, 1000, 700); // Sesuaikan ukuran Scene dengan ukuran FXML Anda (prefHeight, prefWidth)
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Gagal memuat dashboardview.fxml: " + e.getMessage());
        }
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
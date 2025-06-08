import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Dashboard extends Application {
    
    // DATABASE GLOBAL - Variabel untuk menyimpan semua data feedback
    public static List<FeedbackData> feedbackDatabase = new ArrayList<>();
    
    // DATABASE GLOBAL - Variabel untuk menyimpan semua data laporan bencana
    public static List<LaporanBencanaData> laporanBencanaDatabase = new ArrayList<>();
    
    // Method untuk menambah feedback ke database
    public static void addFeedbackToDatabase(FeedbackData feedback) {
        feedbackDatabase.add(feedback);
        // Simpan ke file setiap kali ada data baru
        DataManager.saveFeedbackData(feedbackDatabase);
        System.out.println("Feedback berhasil disimpan ke database!");
        System.out.println("Total feedback dalam database: " + feedbackDatabase.size());
        System.out.println("Data feedback: " + feedback.toString());
    }
    
    // Method untuk menambah laporan bencana ke database
    public static void addLaporanBencanaToDatabase(LaporanBencanaData laporan) {
        laporanBencanaDatabase.add(laporan);
        // Simpan ke file setiap kali ada data baru
        DataManager.saveLaporanData(laporanBencanaDatabase);
        System.out.println("Laporan bencana berhasil disimpan ke database!");
        System.out.println("Total laporan bencana dalam database: " + laporanBencanaDatabase.size());
        System.out.println("Data laporan: " + laporan.toString());
    }
    
    // Method untuk menghapus feedback dari database
    public static void removeFeedbackFromDatabase(int index) {
        if (index >= 0 && index < feedbackDatabase.size()) {
            feedbackDatabase.remove(index);
            // Simpan perubahan ke file
            DataManager.saveFeedbackData(feedbackDatabase);
            System.out.println("Feedback berhasil dihapus dari database!");
        }
    }
    
    // Method untuk menghapus laporan bencana dari database
    public static void removeLaporanBencanaFromDatabase(int index) {
        if (index >= 0 && index < laporanBencanaDatabase.size()) {
            laporanBencanaDatabase.remove(index);
            // Simpan perubahan ke file
            DataManager.saveLaporanData(laporanBencanaDatabase);
            System.out.println("Laporan bencana berhasil dihapus dari database!");
        }
    }
    
    // Method untuk mendapatkan semua feedback
    public static List<FeedbackData> getAllFeedback() {
        return feedbackDatabase;
    }
    
    // Method untuk mendapatkan semua laporan bencana
    public static List<LaporanBencanaData> getAllLaporanBencana() {
        return laporanBencanaDatabase;
    }
    
    // Method untuk memuat data dari file
    public static void loadDataFromFiles() {
        feedbackDatabase = DataManager.loadFeedbackData();
        laporanBencanaDatabase = DataManager.loadLaporanData();
        
        // Jika tidak ada data laporan, buat data dummy
        if (laporanBencanaDatabase.isEmpty()) {
            initializeDummyData();
        }
    }
    
    // Method untuk menampilkan semua feedback (untuk debugging)
    public static void displayAllFeedback() {
        System.out.println("\n=== DATABASE FEEDBACK ===");
        if (feedbackDatabase.isEmpty()) {
            System.out.println("Database feedback kosong.");
        } else {
            for (int i = 0; i < feedbackDatabase.size(); i++) {
                System.out.println("Feedback #" + (i + 1) + ": " + feedbackDatabase.get(i).toString());
            }
        }
        System.out.println("========================\n");
    }
    
    // Method untuk menampilkan semua laporan bencana (untuk debugging)
    public static void displayAllLaporanBencana() {
        System.out.println("\n=== DATABASE LAPORAN BENCANA ===");
        if (laporanBencanaDatabase.isEmpty()) {
            System.out.println("Database laporan bencana kosong.");
        } else {
            for (int i = 0; i < laporanBencanaDatabase.size(); i++) {
                System.out.println("Laporan #" + (i + 1) + ": " + laporanBencanaDatabase.get(i).toString());
            }
        }
        System.out.println("===============================\n");
    }
    
    // Method untuk inisialisasi data dummy (opsional)
    public static void initializeDummyData() {
        // Tambah beberapa data dummy laporan bencana
        LaporanBencanaData dummy1 = new LaporanBencanaData("Banjir", "Jakarta Selatan", 
            "Banjir akibat hujan deras selama 3 hari", "Sedang", "50 orang");
        LaporanBencanaData dummy2 = new LaporanBencanaData("Gempa Bumi", "Bandung", 
            "Gempa berkekuatan 5.2 SR", "Ringan", "0 orang");
        LaporanBencanaData dummy3 = new LaporanBencanaData("Kebakaran", "Surabaya", 
            "Kebakaran di pemukiman padat", "Berat", "15 orang");
            
        addLaporanBencanaToDatabase(dummy1);
        addLaporanBencanaToDatabase(dummy2);
        addLaporanBencanaToDatabase(dummy3);
    }
    
    @Override
    public void start(Stage primaryStage) {
        // Muat data dari file saat aplikasi dimulai
        loadDataFromFiles();
        
        // Judul dashboard
        Text title = new Text("Dashboard Bantuan Bencana");
        title.setFont(Font.font("Arial", 24));
        
        // Tombol untuk membuka form feedback
        Button feedbackButton = new Button("Form Feedback Pasca Bencana");
        feedbackButton.setPrefWidth(300);
        feedbackButton.setPrefHeight(50);
        feedbackButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");
        
        // Event handler untuk tombol feedback
        feedbackButton.setOnAction(e -> {
            try {
                Feedback feedbackForm = new Feedback();
                Stage feedbackStage = new Stage();
                feedbackForm.start(feedbackStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        // Tombol untuk membuka laporan bencana
        Button laporanButton = new Button("Laporan Bencana");
        laporanButton.setPrefWidth(300);
        laporanButton.setPrefHeight(50);
        laporanButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 16px;");
        
        // Event handler untuk tombol laporan bencana
        laporanButton.setOnAction(e -> {
            try {
                LaporanBencana laporanForm = new LaporanBencana();
                Stage laporanStage = new Stage();
                laporanForm.start(laporanStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        // Tombol untuk melihat database dalam UI
        Button viewDatabaseButton = new Button("Lihat Database (UI)");
        viewDatabaseButton.setPrefWidth(300);
        viewDatabaseButton.setPrefHeight(50);
        viewDatabaseButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-size: 16px;");
        
        viewDatabaseButton.setOnAction(e -> {
            try {
                DatabaseViewer databaseViewer = new DatabaseViewer();
                Stage databaseStage = new Stage();
                databaseViewer.start(databaseStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        // Tombol untuk menghapus semua data
        Button clearDataButton = new Button("Hapus Semua Data");
        clearDataButton.setPrefWidth(300);
        clearDataButton.setPrefHeight(50);
        clearDataButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 16px;");
        
        clearDataButton.setOnAction(e -> {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Konfirmasi Hapus Data");
            confirmAlert.setHeaderText("Hapus Semua Data");
            confirmAlert.setContentText("Apakah Anda yakin ingin menghapus semua data feedback dan laporan bencana?\n\nTindakan ini tidak dapat dibatalkan!");
            
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Hapus data dari memory
                feedbackDatabase.clear();
                laporanBencanaDatabase.clear();
                
                // Hapus file data
                DataManager.clearAllData();
                
                // Inisialisasi ulang dengan data dummy
                initializeDummyData();
                
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Data Dihapus");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Semua data berhasil dihapus!\nData dummy laporan bencana telah dibuat ulang.");
                successAlert.showAndWait();
            }
        });
        
        // Tombol untuk melihat database di console (untuk debugging)
        Button consoleButton = new Button("Debug Console");
        consoleButton.setPrefWidth(300);
        consoleButton.setPrefHeight(50);
        consoleButton.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; -fx-font-size: 16px;");
        
        consoleButton.setOnAction(e -> {
            displayAllLaporanBencana();
            displayAllFeedback();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Debug Console");
            alert.setHeaderText("Database Status");
            alert.setContentText("Laporan Bencana: " + laporanBencanaDatabase.size() + 
                               "\nFeedback: " + feedbackDatabase.size() + 
                               "\n\nLihat console untuk detail lengkap.");
            alert.showAndWait();
        });
        
        // Layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        layout.getChildren().addAll(title, feedbackButton, laporanButton, viewDatabaseButton, clearDataButton, consoleButton);
        
        // Scene dan Stage
        Scene scene = new Scene(layout, 600, 700);
        primaryStage.setTitle("Dashboard Bantuan Bencana");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Tampilkan status database saat startup
        System.out.println("Dashboard started. Database loaded from files.");
        displayAllLaporanBencana();
        displayAllFeedback();
        
        // Simpan data saat aplikasi ditutup
        primaryStage.setOnCloseRequest(event -> {
            DataManager.saveFeedbackData(feedbackDatabase);
            DataManager.saveLaporanData(laporanBencanaDatabase);
            System.out.println("Data disimpan sebelum aplikasi ditutup.");
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
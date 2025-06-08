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
    
    @Override
    public void start(Stage primaryStage) {
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
        root.setPadding(new Insets(30));
        
        Label titleLabel = new Label("Dashboard Pasca Bencana");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        // Statistik
        Label statsLabel = new Label("Total Laporan Bencana: " + laporanBencanaDatabase.size() + 
                                   "\nTotal Feedback: " + feedbackDatabase.size());
        statsLabel.setStyle("-fx-font-size: 14px;");
        
        // Tombol untuk membuka form feedback
        Button feedbackButton = new Button("Buka Form Feedback");
        feedbackButton.setPrefWidth(200);
        feedbackButton.setOnAction(e -> {
            Feedback feedbackForm = new Feedback();
            Stage feedbackStage = new Stage();
            try {
                feedbackForm.start(feedbackStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        // Tombol untuk membuka laporan bencana
        Button laporanButton = new Button("Buka Laporan Bencana");
        laporanButton.setPrefWidth(200);
        laporanButton.setOnAction(e -> {
            LaporanBencana laporanForm = new LaporanBencana();
            Stage laporanStage = new Stage();
            try {
                laporanForm.start(laporanStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        // Tombol untuk melihat database
        Button viewDatabaseButton = new Button("Lihat Database");
        viewDatabaseButton.setPrefWidth(200);
        viewDatabaseButton.setOnAction(e -> {
            if (databaseViewer == null) {
                databaseViewer = new DatabaseViewer();
            }
            databaseViewer.show(); // Menggunakan show() bukan start()
        });
        
        root.getChildren().addAll(titleLabel, statsLabel, feedbackButton, laporanButton, viewDatabaseButton);
        
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Save data saat aplikasi ditutup
        primaryStage.setOnCloseRequest(e -> {
            DataManager.saveFeedbackData();
            DataManager.saveLaporanBencanaData();
        });
    }
    
    // Method untuk menambah feedback ke database
    public static void addFeedbackToDatabase(FeedbackData feedback) {
        feedbackDatabase.add(feedback);
        DataManager.saveFeedbackData(); // Auto-save setiap kali ada penambahan
        System.out.println("Feedback berhasil disimpan ke database!");
        System.out.println("Total feedback dalam database: " + feedbackDatabase.size());
        System.out.println("Data feedback: " + feedback.toString());
        
        // Refresh database viewer jika sedang terbuka
        if (databaseViewer != null) {
            databaseViewer.refreshData();
        }
    }
    
    // Method untuk menambah laporan bencana ke database
    public static void addLaporanBencanaToDatabase(LaporanBencanaData laporan) {
        laporanBencanaDatabase.add(laporan);
        DataManager.saveLaporanBencanaData(); // Auto-save setiap kali ada penambahan
        System.out.println("Laporan bencana berhasil disimpan ke database!");
        System.out.println("Total laporan dalam database: " + laporanBencanaDatabase.size());
        
        // Refresh database viewer jika sedang terbuka
        if (databaseViewer != null) {
            databaseViewer.refreshData();
        }
    }
    
    // Method untuk menghapus feedback dari database
    public static void removeFeedbackFromDatabase(int index) {
        if (index >= 0 && index < feedbackDatabase.size()) {
            feedbackDatabase.remove(index);
            DataManager.saveFeedbackData(); // Auto-save setiap kali ada penghapusan
            System.out.println("Feedback berhasil dihapus dari database!");
            
            // Refresh database viewer jika sedang terbuka
            if (databaseViewer != null) {
                databaseViewer.refreshData();
            }
        }
    }
    
    // Method untuk menghapus laporan bencana dari database
    public static void removeLaporanBencanaFromDatabase(int index) {
        if (index >= 0 && index < laporanBencanaDatabase.size()) {
            laporanBencanaDatabase.remove(index);
            DataManager.saveLaporanBencanaData(); // Auto-save setiap kali ada penghapusan
            System.out.println("Laporan bencana berhasil dihapus dari database!");
            
            // Refresh database viewer jika sedang terbuka
            if (databaseViewer != null) {
                databaseViewer.refreshData();
            }
        }
    }
    
    // Method untuk mendapatkan email user saat ini (simulasi)
    public static String getCurrentUserEmail() {
        Random random = new Random();
        int userNumber = random.nextInt(1000) + 1;
        return "user" + userNumber + "@example.com";
    }
    
    // Method untuk mendapatkan semua feedback
    public static List<FeedbackData> getAllFeedback() {
        return feedbackDatabase;
    }
    
    // Method untuk mendapatkan semua laporan bencana
    public static List<LaporanBencanaData> getAllLaporanBencana() {
        return laporanBencanaDatabase;
    }
    
    // Method untuk inisialisasi data dummy
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
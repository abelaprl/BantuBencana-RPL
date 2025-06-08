import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends Application {
    
    // DATABASE GLOBAL - Variabel untuk menyimpan semua data feedback
    public static List<FeedbackData> feedbackDatabase = new ArrayList<>();
    
    // Method untuk menambah feedback ke database
    public static void addFeedbackToDatabase(FeedbackData feedback) {
        feedbackDatabase.add(feedback);
        System.out.println("Feedback berhasil disimpan ke database!");
        System.out.println("Total feedback dalam database: " + feedbackDatabase.size());
        System.out.println("Data feedback: " + feedback.toString());
    }
    
    // Method untuk mendapatkan semua feedback
    public static List<FeedbackData> getAllFeedback() {
        return feedbackDatabase;
    }
    
    // Method untuk menampilkan semua feedback (untuk debugging)
    public static void displayAllFeedback() {
        System.out.println("\n=== DATABASE FEEDBACK ===");
        if (feedbackDatabase.isEmpty()) {
            System.out.println("Database kosong.");
        } else {
            for (int i = 0; i < feedbackDatabase.size(); i++) {
                System.out.println("Feedback #" + (i + 1) + ": " + feedbackDatabase.get(i).toString());
            }
        }
        System.out.println("========================\n");
    }
    
    @Override
    public void start(Stage primaryStage) {
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
        
        // Tombol untuk melihat database feedback (untuk testing)
        Button viewDatabaseButton = new Button("Lihat Database Feedback");
        viewDatabaseButton.setPrefWidth(300);
        viewDatabaseButton.setPrefHeight(50);
        viewDatabaseButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-size: 16px;");
        
        viewDatabaseButton.setOnAction(e -> {
            displayAllFeedback();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Database Feedback");
            alert.setHeaderText("Total Feedback: " + feedbackDatabase.size());
            alert.setContentText("Lihat console untuk detail lengkap database feedback.");
            alert.showAndWait();
        });
        
        // Layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        layout.getChildren().addAll(title, feedbackButton, laporanButton, viewDatabaseButton);
        
        // Scene dan Stage
        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setTitle("Dashboard Bantuan Bencana");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Tampilkan status database saat startup
        System.out.println("Dashboard started. Database feedback initialized.");
        displayAllFeedback();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
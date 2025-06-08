import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Optional;

public class DatabaseViewer extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Judul
        Text title = new Text("Database Viewer - Bantuan Bencana");
        title.setFont(Font.font("Arial", 20));
        title.setStyle("-fx-font-weight: bold;");
        
        // Tab Pane untuk memisahkan database
        TabPane tabPane = new TabPane();
        
        // Tab untuk Database Feedback
        Tab feedbackTab = new Tab("Database Feedback");
        feedbackTab.setClosable(false);
        VBox feedbackContent = createFeedbackDatabaseView();
        feedbackTab.setContent(feedbackContent);
        
        // Tab untuk Database Laporan Bencana
        Tab laporanTab = new Tab("Database Laporan Bencana");
        laporanTab.setClosable(false);
        VBox laporanContent = createLaporanDatabaseView();
        laporanTab.setContent(laporanContent);
        
        tabPane.getTabs().addAll(feedbackTab, laporanTab);
        
        // Tombol refresh
        Button refreshButton = new Button("Refresh Data");
        refreshButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        refreshButton.setOnAction(e -> {
            // Refresh kedua tab
            feedbackTab.setContent(createFeedbackDatabaseView());
            laporanTab.setContent(createLaporanDatabaseView());
        });
        
        // Tombol tutup
        Button closeButton = new Button("Tutup");
        closeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px;");
        closeButton.setOnAction(e -> primaryStage.close());
        
        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(refreshButton, closeButton);
        
        // Layout utama
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(title, tabPane, buttonLayout);
        
        Scene scene = new Scene(mainLayout, 900, 600);
        primaryStage.setTitle("Database Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox createFeedbackDatabaseView() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        
        Label countLabel = new Label("Total Feedback: " + Dashboard.getAllFeedback().size());
        countLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        if (Dashboard.getAllFeedback().isEmpty()) {
            Label emptyLabel = new Label("Belum ada data feedback.");
            emptyLabel.setStyle("-fx-font-style: italic;");
            content.getChildren().addAll(countLabel, emptyLabel);
        } else {
            ScrollPane scrollPane = new ScrollPane();
            VBox feedbackList = new VBox(10);
            
            for (int i = 0; i < Dashboard.getAllFeedback().size(); i++) {
                FeedbackData feedback = Dashboard.getAllFeedback().get(i);
                VBox feedbackCard = createFeedbackCard(feedback, i);
                feedbackList.getChildren().add(feedbackCard);
            }
            
            scrollPane.setContent(feedbackList);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(400);
            
            content.getChildren().addAll(countLabel, scrollPane);
        }
        
        return content;
    }
    
    private VBox createLaporanDatabaseView() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        
        Label countLabel = new Label("Total Laporan Bencana: " + Dashboard.getAllLaporanBencana().size());
        countLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        if (Dashboard.getAllLaporanBencana().isEmpty()) {
            Label emptyLabel = new Label("Belum ada data laporan bencana.");
            emptyLabel.setStyle("-fx-font-style: italic;");
            content.getChildren().addAll(countLabel, emptyLabel);
        } else {
            ScrollPane scrollPane = new ScrollPane();
            VBox laporanList = new VBox(10);
            
            for (int i = 0; i < Dashboard.getAllLaporanBencana().size(); i++) {
                LaporanBencanaData laporan = Dashboard.getAllLaporanBencana().get(i);
                VBox laporanCard = createLaporanCard(laporan, i);
                laporanList.getChildren().add(laporanCard);
            }
            
            scrollPane.setContent(laporanList);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(400);
            
            content.getChildren().addAll(countLabel, scrollPane);
        }
        
        return content;
    }
    
    private VBox createFeedbackCard(FeedbackData feedback, int index) {
        VBox card = new VBox(5);
        card.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: #f9f9f9;");
        
        // Header dengan tombol hapus
        HBox headerBox = new HBox();
        headerBox.setSpacing(10);
        
        Label titleLabel = new Label("Feedback #" + (index + 1));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        
        Button deleteButton = new Button("🗑️ Hapus");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 12px;");
        deleteButton.setOnAction(e -> {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Konfirmasi Hapus");
            confirmAlert.setHeaderText("Hapus Feedback #" + (index + 1));
            confirmAlert.setContentText("Apakah Anda yakin ingin menghapus feedback ini?");
            
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Dashboard.removeFeedbackFromDatabase(index);
                
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Feedback Dihapus");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Feedback berhasil dihapus dari database!");
                successAlert.showAndWait();
                
                // Refresh tampilan
                Stage stage = (Stage) deleteButton.getScene().getWindow();
                stage.close();
                try {
                    DatabaseViewer newViewer = new DatabaseViewer();
                    Stage newStage = new Stage();
                    newViewer.start(newStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        headerBox.getChildren().addAll(titleLabel, spacer, deleteButton);
        
        Label infoLabel = new Label(
            "Laporan: " + (feedback.getLaporanBencana() != null ? feedback.getLaporanBencana() : "N/A") + "\n" +
            "Jenis: " + (feedback.getJenisBencana() != null ? feedback.getJenisBencana() : "N/A") + "\n" +
            "Lokasi: " + (feedback.getLokasiLaporan() != null ? feedback.getLokasiLaporan() : "N/A")
        );
        
        // Membuat tampilan rating dengan bintang yang lebih besar
        HBox ratingBox = new HBox(15);
        ratingBox.setPadding(new Insets(5, 0, 5, 0));
        
        // Rating Penanganan
        VBox penangananBox = createRatingDisplay("Penanganan", feedback.getRatingPenanganan());
        
        // Rating Relawan
        VBox relawanBox = createRatingDisplay("Relawan", feedback.getRatingKinerjaRelawan());
        
        // Rating Donasi
        VBox donasiBox = createRatingDisplay("Donasi", feedback.getRatingAlokasiDonasi());
        
        // Rating Pengalaman
        VBox pengalamanBox = createRatingDisplay("Pengalaman", feedback.getRatingPengalamanBuruk());
        
        ratingBox.getChildren().addAll(penangananBox, relawanBox, donasiBox, pengalamanBox);
        
        Label mediaLabel = new Label("Media: " + 
            (feedback.getMediaPendukung() != null ? feedback.getMediaPendukung().getName() : "Tidak ada"));
        
        card.getChildren().addAll(headerBox, infoLabel, ratingBox, mediaLabel);
        return card;
    }
    
    private VBox createLaporanCard(LaporanBencanaData laporan, int index) {
        VBox card = new VBox(5);
        card.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: #f0f8ff;");
        
        // Header dengan tombol hapus
        HBox headerBox = new HBox();
        headerBox.setSpacing(10);
        
        Label titleLabel = new Label("Laporan #" + (index + 1));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        
        Button deleteButton = new Button("🗑️ Hapus");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 12px;");
        deleteButton.setOnAction(e -> {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Konfirmasi Hapus");
            confirmAlert.setHeaderText("Hapus Laporan #" + (index + 1));
            confirmAlert.setContentText("Apakah Anda yakin ingin menghapus laporan bencana ini?\n\nPerhatian: Feedback yang terkait dengan laporan ini mungkin akan terpengaruh!");
            
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Dashboard.removeLaporanBencanaFromDatabase(index);
                
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Laporan Dihapus");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Laporan bencana berhasil dihapus dari database!");
                successAlert.showAndWait();
                
                // Refresh tampilan
                Stage stage = (Stage) deleteButton.getScene().getWindow();
                stage.close();
                try {
                    DatabaseViewer newViewer = new DatabaseViewer();
                    Stage newStage = new Stage();
                    newViewer.start(newStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        headerBox.getChildren().addAll(titleLabel, spacer, deleteButton);
        
        Label infoLabel = new Label(
            "Jenis: " + laporan.getJenisBencana() + "\n" +
            "Lokasi: " + laporan.getLokasiBencana() + "\n" +
            "Tingkat: " + laporan.getTingkatKeparahan() + "\n" +
            "Korban: " + (laporan.getJumlahKorban().isEmpty() ? "Tidak ada data" : laporan.getJumlahKorban())
        );
        
        Label deskripsiLabel = new Label("Deskripsi: " + 
            (laporan.getDeskripsi().length() > 100 ? 
             laporan.getDeskripsi().substring(0, 100) + "..." : 
             laporan.getDeskripsi()));
        deskripsiLabel.setWrapText(true);
        
        card.getChildren().addAll(headerBox, infoLabel, deskripsiLabel);
        return card;
    }
    
    private VBox createRatingDisplay(String label, int rating) {
        VBox box = new VBox(2);
        box.setAlignment(javafx.geometry.Pos.CENTER);
        
        Label titleLabel = new Label(label);
        titleLabel.setStyle("-fx-font-size: 12px;");
        
        HBox starsBox = new HBox(2);
        starsBox.setAlignment(javafx.geometry.Pos.CENTER);
        
        // Buat label dengan bintang yang lebih besar
        Label starsLabel = new Label(getStarString(rating));
        starsLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #FFD700;");
        
        starsBox.getChildren().add(starsLabel);
        box.getChildren().addAll(titleLabel, starsBox);
        
        return box;
    }
    
    private String getStarString(int rating) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < rating; i++) {
            stars.append("★"); // Bintang solid
        }
        for (int i = rating; i < 5; i++) {
            stars.append("☆"); // Bintang outline
        }
        return stars.toString();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
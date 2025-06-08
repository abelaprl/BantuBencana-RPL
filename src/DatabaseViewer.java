import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class DatabaseViewer {
    private Stage stage;
    private TabPane tabPane;
    private VBox feedbackContent;
    private VBox laporanContent;

    public DatabaseViewer() {
        stage = new Stage();
        stage.setTitle("Database Viewer");
        
        tabPane = new TabPane();
        
        // Tab untuk Feedback
        Tab feedbackTab = new Tab("Feedback");
        feedbackTab.setClosable(false);
        feedbackContent = new VBox(10);
        feedbackContent.setPadding(new Insets(10));
        ScrollPane feedbackScrollPane = new ScrollPane(feedbackContent);
        feedbackScrollPane.setFitToWidth(true);
        feedbackTab.setContent(feedbackScrollPane);
        
        // Tab untuk Laporan Bencana
        Tab laporanTab = new Tab("Laporan Bencana");
        laporanTab.setClosable(false);
        laporanContent = new VBox(10);
        laporanContent.setPadding(new Insets(10));
        ScrollPane laporanScrollPane = new ScrollPane(laporanContent);
        laporanScrollPane.setFitToWidth(true);
        laporanTab.setContent(laporanScrollPane);
        
        tabPane.getTabs().addAll(feedbackTab, laporanTab);
        
        Scene scene = new Scene(tabPane, 800, 600);
        stage.setScene(scene);
    }
    
    public void show() {
        refreshData();
        stage.show();
    }
    
    public void refreshData() {
        Platform.runLater(() -> {
            refreshFeedbackData();
            refreshLaporanData();
        });
    }
    
    private void refreshFeedbackData() {
        feedbackContent.getChildren().clear();
        
        List<FeedbackData> feedbackList = Dashboard.getAllFeedback();
        
        if (feedbackList.isEmpty()) {
            Label emptyLabel = new Label("Tidak ada data feedback.");
            emptyLabel.setStyle("-fx-font-size: 14px;");
            feedbackContent.getChildren().add(emptyLabel);
            return;
        }
        
        for (int i = 0; i < feedbackList.size(); i++) {
            FeedbackData feedback = feedbackList.get(i);
            VBox feedbackBox = createFeedbackBox(feedback, i);
            feedbackContent.getChildren().add(feedbackBox);
        }
    }
    
    private VBox createFeedbackBox(FeedbackData feedback, int index) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(15));
        box.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5;");
        
        // Header dengan informasi utama
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label titleLabel = new Label(feedback.getLaporanBencana());
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        Label emailLabel = new Label("(" + feedback.getUserEmail() + ")");
        emailLabel.setFont(Font.font("System", 12));
        emailLabel.setTextFill(Color.GRAY);
        
        header.getChildren().addAll(titleLabel, emailLabel);
        
        // Informasi detail
        VBox detailsBox = new VBox(5);
        detailsBox.getChildren().addAll(
            new Label("Jenis Bencana: " + feedback.getJenisBencana()),
            new Label("Lokasi: " + feedback.getLokasiLaporan()),
            new Label("Waktu Laporan: " + feedback.getTimestamp())
        );
        
        // Rating section
        VBox ratingsBox = new VBox(5);
        ratingsBox.getChildren().addAll(
            createRatingDisplay("Penanganan", feedback.getRatingPenanganan()),
            createRatingDisplay("Kinerja Relawan", feedback.getRatingKinerjaRelawan()),
            createRatingDisplay("Alokasi Donasi", feedback.getRatingAlokasiDonasi()),
            createRatingDisplay("Pengalaman Buruk", feedback.getRatingPengalamanBuruk())
        );
        
        // Feedback text section
        VBox feedbackTextBox = new VBox(5);
        
        if (feedback.getFeedbackPenanganan() != null && !feedback.getFeedbackPenanganan().isEmpty()) {
            feedbackTextBox.getChildren().add(createFeedbackTextSection("Penanganan", feedback.getFeedbackPenanganan()));
        }
        
        if (feedback.getFeedbackKinerjaRelawan() != null && !feedback.getFeedbackKinerjaRelawan().isEmpty()) {
            feedbackTextBox.getChildren().add(createFeedbackTextSection("Kinerja Relawan", feedback.getFeedbackKinerjaRelawan()));
        }
        
        if (feedback.getFeedbackAlokasiDonasi() != null && !feedback.getFeedbackAlokasiDonasi().isEmpty()) {
            feedbackTextBox.getChildren().add(createFeedbackTextSection("Alokasi Donasi", feedback.getFeedbackAlokasiDonasi()));
        }
        
        if (feedback.getFeedbackPengalamanBuruk() != null && !feedback.getFeedbackPengalamanBuruk().isEmpty()) {
            feedbackTextBox.getChildren().add(createFeedbackTextSection("Pengalaman Buruk", feedback.getFeedbackPengalamanBuruk()));
        }
        
        if (feedback.getEvaluasiTambahan() != null && !feedback.getEvaluasiTambahan().isEmpty()) {
            feedbackTextBox.getChildren().add(createFeedbackTextSection("Evaluasi Tambahan", feedback.getEvaluasiTambahan()));
        }
        
        // Media pendukung section
        VBox mediaBox = new VBox(5);
        if (feedback.getMediaPendukung() != null) {
            File mediaFile = feedback.getMediaPendukung();
            if (mediaFile.exists()) {
                Label mediaLabel = new Label("Media Pendukung: " + mediaFile.getName());
                mediaBox.getChildren().add(mediaLabel);
                
                // Menampilkan gambar jika file adalah jpg atau png
                String fileName = mediaFile.getName().toLowerCase();
                if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
                    try {
                        Image image = new Image(new FileInputStream(mediaFile));
                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(300);
                        imageView.setPreserveRatio(true);
                        mediaBox.getChildren().add(imageView);
                    } catch (FileNotFoundException e) {
                        Label errorLabel = new Label("Tidak dapat menampilkan gambar: " + e.getMessage());
                        errorLabel.setTextFill(Color.RED);
                        mediaBox.getChildren().add(errorLabel);
                    }
                }
            } else {
                Label errorLabel = new Label("File tidak ditemukan: " + mediaFile.getAbsolutePath());
                errorLabel.setTextFill(Color.RED);
                mediaBox.getChildren().add(errorLabel);
            }
        }
        
        // Rata-rata rating
        HBox averageBox = new HBox(10);
        averageBox.setAlignment(Pos.CENTER_LEFT);
        
        Label averageLabel = new Label("Rata-rata Rating: ");
        averageLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        Text averageValue = new Text(String.format("%.1f", feedback.getAverageRating()));
        averageValue.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        averageBox.getChildren().addAll(averageLabel, averageValue);
        
        // Menggabungkan semua komponen
        box.getChildren().addAll(header, new Separator(), detailsBox, new Separator(), 
                                ratingsBox, new Separator(), feedbackTextBox);
        
        // Tambahkan media box jika ada media pendukung
        if (feedback.getMediaPendukung() != null) {
            box.getChildren().addAll(new Separator(), mediaBox);
        }
        
        box.getChildren().addAll(new Separator(), averageBox);
        
        return box;
    }
    
    private HBox createRatingDisplay(String label, int rating) {
        HBox ratingBox = new HBox(10);
        ratingBox.setAlignment(Pos.CENTER_LEFT);
        
        Label ratingLabel = new Label(label + ": ");
        
        HBox starsBox = new HBox(2);
        for (int i = 1; i <= 5; i++) {
            Text star = new Text(i <= rating ? "★" : "☆");
            star.setFont(Font.font("System", 18)); // Ukuran bintang yang lebih besar
            star.setFill(i <= rating ? Color.GOLD : Color.GRAY);
            starsBox.getChildren().add(star);
        }
        
        ratingBox.getChildren().addAll(ratingLabel, starsBox);
        return ratingBox;
    }
    
    private VBox createFeedbackTextSection(String title, String content) {
        VBox section = new VBox(5);
        
        Label titleLabel = new Label(title + ":");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        
        TextArea contentArea = new TextArea(content);
        contentArea.setEditable(false);
        contentArea.setWrapText(true);
        contentArea.setPrefRowCount(2);
        
        section.getChildren().addAll(titleLabel, contentArea);
        return section;
    }
    
    private void refreshLaporanData() {
        laporanContent.getChildren().clear();
        
        List<LaporanBencanaData> laporanList = Dashboard.getAllLaporanBencana();
        
        if (laporanList.isEmpty()) {
            Label emptyLabel = new Label("Tidak ada data laporan bencana.");
            emptyLabel.setStyle("-fx-font-size: 14px;");
            laporanContent.getChildren().add(emptyLabel);
            return;
        }
        
        for (int i = 0; i < laporanList.size(); i++) {
            LaporanBencanaData laporan = laporanList.get(i);
            HBox laporanBox = createLaporanBox(laporan, i);
            laporanContent.getChildren().add(laporanBox);
        }
    }
    
    private HBox createLaporanBox(LaporanBencanaData laporan, int index) {
        HBox box = new HBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: #f0f8ff; -fx-background-radius: 5;");
        
        VBox detailsBox = new VBox(5);
        detailsBox.setPrefWidth(650);
        detailsBox.getChildren().addAll(
            new Label("Jenis Bencana: " + laporan.getJenisBencana()),
            new Label("Lokasi: " + laporan.getLokasi()),
            new Label("Deskripsi: " + laporan.getDeskripsi()),
            new Label("Tingkat Keparahan: " + laporan.getTingkatKeparahan()),
            new Label("Jumlah Korban: " + laporan.getJumlahKorban())
        );
        
        VBox actionsBox = new VBox(10);
        actionsBox.setAlignment(Pos.CENTER);
        
        Button deleteButton = new Button("Hapus");
        deleteButton.setStyle("-fx-background-color: #ff6b6b;");
        deleteButton.setOnAction(e -> {
            Dashboard.removeLaporanBencanaFromDatabase(index);
            refreshData();
        });
        
        actionsBox.getChildren().add(deleteButton);
        
        box.getChildren().addAll(detailsBox, actionsBox);
        return box;
    }
}
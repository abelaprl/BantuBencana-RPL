import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class Feedback extends Application {
    
    // Variabel untuk menyimpan data feedback sementara
    private FeedbackData currentFeedback;
    private ComboBox<String> laporanBencana;
    private ComboBox<String> jenisBencana;
    private ComboBox<String> lokasiLaporan;
    private TextArea[] feedbackAreas;
    private TextArea evaluasiText;
    private Label fileLabel;
    
    @Override
    public void start(Stage primaryStage) {
        // Inisialisasi data feedback
        currentFeedback = new FeedbackData();
        feedbackAreas = new TextArea[4];
        
        // Judul form
        Text title = new Text("Feedback Pasca Bencana");
        title.setFont(Font.font("Arial", 20));
        title.setStyle("-fx-font-weight: bold;");
        
        // Dropdown menus
        laporanBencana = new ComboBox<>();
        laporanBencana.getItems().addAll("Laporan Bencana 1", "Laporan Bencana 2", "Laporan Bencana 3");
        laporanBencana.setPromptText("Laporan Bencana*");
        laporanBencana.setPrefWidth(200);
        
        jenisBencana = new ComboBox<>();
        jenisBencana.getItems().addAll("Banjir", "Gempa Bumi", "Kebakaran", "Longsor", "Tsunami");
        jenisBencana.setPromptText("Pilih Jenis Bencana*");
        jenisBencana.setPrefWidth(200);
        
        lokasiLaporan = new ComboBox<>();
        lokasiLaporan.getItems().addAll("Jakarta", "Bandung", "Surabaya", "Medan", "Makassar");
        lokasiLaporan.setPromptText("Pilih Laporan Bencana*");
        lokasiLaporan.setPrefWidth(200);
        
        // Rating sections
        VBox ratingSection1 = createRatingSection("Seberapa Baik Penanganan Bencana Dilakukan?", 
                                                  "Apa yang membuatmu puas dengan penanganan bencana ini?", 0);
        
        VBox ratingSection2 = createRatingSection("Seberapa Baik Kinerja Relawan Bencana Ini?", 
                                                  "Bagaimana kami bisa membuatnya lebih baik lain kali?", 1);
        
        VBox ratingSection3 = createRatingSection("Seberapa Baik Alokasi Donasi/Bantuan Dilakukan?", 
                                                  "Kami ingin tahu lebih banyak. Apa yang terjadi?", 2);
        
        VBox ratingSection4 = createRatingSection("(Isi Jika Anda Mengalami Pengalaman Buruk) Bantuan yang Diberikan?", 
                                                  "Kami ingin tahu lebih banyak. Apa yang terjadi?", 3);
        
        // Evaluasi section
        Label evaluasiLabel = new Label("Adakah Evaluasi Lain yang Ingin Anda Sampaikan?");
        evaluasiLabel.setStyle("-fx-font-weight: bold;");
        evaluasiText = new TextArea();
        evaluasiText.setPromptText("Kritik, Saran");
        evaluasiText.setPrefRowCount(3);
        
        // Media upload section
        Label mediaLabel = new Label("Media Pendukung");
        mediaLabel.setStyle("-fx-font-weight: bold;");
        Button uploadButton = new Button("📁 Pilih File");
        uploadButton.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #ccc;");
        fileLabel = new Label("Belum ada file dipilih");
        
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Pilih Media Pendukung");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                currentFeedback.setMediaPendukung(selectedFile);
                fileLabel.setText("File dipilih: " + selectedFile.getName());
            }
        });
        
        // Buttons
        Button tutupButton = new Button("Tutup");
        tutupButton.setStyle("-fx-background-color: white; -fx-border-color: #ff6b6b; -fx-text-fill: #ff6b6b; -fx-pref-width: 100;");
        tutupButton.setOnAction(e -> primaryStage.close());
        
        Button kirimButton = new Button("Kirim");
        kirimButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-pref-width: 100;");
        kirimButton.setOnAction(e -> {
            // Simpan data ke feedback object
            saveFeedbackData();
            
            // Simpan ke database global
            Dashboard.addFeedbackToDatabase(currentFeedback);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Feedback Terkirim");
            alert.setHeaderText(null);
            alert.setContentText("Terima kasih! Feedback Anda telah berhasil dikirim dan disimpan ke database.");
            alert.showAndWait();
            
            primaryStage.close();
        });
        
        // Layout
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        
        HBox dropdownLayout = new HBox(10);
        dropdownLayout.getChildren().addAll(laporanBencana, jenisBencana, lokasiLaporan);
        
        VBox uploadLayout = new VBox(5);
        uploadLayout.getChildren().addAll(uploadButton, fileLabel);
        
        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER_RIGHT);
        buttonLayout.getChildren().addAll(tutupButton, kirimButton);
        
        mainLayout.getChildren().addAll(
            title, dropdownLayout, ratingSection1, ratingSection2, 
            ratingSection3, ratingSection4, evaluasiLabel, evaluasiText,
            mediaLabel, uploadLayout, buttonLayout
        );
        
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(800, 700);
        
        Scene scene = new Scene(scrollPane, 800, 700);
        primaryStage.setTitle("Feedback Pasca Bencana");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox createRatingSection(String question, String placeholder, int sectionIndex) {
        VBox section = new VBox(5);
        
        Label questionLabel = new Label(question);
        questionLabel.setStyle("-fx-font-weight: bold;");
        
        // Star rating
        HBox starBox = new HBox(5);
        Button[] stars = new Button[5];
        
        for (int i = 0; i < 5; i++) {
            stars[i] = new Button("⭐");
            stars[i].setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #ddd;");
            final int rating = i + 1;
            final int currentSection = sectionIndex;
            
            stars[i].setOnAction(e -> {
                // Update rating in feedback data
                switch (currentSection) {
                    case 0: currentFeedback.setRatingPenanganan(rating); break;
                    case 1: currentFeedback.setRatingKinerjaRelawan(rating); break;
                    case 2: currentFeedback.setRatingAlokasiDonasi(rating); break;
                    case 3: currentFeedback.setRatingPengalamanBuruk(rating); break;
                }
                
                // Update star display
                for (int j = 0; j < 5; j++) {
                    if (j < rating) {
                        stars[j].setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #FFD700;");
                    } else {
                        stars[j].setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #ddd;");
                    }
                }
                
                System.out.println("Section " + currentSection + " - Rating: " + rating + " stars");
            });
            starBox.getChildren().add(stars[i]);
        }
        
        TextArea commentArea = new TextArea();
        commentArea.setPromptText(placeholder);
        commentArea.setPrefRowCount(2);
        feedbackAreas[sectionIndex] = commentArea;
        
        section.getChildren().addAll(questionLabel, starBox, commentArea);
        return section;
    }
    
    private void saveFeedbackData() {
        // Simpan data dropdown
        currentFeedback.setLaporanBencana(laporanBencana.getValue());
        currentFeedback.setJenisBencana(jenisBencana.getValue());
        currentFeedback.setLokasiLaporan(lokasiLaporan.getValue());
        
        // Simpan feedback text
        currentFeedback.setFeedbackPenanganan(feedbackAreas[0].getText());
        currentFeedback.setFeedbackKinerjaRelawan(feedbackAreas[1].getText());
        currentFeedback.setFeedbackAlokasiDonasi(feedbackAreas[2].getText());
        currentFeedback.setFeedbackPengalamanBuruk(feedbackAreas[3].getText());
        
        // Simpan evaluasi tambahan
        currentFeedback.setEvaluasiTambahan(evaluasiText.getText());
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
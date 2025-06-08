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
import java.util.List;

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
        
        // Dropdown menus - MENGAMBIL DATA DARI DATABASE LAPORAN BENCANA
        laporanBencana = new ComboBox<>();
        updateLaporanBencanaDropdown(); // Method untuk mengisi dropdown dari database
        laporanBencana.setPromptText("Pilih Laporan Bencana*");
        laporanBencana.setPrefWidth(250);
        
        // Event listener untuk update jenis dan lokasi berdasarkan laporan yang dipilih
        laporanBencana.setOnAction(e -> {
            String selectedLaporan = laporanBencana.getValue();
            if (selectedLaporan != null) {
                updateJenisAndLokasiFromLaporan(selectedLaporan);
            }
        });
        
        jenisBencana = new ComboBox<>();
        jenisBencana.setPromptText("Jenis Bencana (Auto-filled)");
        jenisBencana.setPrefWidth(200);
        jenisBencana.setDisable(true); // Disabled karena auto-filled
        
        lokasiLaporan = new ComboBox<>();
        lokasiLaporan.setPromptText("Lokasi (Auto-filled)");
        lokasiLaporan.setPrefWidth(200);
        lokasiLaporan.setDisable(true); // Disabled karena auto-filled
        
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
        Button refreshButton = new Button("Refresh Data Laporan");
        refreshButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-pref-width: 150;");
        refreshButton.setOnAction(e -> {
            updateLaporanBencanaDropdown();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Data Diperbarui");
            alert.setHeaderText(null);
            alert.setContentText("Data laporan bencana telah diperbarui!");
            alert.showAndWait();
        });
        
        Button tutupButton = new Button("Tutup");
        tutupButton.setStyle("-fx-background-color: white; -fx-border-color: #ff6b6b; -fx-text-fill: #ff6b6b; -fx-pref-width: 100;");
        tutupButton.setOnAction(e -> primaryStage.close());
        
        Button kirimButton = new Button("Kirim");
        kirimButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-pref-width: 100;");
        kirimButton.setOnAction(e -> {
            // Validasi input
            if (laporanBencana.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Tidak Lengkap");
                alert.setHeaderText(null);
                alert.setContentText("Mohon pilih laporan bencana terlebih dahulu!");
                alert.showAndWait();
                return;
            }
            
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
        buttonLayout.getChildren().addAll(refreshButton, tutupButton, kirimButton);
        
        mainLayout.getChildren().addAll(
            title, dropdownLayout, ratingSection1, ratingSection2, 
            ratingSection3, ratingSection4, evaluasiLabel, evaluasiText,
            mediaLabel, uploadLayout, buttonLayout
        );
        
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(900, 700);
        
        Scene scene = new Scene(scrollPane, 900, 700);
        primaryStage.setTitle("Feedback Pasca Bencana");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    // Method untuk mengisi dropdown laporan bencana dari database
    private void updateLaporanBencanaDropdown() {
        laporanBencana.getItems().clear();
        List<LaporanBencanaData> laporanList = Dashboard.getAllLaporanBencana();
        
        if (laporanList.isEmpty()) {
            laporanBencana.getItems().add("Belum ada laporan bencana");
            laporanBencana.setDisable(true);
        } else {
            laporanBencana.setDisable(false);
            for (LaporanBencanaData laporan : laporanList) {
                laporanBencana.getItems().add(laporan.getDisplayName());
            }
        }
    }
    
    // Method untuk update jenis dan lokasi berdasarkan laporan yang dipilih
    private void updateJenisAndLokasiFromLaporan(String selectedLaporanDisplay) {
        List<LaporanBencanaData> laporanList = Dashboard.getAllLaporanBencana();
        
        for (LaporanBencanaData laporan : laporanList) {
            if (laporan.getDisplayName().equals(selectedLaporanDisplay)) {
                jenisBencana.getItems().clear();
                jenisBencana.getItems().add(laporan.getJenisBencana());
                jenisBencana.setValue(laporan.getJenisBencana());
                
                lokasiLaporan.getItems().clear();
                lokasiLaporan.getItems().add(laporan.getLokasiBencana());
                lokasiLaporan.setValue(laporan.getLokasiBencana());
                break;
            }
        }
    }
    
    private VBox createRatingSection(String question, String placeholder, int sectionIndex) {
        VBox section = new VBox(5);
        
        Label questionLabel = new Label(question);
        questionLabel.setStyle("-fx-font-weight: bold;");
        
        // Star rating dengan ukuran bintang yang lebih besar
        HBox starBox = new HBox(10); // Spacing antar bintang diperbesar
        starBox.setPadding(new Insets(5, 0, 5, 0)); // Padding atas dan bawah
        starBox.setAlignment(Pos.CENTER_LEFT);
        Button[] stars = new Button[5];
        
        for (int i = 0; i < 5; i++) {
            stars[i] = new Button("★"); // Menggunakan simbol bintang solid
            
            // Memperbesar ukuran bintang dan styling
            stars[i].setStyle(
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;" +
                "-fx-text-fill: #ddd;" +
                "-fx-font-size: 30px;" + // Ukuran font diperbesar
                "-fx-min-width: 40px;" + // Lebar minimum
                "-fx-min-height: 40px;" + // Tinggi minimum
                "-fx-padding: 5px;" // Padding dalam button
            );
            
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
                
                // Update star display dengan warna kuning yang lebih cerah
                for (int j = 0; j < 5; j++) {
                    if (j < rating) {
                        stars[j].setStyle(
                            "-fx-background-color: transparent;" +
                            "-fx-border-color: transparent;" +
                            "-fx-text-fill: #FFD700;" + // Gold color untuk bintang aktif
                            "-fx-font-size: 30px;" +
                            "-fx-min-width: 40px;" +
                            "-fx-min-height: 40px;" +
                            "-fx-padding: 5px;"
                        );
                    } else {
                        stars[j].setStyle(
                            "-fx-background-color: transparent;" +
                            "-fx-border-color: transparent;" +
                            "-fx-text-fill: #ddd;" +
                            "-fx-font-size: 30px;" +
                            "-fx-min-width: 40px;" +
                            "-fx-min-height: 40px;" +
                            "-fx-padding: 5px;"
                        );
                    }
                }
                
                System.out.println("Section " + currentSection + " - Rating: " + rating + " stars");
            });
            
            // Hover effect untuk bintang
            stars[i].setOnMouseEntered(e -> {
                if (!stars[0].getStyle().contains("#FFD700")) { // Jika belum dirating
                    ((Button)e.getSource()).setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;" +
                        "-fx-text-fill: #FFC107;" + // Amber color untuk hover
                        "-fx-font-size: 30px;" +
                        "-fx-min-width: 40px;" +
                        "-fx-min-height: 40px;" +
                        "-fx-padding: 5px;"
                    );
                }
            });
            
            stars[i].setOnMouseExited(e -> {
                if (!stars[0].getStyle().contains("#FFD700")) { // Jika belum dirating
                    ((Button)e.getSource()).setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;" +
                        "-fx-text-fill: #ddd;" +
                        "-fx-font-size: 30px;" +
                        "-fx-min-width: 40px;" +
                        "-fx-min-height: 40px;" +
                        "-fx-padding: 5px;"
                    );
                }
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
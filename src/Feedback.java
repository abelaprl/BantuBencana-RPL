// File: src/Feedback.java

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
import javafx.scene.Node; // Import Node

public class Feedback extends Application {

    private FeedbackData currentFeedback;
    private ComboBox<String> laporanBencana;
    private ComboBox<String> jenisBencana; // Ini tidak akan digunakan langsung untuk setting, tapi untuk tampilan
    private ComboBox<String> lokasiLaporan; // Ini tidak akan digunakan langsung untuk setting, tapi untuk tampilan
    private TextArea[] feedbackAreas;
    private TextArea evaluasiText;
    private Label fileLabel;
    
    private String currentUserEmail; // Untuk menyimpan email user yang login

    @Override
    public void start(Stage primaryStage) {
        // Ini adalah start default, jika Anda mencoba menjalankan Feedback.java secara mandiri.
        // Sebaiknya selalu panggil start(primaryStage, userEmail) dari Dashboard.
        start(primaryStage, "default_user@example.com"); // Email dummy jika dijalankan langsung
    }

    // Metode start yang akan dipanggil dari Dashboard.java
    public void start(Stage primaryStage, String userEmail) {
        this.currentUserEmail = userEmail; // Simpan email user yang login
        System.out.println("DEBUG Feedback Form: User email set to " + this.currentUserEmail);

        currentFeedback = new FeedbackData();
        currentFeedback.setUserEmail(this.currentUserEmail); // Set email ke FeedbackData

        feedbackAreas = new TextArea[4];

        Text title = new Text("Feedback Pasca Bencana");
        title.setFont(Font.font("Arial", 20));
        title.setStyle("-fx-font-weight: bold;");

        laporanBencana = new ComboBox<>();
        updateLaporanBencanaDropdown(); // Method untuk mengisi dropdown dari database
        laporanBencana.setPromptText("Pilih Laporan Bencana*");
        laporanBencana.setPrefWidth(250);

        laporanBencana.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Cari objek LaporanBencanaData yang sesuai
                LaporanBencanaData selectedLaporan = Dashboard.getAllLaporanBencana().stream()
                    // --- MODIFIKASI DIMULAI ---
                    // Ubah getJenis() menjadi getJenisBencana()
                    .filter(lb -> (lb.getJenisBencana() + " - " + lb.getLokasi()).equals(newVal)) // Sesuaikan dengan format display name
                    // --- MODIFIKASI BERAKHIR ---
                    .findFirst().orElse(null);

                if (selectedLaporan != null) {
                    // --- MODIFIKASI DIMULAI ---
                    // Ubah getJenis() menjadi getJenisBencana()
                    jenisBencana.setValue(selectedLaporan.getJenisBencana());
                    // --- MODIFIKASI BERAKHIR ---
                    lokasiLaporan.setValue(selectedLaporan.getLokasi());
                } else {
                    jenisBencana.setValue(null); // Clear if not found
                    lokasiLaporan.setValue(null); // Clear if not found
                }
            }
        });

        jenisBencana = new ComboBox<>();
        jenisBencana.getItems().addAll("Banjir", "Gempa Bumi", "Tsunami", "Kebakaran Hutan", "Tanah Longsor", "Angin Topan", "Lainnya");
        jenisBencana.setPromptText("Jenis Bencana*");
        jenisBencana.setPrefWidth(250);

        lokasiLaporan = new ComboBox<>();
        lokasiLaporan.getItems().addAll("Jakarta", "Yogyakarta", "Surabaya", "Bandung", "Medan", "Makassar", "Aceh", "Lombok", "Kalimantan Tengah", "Jawa Timur", "Lainnya");
        lokasiLaporan.setPromptText("Lokasi Laporan*");
        lokasiLaporan.setPrefWidth(250);

        // Bagian untuk rating dan komentar
        VBox penangananSection = createFeedbackSection(0, "Bagaimana penilaian Anda terhadap kecepatan dan efektivitas penanganan bencana?", "Berikan komentar tentang penanganan bencana", currentFeedback.getRatingPenanganan());
        VBox kinerjaSection = createFeedbackSection(1, "Bagaimana penilaian Anda terhadap kinerja relawan yang terlibat?", "Berikan komentar tentang kinerja relawan", currentFeedback.getRatingKinerjaRelawan());
        VBox alokasiSection = createFeedbackSection(2, "Bagaimana penilaian Anda terhadap alokasi donasi dan bantuan?", "Berikan komentar tentang alokasi donasi", currentFeedback.getRatingAlokasiDonasi());
        VBox pengalamanBurukSection = createFeedbackSection(3, "Apakah ada pengalaman buruk atau masalah yang Anda alami selama proses penanganan/bantuan?", "Jelaskan pengalaman buruk yang Anda alami", currentFeedback.getRatingPengalamanBuruk());


        // Evaluasi Tambahan
        evaluasiText = new TextArea();
        evaluasiText.setPromptText("Evaluasi tambahan atau saran untuk perbaikan...");
        evaluasiText.setPrefRowCount(3);

        // Upload Media Pendukung
        Button uploadButton = new Button("Upload Media Pendukung");
        fileLabel = new Label("Tidak ada file dipilih");
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Pilih Media Pendukung");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"),
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mov"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                currentFeedback.setMediaPendukungPath(selectedFile.getAbsolutePath());
                fileLabel.setText(selectedFile.getName());
            } else {
                currentFeedback.setMediaPendukungPath(null);
                fileLabel.setText("Tidak ada file dipilih");
            }
        });
        HBox uploadBox = new HBox(10, uploadButton, fileLabel);
        uploadBox.setAlignment(Pos.CENTER_LEFT);

        // Tombol Submit
        Button submitButton = new Button("Kirim Feedback");
        submitButton.setStyle("-fx-font-size: 18px; -fx-padding: 10 20; -fx-background-color: #28a745; -fx-text-fill: white;");
        submitButton.setOnAction(e -> {
            saveFeedbackData(); // Simpan data dari form ke currentFeedback object

            if (currentFeedback.isValid()) {
                Dashboard.addFeedback(currentFeedback); // Kirim feedback ke Dashboard
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Feedback berhasil dikirim!");
                primaryStage.close(); // Tutup form setelah submit
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Harap isi semua kolom wajib (*) dan berikan rating!");
            }
        });

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(
            title,
            new HBox(10, laporanBencana, jenisBencana, lokasiLaporan),
            penangananSection,
            kinerjaSection,
            alokasiSection,
            pengalamanBurukSection,
            new Label("Evaluasi Tambahan:"),
            evaluasiText,
            uploadBox,
            submitButton
        );

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Scene scene = new Scene(scrollPane, 700, 800);
        primaryStage.setTitle("Form Feedback Pasca Bencana");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateLaporanBencanaDropdown() {
        List<LaporanBencanaData> allLaporan = Dashboard.getAllLaporanBencana();
        laporanBencana.getItems().clear();
        for (LaporanBencanaData laporan : allLaporan) {
            // Menggunakan getDisplayName() jika ada, atau buat format sesuai kebutuhan
            // --- MODIFIKASI DIMULAI ---
            // Ubah getJenis() menjadi getJenisBencana()
            laporanBencana.getItems().add(laporan.getJenisBencana() + " - " + laporan.getLokasi());
            // --- MODIFIKASI BERAKHIR ---
        }
    }

    private VBox createFeedbackSection(int sectionIndex, String question, String placeholder, int initialRating) {
        VBox section = new VBox(5);
        section.setPadding(new Insets(10, 0, 10, 0));
        Label questionLabel = new Label(question);
        questionLabel.setFont(Font.font("System Bold", 14));
        questionLabel.setWrapText(true);

        HBox starBox = new HBox(5);
        starBox.setAlignment(Pos.CENTER_LEFT);
        Button[] stars = new Button[5];

        for (int i = 0; i < 5; i++) {
            final int rating = i + 1;
            stars[i] = new Button("★"); // Bintang
            stars[i].setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #ddd;" + // Default warna abu-abu
                "-fx-font-size: 30px;" +
                "-fx-min-width: 40px;" +
                "-fx-min-height: 40px;" +
                "-fx-padding: 5px;"
            );

            // Set initial rating (jika ada)
            if (rating <= initialRating) {
                stars[i].setStyle(stars[i].getStyle() + "-fx-text-fill: #FFD700;"); // Warna emas
            }

            stars[i].setOnAction(e -> {
                for (int j = 0; j < 5; j++) {
                    if (j < rating) {
                        stars[j].setStyle(stars[j].getStyle().replace("-fx-text-fill: #ddd;", "-fx-text-fill: #FFD700;"));
                    } else {
                        stars[j].setStyle(stars[j].getStyle().replace("-fx-text-fill: #FFD700;", "-fx-text-fill: #ddd;"));
                    }
                }
                switch (sectionIndex) {
                    case 0: currentFeedback.setRatingPenanganan(rating); break;
                    case 1: currentFeedback.setRatingKinerjaRelawan(rating); break;
                    case 2: currentFeedback.setRatingAlokasiDonasi(rating); break;
                    case 3: currentFeedback.setRatingPengalamanBuruk(rating); break;
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
        currentFeedback.setLaporanBencana(laporanBencana.getValue());
        currentFeedback.setJenisBencana(jenisBencana.getValue()); // Ini hanya mengambil dari dropdown
        currentFeedback.setLokasiLaporan(lokasiLaporan.getValue()); // Ini hanya mengambil dari dropdown

        currentFeedback.setFeedbackPenanganan(feedbackAreas[0].getText());
        currentFeedback.setFeedbackKinerjaRelawan(feedbackAreas[1].getText());
        currentFeedback.setFeedbackAlokasiDonasi(feedbackAreas[2].getText());
        currentFeedback.setFeedbackPengalamanBuruk(feedbackAreas[3].getText());
        currentFeedback.setEvaluasiTambahan(evaluasiText.getText());
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
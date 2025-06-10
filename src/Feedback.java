// File: src/Feedback.java

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;
import javafx.scene.Node; // Import Node

public class Feedback extends Application {

    private FeedbackData currentFeedback;
    private ComboBox<String> laporanBencana;
    private ComboBox<String> jenisBencana;
    private ComboBox<String> lokasiLaporan;
    private TextArea[] feedbackAreas;
    private TextArea evaluasiText;
    private Label fileLabel;
    
    private String currentUserEmail;

    @Override
    public void start(Stage primaryStage) {
        start(primaryStage, "default_user@example.com");
    }

    public void start(Stage primaryStage, String userEmail) {
        this.currentUserEmail = userEmail;
        System.out.println("DEBUG Feedback Form: User email set to " + this.currentUserEmail);

        currentFeedback = new FeedbackData();
        currentFeedback.setUserEmail(this.currentUserEmail);

        feedbackAreas = new TextArea[4];

        // Main container dengan background gradient
        VBox root = new VBox(25);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #f8fdff);");

        // Header card
        VBox headerCard = new VBox(10);
        headerCard.setAlignment(Pos.CENTER);
        headerCard.setPadding(new Insets(25));
        headerCard.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); " +
                           "-fx-background-radius: 15; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 10, 0, 0, 2);");

        Text title = new Text("📝 Feedback Pasca Bencana");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setStyle("-fx-fill: #1565c0;");

        Text subtitle = new Text("Bantu kami meningkatkan pelayanan dengan feedback Anda");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitle.setStyle("-fx-fill: #42a5f5;");

        headerCard.getChildren().addAll(title, subtitle);

        // Form card
        VBox formCard = new VBox(20);
        formCard.setPadding(new Insets(30));
        formCard.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); " +
                         "-fx-background-radius: 15; " +
                         "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.08), 12, 0, 0, 3);");

        // Styling untuk labels
        String labelStyle = "-fx-font-family: 'Segoe UI'; " +
                           "-fx-font-size: 14px; " +
                           "-fx-font-weight: 600; " +
                           "-fx-text-fill: #1565c0;";

        // Styling untuk ComboBox
        String comboBoxStyle = "-fx-background-color: #f5f9ff; " +
                              "-fx-border-color: #bbdefb; " +
                              "-fx-border-width: 1.5; " +
                              "-fx-border-radius: 8; " +
                              "-fx-background-radius: 8; " +
                              "-fx-padding: 12; " +
                              "-fx-font-family: 'Segoe UI'; " +
                              "-fx-font-size: 13px; " +
                              "-fx-text-fill: #1565c0;";

        // Dropdown section
        VBox dropdownSection = new VBox(15);
        
        laporanBencana = new ComboBox<>();
        updateLaporanBencanaDropdown();
        laporanBencana.setPromptText("Pilih Laporan Bencana*");
        laporanBencana.setPrefWidth(220);
        laporanBencana.setStyle(comboBoxStyle);
        laporanBencana.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                laporanBencana.setStyle(comboBoxStyle.replace("-fx-border-color: #bbdefb;", "-fx-border-color: #42a5f5;")
                                                   .replace("-fx-background-color: #f5f9ff;", "-fx-background-color: #ffffff;"));
            } else {
                laporanBencana.setStyle(comboBoxStyle);
            }
        });

        laporanBencana.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                LaporanBencanaData selectedLaporan = Dashboard.getAllLaporanBencana().stream()
                    .filter(lb -> (lb.getJenisBencana() + " - " + lb.getLokasi()).equals(newVal))
                    .findFirst().orElse(null);

                if (selectedLaporan != null) {
                    jenisBencana.setValue(selectedLaporan.getJenisBencana());
                    lokasiLaporan.setValue(selectedLaporan.getLokasi());
                } else {
                    jenisBencana.setValue(null);
                    lokasiLaporan.setValue(null);
                }
            }
        });

        jenisBencana = new ComboBox<>();
        jenisBencana.getItems().addAll("Banjir", "Gempa Bumi", "Tsunami", "Kebakaran Hutan", "Tanah Longsor", "Angin Topan", "Lainnya");
        jenisBencana.setPromptText("Jenis Bencana*");
        jenisBencana.setPrefWidth(220);
        jenisBencana.setStyle(comboBoxStyle);
        jenisBencana.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                jenisBencana.setStyle(comboBoxStyle.replace("-fx-border-color: #bbdefb;", "-fx-border-color: #42a5f5;")
                                                 .replace("-fx-background-color: #f5f9ff;", "-fx-background-color: #ffffff;"));
            } else {
                jenisBencana.setStyle(comboBoxStyle);
            }
        });

        lokasiLaporan = new ComboBox<>();
        lokasiLaporan.getItems().addAll("Jakarta", "Yogyakarta", "Surabaya", "Bandung", "Medan", "Makassar", "Aceh", "Lombok", "Kalimantan Tengah", "Jawa Timur", "Lainnya");
        lokasiLaporan.setPromptText("Lokasi Laporan*");
        lokasiLaporan.setPrefWidth(220);
        lokasiLaporan.setStyle(comboBoxStyle);
        lokasiLaporan.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                lokasiLaporan.setStyle(comboBoxStyle.replace("-fx-border-color: #bbdefb;", "-fx-border-color: #42a5f5;")
                                                   .replace("-fx-background-color: #f5f9ff;", "-fx-background-color: #ffffff;"));
            } else {
                lokasiLaporan.setStyle(comboBoxStyle);
            }
        });

        HBox dropdownBox = new HBox(15);
        dropdownBox.setAlignment(Pos.CENTER);
        dropdownBox.getChildren().addAll(laporanBencana, jenisBencana, lokasiLaporan);
        
        dropdownSection.getChildren().add(dropdownBox);

        // Rating sections
        VBox ratingsContainer = new VBox(25);
        
        VBox penangananSection = createFeedbackSection(0, 
            "⚡ Bagaimana penilaian Anda terhadap kecepatan dan efektivitas penanganan bencana?", 
            "Berikan komentar tentang penanganan bencana", 
            currentFeedback.getRatingPenanganan());
            
        VBox kinerjaSection = createFeedbackSection(1, 
            "👥 Bagaimana penilaian Anda terhadap kinerja relawan yang terlibat?", 
            "Berikan komentar tentang kinerja relawan", 
            currentFeedback.getRatingKinerjaRelawan());
            
        VBox alokasiSection = createFeedbackSection(2, 
            "💰 Bagaimana penilaian Anda terhadap alokasi donasi dan bantuan?", 
            "Berikan komentar tentang alokasi donasi", 
            currentFeedback.getRatingAlokasiDonasi());
            
        VBox pengalamanBurukSection = createFeedbackSection(3, 
            "⚠️ Apakah ada pengalaman buruk atau masalah yang Anda alami selama proses penanganan/bantuan?", 
            "Jelaskan pengalaman buruk yang Anda alami", 
            currentFeedback.getRatingPengalamanBuruk());

        ratingsContainer.getChildren().addAll(penangananSection, kinerjaSection, alokasiSection, pengalamanBurukSection);

        // Additional evaluation section
        VBox evaluasiSection = new VBox(10);
        Label evaluasiLabel = new Label("💭 Evaluasi Tambahan");
        evaluasiLabel.setStyle(labelStyle);
        
        evaluasiText = new TextArea();
        evaluasiText.setPromptText("Evaluasi tambahan atau saran untuk perbaikan...");
        evaluasiText.setPrefRowCount(4);
        evaluasiText.setStyle("-fx-background-color: #f5f9ff; " +
                             "-fx-border-color: #bbdefb; " +
                             "-fx-border-width: 1.5; " +
                             "-fx-border-radius: 8; " +
                             "-fx-background-radius: 8; " +
                             "-fx-padding: 12; " +
                             "-fx-font-family: 'Segoe UI'; " +
                             "-fx-font-size: 13px; " +
                             "-fx-text-fill: #1565c0;");
        evaluasiText.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                evaluasiText.setStyle("-fx-background-color: #ffffff; " +
                                     "-fx-border-color: #42a5f5; " +
                                     "-fx-border-width: 1.5; " +
                                     "-fx-border-radius: 8; " +
                                     "-fx-background-radius: 8; " +
                                     "-fx-padding: 12; " +
                                     "-fx-font-family: 'Segoe UI'; " +
                                     "-fx-font-size: 13px; " +
                                     "-fx-text-fill: #1565c0;");
            } else {
                evaluasiText.setStyle("-fx-background-color: #f5f9ff; " +
                                     "-fx-border-color: #bbdefb; " +
                                     "-fx-border-width: 1.5; " +
                                     "-fx-border-radius: 8; " +
                                     "-fx-background-radius: 8; " +
                                     "-fx-padding: 12; " +
                                     "-fx-font-family: 'Segoe UI'; " +
                                     "-fx-font-size: 13px; " +
                                     "-fx-text-fill: #1565c0;");
            }
        });
        
        evaluasiSection.getChildren().addAll(evaluasiLabel, evaluasiText);

        // Upload section
        VBox uploadSection = new VBox(10);
        Label uploadLabel = new Label("📎 Media Pendukung");
        uploadLabel.setStyle(labelStyle);
        
        Button uploadButton = new Button("📁 Pilih File");
        uploadButton.setStyle("-fx-background-color: #e3f2fd; " +
                             "-fx-text-fill: #1565c0; " +
                             "-fx-border-color: #42a5f5; " +
                             "-fx-border-width: 1.5; " +
                             "-fx-border-radius: 8; " +
                             "-fx-background-radius: 8; " +
                             "-fx-padding: 10 20; " +
                             "-fx-font-family: 'Segoe UI'; " +
                             "-fx-font-size: 13px; " +
                             "-fx-cursor: hand;");
        uploadButton.setOnMouseEntered(e -> 
            uploadButton.setStyle("-fx-background-color: #bbdefb; " +
                                 "-fx-text-fill: #1565c0; " +
                                 "-fx-border-color: #1976d2; " +
                                 "-fx-border-width: 1.5; " +
                                 "-fx-border-radius: 8; " +
                                 "-fx-background-radius: 8; " +
                                 "-fx-padding: 10 20; " +
                                 "-fx-font-family: 'Segoe UI'; " +
                                 "-fx-font-size: 13px; " +
                                 "-fx-cursor: hand;"));
        uploadButton.setOnMouseExited(e -> 
            uploadButton.setStyle("-fx-background-color: #e3f2fd; " +
                                 "-fx-text-fill: #1565c0; " +
                                 "-fx-border-color: #42a5f5; " +
                                 "-fx-border-width: 1.5; " +
                                 "-fx-border-radius: 8; " +
                                 "-fx-background-radius: 8; " +
                                 "-fx-padding: 10 20; " +
                                 "-fx-font-family: 'Segoe UI'; " +
                                 "-fx-font-size: 13px; " +
                                 "-fx-cursor: hand;"));
        
        fileLabel = new Label("Tidak ada file dipilih");
        fileLabel.setStyle("-fx-font-family: 'Segoe UI'; " +
                          "-fx-font-size: 12px; " +
                          "-fx-text-fill: #666;");
        
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
                fileLabel.setText("📄 " + selectedFile.getName());
                fileLabel.setStyle("-fx-font-family: 'Segoe UI'; " +
                                  "-fx-font-size: 12px; " +
                                  "-fx-text-fill: #1565c0;");
            } else {
                currentFeedback.setMediaPendukungPath(null);
                fileLabel.setText("Tidak ada file dipilih");
                fileLabel.setStyle("-fx-font-family: 'Segoe UI'; " +
                                  "-fx-font-size: 12px; " +
                                  "-fx-text-fill: #666;");
            }
        });
        
        HBox uploadBox = new HBox(15, uploadButton, fileLabel);
        uploadBox.setAlignment(Pos.CENTER_LEFT);
        uploadSection.getChildren().addAll(uploadLabel, uploadBox);

        // Submit button
        Button submitButton = new Button("✨ Kirim Feedback");
        submitButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        submitButton.setStyle("-fx-background-color: linear-gradient(to bottom, #42a5f5, #1976d2); " +
                             "-fx-text-fill: white; " +
                             "-fx-background-radius: 25; " +
                             "-fx-padding: 15 40 15 40; " +
                             "-fx-cursor: hand; " +
                             "-fx-effect: dropshadow(gaussian, rgba(25, 118, 210, 0.3), 8, 0, 0, 2);");
        submitButton.setOnMouseEntered(e -> 
            submitButton.setStyle("-fx-background-color: linear-gradient(to bottom, #64b5f6, #1e88e5); " +
                                 "-fx-text-fill: white; " +
                                 "-fx-background-radius: 25; " +
                                 "-fx-padding: 15 40 15 40; " +
                                 "-fx-cursor: hand; " +
                                 "-fx-effect: dropshadow(gaussian, rgba(25, 118, 210, 0.4), 12, 0, 0, 3); " +
                                 "-fx-scale-y: 1.05;"));
        submitButton.setOnMouseExited(e -> 
            submitButton.setStyle("-fx-background-color: linear-gradient(to bottom, #42a5f5, #1976d2); " +
                                 "-fx-text-fill: white; " +
                                 "-fx-background-radius: 25; " +
                                 "-fx-padding: 15 40 15 40; " +
                                 "-fx-cursor: hand; " +
                                 "-fx-effect: dropshadow(gaussian, rgba(25, 118, 210, 0.3), 8, 0, 0, 2); " +
                                 "-fx-scale-y: 1.0;"));
        
        submitButton.setOnAction(e -> {
            saveFeedbackData();

            if (currentFeedback.isValid()) {
                Dashboard.addFeedback(currentFeedback);
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Feedback berhasil dikirim! Terima kasih atas masukan Anda.");
                primaryStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Harap isi semua kolom wajib (*) dan berikan rating!");
            }
        });

        HBox submitBox = new HBox();
        submitBox.setAlignment(Pos.CENTER);
        submitBox.setPadding(new Insets(20, 0, 0, 0));
        submitBox.getChildren().add(submitButton);

        formCard.getChildren().addAll(
            dropdownSection,
            ratingsContainer,
            evaluasiSection,
            uploadSection,
            submitBox
        );

        root.getChildren().addAll(headerCard, formCard);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; " +
                           "-fx-background: transparent;");

        Scene scene = new Scene(scrollPane, 750, 900);
        primaryStage.setTitle("Form Feedback Pasca Bencana");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateLaporanBencanaDropdown() {
        List<LaporanBencanaData> allLaporan = Dashboard.getAllLaporanBencana();
        laporanBencana.getItems().clear();
        for (LaporanBencanaData laporan : allLaporan) {
            laporanBencana.getItems().add(laporan.getJenisBencana() + " - " + laporan.getLokasi());
        }
    }

    private VBox createFeedbackSection(int sectionIndex, String question, String placeholder, int initialRating) {
        VBox section = new VBox(15);
        section.setPadding(new Insets(20));
        section.setStyle("-fx-background-color: rgba(227, 242, 253, 0.3); " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-color: rgba(187, 222, 251, 0.5); " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 12;");
        
        Label questionLabel = new Label(question);
        questionLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        questionLabel.setStyle("-fx-text-fill: #1565c0;");
        questionLabel.setWrapText(true);
        questionLabel.setPrefWidth(650);

        // Star rating container
        VBox ratingContainer = new VBox(10);
        Label ratingLabel = new Label("⭐ Berikan Rating (1-5 bintang):");
        ratingLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        ratingLabel.setStyle("-fx-text-fill: #42a5f5;");

        HBox starBox = new HBox(8);
        starBox.setAlignment(Pos.CENTER_LEFT);
        Button[] stars = new Button[5];

        for (int i = 0; i < 5; i++) {
            final int rating = i + 1;
            stars[i] = new Button("★");
            stars[i].setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #e0e0e0;" +
                "-fx-font-size: 32px;" +
                "-fx-min-width: 45px;" +
                "-fx-min-height: 45px;" +
                "-fx-padding: 5px;" +
                "-fx-cursor: hand;" +
                "-fx-border-radius: 50%; " +
                "-fx-background-radius: 50%;"
            );

            // Set initial rating
            if (rating <= initialRating) {
                stars[i].setStyle(stars[i].getStyle() + "-fx-text-fill: #FFD700;");
            }

            // Hover effects
            stars[i].setOnMouseEntered(e -> {
                for (int j = 0; j < rating; j++) {
                    stars[j].setStyle(stars[j].getStyle().replaceAll("-fx-text-fill: [^;]+;", "-fx-text-fill: #FFA726;"));
                }
            });

            stars[i].setOnMouseExited(e -> {
                for (int j = 0; j < 5; j++) {
                    int currentRating = getCurrentRating(sectionIndex);
                    if (j < currentRating) {
                        stars[j].setStyle(stars[j].getStyle().replaceAll("-fx-text-fill: [^;]+;", "-fx-text-fill: #FFD700;"));
                    } else {
                        stars[j].setStyle(stars[j].getStyle().replaceAll("-fx-text-fill: [^;]+;", "-fx-text-fill: #e0e0e0;"));
                    }
                }
            });

            stars[i].setOnAction(e -> {
                for (int j = 0; j < 5; j++) {
                    if (j < rating) {
                        stars[j].setStyle(stars[j].getStyle().replaceAll("-fx-text-fill: [^;]+;", "-fx-text-fill: #FFD700;"));
                    } else {
                        stars[j].setStyle(stars[j].getStyle().replaceAll("-fx-text-fill: [^;]+;", "-fx-text-fill: #e0e0e0;"));
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

        ratingContainer.getChildren().addAll(ratingLabel, starBox);

        // Comment area
        TextArea commentArea = new TextArea();
        commentArea.setPromptText(placeholder);
        commentArea.setPrefRowCount(3);
        commentArea.setStyle("-fx-background-color: #ffffff; " +
                            "-fx-border-color: #bbdefb; " +
                            "-fx-border-width: 1.5; " +
                            "-fx-border-radius: 8; " +
                            "-fx-background-radius: 8; " +
                            "-fx-padding: 12; " +
                            "-fx-font-family: 'Segoe UI'; " +
                            "-fx-font-size: 13px; " +
                            "-fx-text-fill: #1565c0;");
        commentArea.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                commentArea.setStyle("-fx-background-color: #ffffff; " +
                                    "-fx-border-color: #42a5f5; " +
                                    "-fx-border-width: 2; " +
                                    "-fx-border-radius: 8; " +
                                    "-fx-background-radius: 8; " +
                                    "-fx-padding: 12; " +
                                    "-fx-font-family: 'Segoe UI'; " +
                                    "-fx-font-size: 13px; " +
                                    "-fx-text-fill: #1565c0;");
            } else {
                commentArea.setStyle("-fx-background-color: #ffffff; " +
                                    "-fx-border-color: #bbdefb; " +
                                    "-fx-border-width: 1.5; " +
                                    "-fx-border-radius: 8; " +
                                    "-fx-background-radius: 8; " +
                                    "-fx-padding: 12; " +
                                    "-fx-font-family: 'Segoe UI'; " +
                                    "-fx-font-size: 13px; " +
                                    "-fx-text-fill: #1565c0;");
            }
        });
        
        feedbackAreas[sectionIndex] = commentArea;

        section.getChildren().addAll(questionLabel, ratingContainer, commentArea);
        return section;
    }

    private int getCurrentRating(int sectionIndex) {
        switch (sectionIndex) {
            case 0: return currentFeedback.getRatingPenanganan();
            case 1: return currentFeedback.getRatingKinerjaRelawan();
            case 2: return currentFeedback.getRatingAlokasiDonasi();
            case 3: return currentFeedback.getRatingPengalamanBuruk();
            default: return 0;
        }
    }

    private void saveFeedbackData() {
        currentFeedback.setLaporanBencana(laporanBencana.getValue());
        currentFeedback.setJenisBencana(jenisBencana.getValue());
        currentFeedback.setLokasiLaporan(lokasiLaporan.getValue());

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
        
        // Styling untuk alert dialog
        alert.getDialogPane().setStyle("-fx-background-color: #f8fdff; " +
                                     "-fx-border-color: #bbdefb; " +
                                     "-fx-border-width: 2; " +
                                     "-fx-border-radius: 10; " +
                                     "-fx-background-radius: 10;");
        
        // Styling untuk button di alert
        alert.getDialogPane().lookupButton(ButtonType.OK).setStyle(
            "-fx-background-color: #42a5f5; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 15; " +
            "-fx-padding: 8 20 8 20; " +
            "-fx-font-weight: bold;");
            
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
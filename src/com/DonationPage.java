package com;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional; // Import untuk Alert

public class DonationPage extends Application {

    private String currentUserEmail;
    private ComboBox<String> disasterComboBox;
    private ComboBox<String> donationTypeComboBox;
    private TextField amountOrDescriptionField;

    // BARU: Elemen UI tambahan untuk alur donasi uang/barang
    private Label paymentMethodLabel;
    private TextField paymentMethodField;
    private Label dropOffLocationLabel;
    private TextArea dropOffLocationArea;

    public DonationPage(String userEmail) {
        this.currentUserEmail = userEmail;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Halaman Donasi");

        // Main container dengan background gradient
        VBox root = new VBox(25);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #f8fdff); " +
                     "-fx-background-radius: 0;");

        // Header card
        VBox headerCard = new VBox(10);
        headerCard.setAlignment(Pos.CENTER);
        headerCard.setPadding(new Insets(25));
        headerCard.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); " +
                           "-fx-background-radius: 15; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 10, 0, 0, 2);");

        Label titleLabel = new Label("💙 Donasi untuk Korban Bencana");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #1565c0; -fx-alignment: center;");

        Label subtitleLabel = new Label("Setiap bantuan Anda sangat berarti");
        subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitleLabel.setStyle("-fx-text-fill: #42a5f5; -fx-alignment: center;");

        headerCard.getChildren().addAll(titleLabel, subtitleLabel);

        // Form card
        VBox formCard = new VBox(15);
        formCard.setPadding(new Insets(30));
        formCard.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); " +
                         "-fx-background-radius: 15; " +
                         "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.08), 12, 0, 0, 3);");

        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(20);
        formGrid.setAlignment(Pos.CENTER_LEFT);

        // Styling untuk labels
        String labelStyle = "-fx-font-family: 'Segoe UI'; " +
                           "-fx-font-size: 14px; " +
                           "-fx-font-weight: 600; " +
                           "-fx-text-fill: #1565c0;";

        // Styling untuk input fields
        String inputStyle = "-fx-background-color: #f5f9ff; " +
                           "-fx-border-color: #bbdefb; " +
                           "-fx-border-width: 1.5; " +
                           "-fx-border-radius: 8; " +
                           "-fx-background-radius: 8; " +
                           "-fx-padding: 12; " +
                           "-fx-font-family: 'Segoe UI'; " +
                           "-fx-font-size: 13px; " +
                           "-fx-text-fill: #1565c0; " +
                           "-fx-pref-width: 280;";

        String inputFocusStyle = inputStyle + 
                                "-fx-border-color: #42a5f5; " +
                                "-fx-background-color: #ffffff;";

        // Pilih Laporan Bencana
        Label disasterLabel = new Label("🏔️ Pilih Laporan Bencana");
        disasterLabel.setStyle(labelStyle);
        disasterComboBox = new ComboBox<>();
        disasterComboBox.setPromptText("Pilih bencana yang ingin didonasikan");
        disasterComboBox.setStyle(inputStyle);
        disasterComboBox.setPrefWidth(280);
        disasterComboBox.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                disasterComboBox.setStyle(inputFocusStyle);
            } else {
                disasterComboBox.setStyle(inputStyle);
            }
        });
        populateDisasterComboBox();

        formGrid.add(disasterLabel, 0, 0);
        formGrid.add(disasterComboBox, 1, 0);

        // Jenis Donasi
        Label donationTypeLabel = new Label("💝 Jenis Donasi");
        donationTypeLabel.setStyle(labelStyle);
        donationTypeComboBox = new ComboBox<>(FXCollections.observableArrayList("Uang", "Barang"));
        donationTypeComboBox.setPromptText("Pilih jenis donasi");
        donationTypeComboBox.setStyle(inputStyle);
        donationTypeComboBox.setPrefWidth(280);
        donationTypeComboBox.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                donationTypeComboBox.setStyle(inputFocusStyle);
            } else {
                donationTypeComboBox.setStyle(inputStyle);
            }
        });

        formGrid.add(donationTypeLabel, 0, 1);
        formGrid.add(donationTypeComboBox, 1, 1);

        // Jumlah/Deskripsi
        Label amountOrDescriptionLabel = new Label("📝 Jumlah/Deskripsi");
        amountOrDescriptionLabel.setStyle(labelStyle);
        amountOrDescriptionField = new TextField();
        amountOrDescriptionField.setPromptText("Cth: 100000 (uang), Selimut (barang)");
        amountOrDescriptionField.setStyle(inputStyle);
        amountOrDescriptionField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                amountOrDescriptionField.setStyle(inputFocusStyle);
            } else {
                amountOrDescriptionField.setStyle(inputStyle);
            }
        });

        formGrid.add(amountOrDescriptionLabel, 0, 2);
        formGrid.add(amountOrDescriptionField, 1, 2);

        // BARU: Elemen UI dinamis berdasarkan jenis donasi
        paymentMethodLabel = new Label("💳 Metode Pembayaran");
        paymentMethodLabel.setStyle(labelStyle);
        paymentMethodField = new TextField();
        paymentMethodField.setPromptText("Cth: Transfer Bank, E-wallet");
        paymentMethodField.setStyle(inputStyle);
        paymentMethodField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                paymentMethodField.setStyle(inputFocusStyle);
            } else {
                paymentMethodField.setStyle(inputStyle);
            }
        });

        dropOffLocationLabel = new Label("📍 Lokasi Drop-off");
        dropOffLocationLabel.setStyle(labelStyle);
        dropOffLocationArea = new TextArea();
        dropOffLocationArea.setPromptText("Alamat lengkap atau koordinat lokasi drop-off");
        dropOffLocationArea.setPrefRowCount(3);
        dropOffLocationArea.setPrefWidth(280);
        dropOffLocationArea.setWrapText(true);
        dropOffLocationArea.setStyle("-fx-background-color: #f5f9ff; " +
                                   "-fx-border-color: #bbdefb; " +
                                   "-fx-border-width: 1.5; " +
                                   "-fx-border-radius: 8; " +
                                   "-fx-background-radius: 8; " +
                                   "-fx-padding: 12; " +
                                   "-fx-font-family: 'Segoe UI'; " +
                                   "-fx-font-size: 13px; " +
                                   "-fx-text-fill: #1565c0;");
        dropOffLocationArea.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                dropOffLocationArea.setStyle("-fx-background-color: #ffffff; " +
                                           "-fx-border-color: #42a5f5; " +
                                           "-fx-border-width: 1.5; " +
                                           "-fx-border-radius: 8; " +
                                           "-fx-background-radius: 8; " +
                                           "-fx-padding: 12; " +
                                           "-fx-font-family: 'Segoe UI'; " +
                                           "-fx-font-size: 13px; " +
                                           "-fx-text-fill: #1565c0;");
            } else {
                dropOffLocationArea.setStyle("-fx-background-color: #f5f9ff; " +
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

        // Listener untuk Jenis Donasi
        donationTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            formGrid.getChildren().removeAll(paymentMethodLabel, paymentMethodField, dropOffLocationLabel, dropOffLocationArea);

            if ("Uang".equals(newVal)) {
                amountOrDescriptionLabel.setText("💰 Jumlah Donasi (Rp)");
                amountOrDescriptionField.setPromptText("Cth: 100000");
                formGrid.add(paymentMethodLabel, 0, 3);
                formGrid.add(paymentMethodField, 1, 3);
            } else if ("Barang".equals(newVal)) {
                amountOrDescriptionLabel.setText("📦 Deskripsi Barang");
                amountOrDescriptionField.setPromptText("Cth: Selimut, Pakaian layak pakai");
                formGrid.add(dropOffLocationLabel, 0, 3);
                formGrid.add(dropOffLocationArea, 1, 3);
            } else {
                amountOrDescriptionLabel.setText("📝 Jumlah/Deskripsi");
                amountOrDescriptionField.setPromptText("Cth: 100000 (uang), Selimut (barang)");
            }
        });

        formCard.getChildren().add(formGrid);

        // Button container
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(15, 0, 0, 0));

        // Submit button dengan styling modern
        Button submitButton = new Button("✨ Kirim Donasi");
        submitButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        submitButton.setStyle("-fx-background-color: linear-gradient(to bottom, #42a5f5, #1976d2); " +
                             "-fx-text-fill: white; " +
                             "-fx-background-radius: 25; " +
                             "-fx-padding: 12 30 12 30; " +
                             "-fx-cursor: hand; " +
                             "-fx-effect: dropshadow(gaussian, rgba(25, 118, 210, 0.3), 8, 0, 0, 2);");
        submitButton.setOnMouseEntered(e -> 
            submitButton.setStyle("-fx-background-color: linear-gradient(to bottom, #64b5f6, #1e88e5); " +
                                 "-fx-text-fill: white; " +
                                 "-fx-background-radius: 25; " +
                                 "-fx-padding: 12 30 12 30; " +
                                 "-fx-cursor: hand; " +
                                 "-fx-effect: dropshadow(gaussian, rgba(25, 118, 210, 0.4), 12, 0, 0, 3); " +
                                 "-fx-scale-y: 1.05;"));
        submitButton.setOnMouseExited(e -> 
            submitButton.setStyle("-fx-background-color: linear-gradient(to bottom, #42a5f5, #1976d2); " +
                                 "-fx-text-fill: white; " +
                                 "-fx-background-radius: 25; " +
                                 "-fx-padding: 12 30 12 30; " +
                                 "-fx-cursor: hand; " +
                                 "-fx-effect: dropshadow(gaussian, rgba(25, 118, 210, 0.3), 8, 0, 0, 2); " +
                                 "-fx-scale-y: 1.0;"));
        submitButton.setOnAction(e -> processDonation(primaryStage));

        // Back button dengan styling modern
        Button backButton = new Button("← Kembali");
        backButton.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        backButton.setStyle("-fx-background-color: transparent; " +
                           "-fx-text-fill: #1976d2; " +
                           "-fx-border-color: #42a5f5; " +
                           "-fx-border-width: 2; " +
                           "-fx-border-radius: 25; " +
                           "-fx-background-radius: 25; " +
                           "-fx-padding: 12 25 12 25; " +
                           "-fx-cursor: hand;");
        backButton.setOnMouseEntered(e -> 
            backButton.setStyle("-fx-background-color: #e3f2fd; " +
                               "-fx-text-fill: #1565c0; " +
                               "-fx-border-color: #1976d2; " +
                               "-fx-border-width: 2; " +
                               "-fx-border-radius: 25; " +
                               "-fx-background-radius: 25; " +
                               "-fx-padding: 12 25 12 25; " +
                               "-fx-cursor: hand;"));
        backButton.setOnMouseExited(e -> 
            backButton.setStyle("-fx-background-color: transparent; " +
                               "-fx-text-fill: #1976d2; " +
                               "-fx-border-color: #42a5f5; " +
                               "-fx-border-width: 2; " +
                               "-fx-border-radius: 25; " +
                               "-fx-background-radius: 25; " +
                               "-fx-padding: 12 25 12 25; " +
                               "-fx-cursor: hand;"));
        backButton.setOnAction(e -> primaryStage.close());

        buttonBox.getChildren().addAll(backButton, submitButton);

        root.getChildren().addAll(headerCard, formCard, buttonBox);

        Scene scene = new Scene(root, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void populateDisasterComboBox() {
        List<LaporanBencanaData> laporanList = Dashboard.getAllLaporanBencana();
        if (laporanList != null && !laporanList.isEmpty()) {
            for (LaporanBencanaData laporan : laporanList) {
                disasterComboBox.getItems().add(laporan.getJenisBencana() + " - " + laporan.getLokasi());
            }
        } else {
            disasterComboBox.setDisable(true);
            disasterComboBox.setPromptText("Belum ada laporan bencana");
        }
    }

    // BARU: Metode untuk memproses donasi sesuai jenis
    private void processDonation(Stage stage) {
        String selectedDisaster = disasterComboBox.getValue();
        String donationType = donationTypeComboBox.getValue();
        String amountOrDescription = amountOrDescriptionField.getText().trim();
        String additionalInfo = ""; // Untuk metode pembayaran atau lokasi drop-off

        if (selectedDisaster == null || selectedDisaster.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Pilih laporan bencana terlebih dahulu.");
            return;
        }
        if (donationType == null || donationType.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Pilih jenis donasi (Uang/Barang).");
            return;
        }
        if (amountOrDescription.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Jumlah donasi atau deskripsi barang tidak boleh kosong.");
            return;
        }

        String disasterType = selectedDisaster.split(" - ")[0];
        String location = selectedDisaster.split(" - ")[1];

        // Logika sesuai sequence diagram (alt: skenario donasi uang/barang)
        if ("Uang".equals(donationType)) {
            try {
                double amount = Double.parseDouble(amountOrDescription); // Pastikan input adalah angka
                if (amount <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Jumlah donasi uang harus lebih dari 0.");
                    return;
                }
                additionalInfo = paymentMethodField.getText().trim();
                if (additionalInfo.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Metode pembayaran tidak boleh kosong.");
                    return;
                }
                // Dalam skenario nyata, ini akan melibatkan integrasi gateway pembayaran.
                // Untuk demo, kita asumsikan pembayaran berhasil.
                showAlert(Alert.AlertType.INFORMATION, "Donasi Berhasil", "Donasi uang sebesar Rp" + String.format("%,.0f", amount) + " melalui " + additionalInfo + " berhasil dilakukan! Terima kasih atas donasi Anda.");
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Jumlah donasi uang harus berupa angka.");
                return;
            }
        } else if ("Barang".equals(donationType)) {
            additionalInfo = dropOffLocationArea.getText().trim();
            if (additionalInfo.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Lokasi drop-off barang tidak boleh kosong.");
                return;
            }
            // Dalam skenario nyata, ini akan melibatkan proses konfirmasi drop-off.
            showAlert(Alert.AlertType.INFORMATION, "Donasi Berhasil", "Donasi barang berupa '" + amountOrDescription + "' ke lokasi '" + additionalInfo + "' berhasil dicatat. Barang Anda akan segera diverifikasi!");
            // Sesuai diagram: "update_status_donasi(status)" dan "menampilkan notifikasi 'Barang sudah diterima'".
            // Untuk demo sederhana, kita asumsikan langsung diterima atau bisa ditambahkan field status di DonationData.
        }

        // Buat objek DonationData
        DonationData newDonation = new DonationData(
                disasterType,
                location,
                donationType,
                amountOrDescription,
                currentUserEmail // Gunakan email pengguna yang login
        );

        // Tambahkan donasi ke database melalui Dashboard
        Dashboard.addDonation(newDonation);

        // Tutup halaman donasi setelah sukses
        stage.close();
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

    // Metode main hanya untuk pengujian mandiri DonationPage
    public static void main(String[] args) {
        // Untuk testing, kita perlu dummy data laporan bencana di Dashboard
        // Pastikan Dashboard.initializeDummyData() dipanggil sebelum ini jika Anda mengujinya sendiri
        Dashboard.initializeDummyData();
        launch(args);
    }
}
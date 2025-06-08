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

        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Donasi untuk Korban Bencana");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #333;");

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10));
        formGrid.setAlignment(Pos.CENTER);

        // Pilih Laporan Bencana
        Label disasterLabel = new Label("Pilih Laporan Bencana:");
        disasterComboBox = new ComboBox<>();
        disasterComboBox.setPromptText("Pilih bencana yang ingin didonasikan");
        populateDisasterComboBox(); // Panggil method untuk mengisi ComboBox

        formGrid.add(disasterLabel, 0, 0);
        formGrid.add(disasterComboBox, 1, 0);

        // Jenis Donasi (Uang/Barang)
        Label donationTypeLabel = new Label("Jenis Donasi:");
        donationTypeComboBox = new ComboBox<>(FXCollections.observableArrayList("Uang", "Barang"));
        donationTypeComboBox.setPromptText("Pilih jenis donasi");

        formGrid.add(donationTypeLabel, 0, 1);
        formGrid.add(donationTypeComboBox, 1, 1);

        // Jumlah/Deskripsi
        Label amountOrDescriptionLabel = new Label("Jumlah/Deskripsi:");
        amountOrDescriptionField = new TextField();
        amountOrDescriptionField.setPromptText("Cth: 100000 (uang), Selimut (barang)");
        formGrid.add(amountOrDescriptionLabel, 0, 2);
        formGrid.add(amountOrDescriptionField, 1, 2);

        // BARU: Elemen UI dinamis berdasarkan jenis donasi
        paymentMethodLabel = new Label("Metode Pembayaran:");
        paymentMethodField = new TextField();
        paymentMethodField.setPromptText("Cth: Transfer Bank, E-wallet");

        dropOffLocationLabel = new Label("Lokasi Drop-off:");
        dropOffLocationArea = new TextArea();
        dropOffLocationArea.setPromptText("Alamat lengkap atau koordinat lokasi drop-off");
        dropOffLocationArea.setPrefRowCount(3);
        dropOffLocationArea.setWrapText(true);

        // Listener untuk Jenis Donasi
        donationTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            formGrid.getChildren().removeAll(paymentMethodLabel, paymentMethodField, dropOffLocationLabel, dropOffLocationArea); // Hapus elemen sebelumnya

            if ("Uang".equals(newVal)) {
                amountOrDescriptionLabel.setText("Jumlah Donasi (Rp):");
                amountOrDescriptionField.setPromptText("Cth: 100000");
                formGrid.add(paymentMethodLabel, 0, 3);
                formGrid.add(paymentMethodField, 1, 3);
            } else if ("Barang".equals(newVal)) {
                amountOrDescriptionLabel.setText("Deskripsi Barang:");
                amountOrDescriptionField.setPromptText("Cth: Selimut, Pakaian layak pakai");
                formGrid.add(dropOffLocationLabel, 0, 3);
                formGrid.add(dropOffLocationArea, 1, 3);
            } else {
                amountOrDescriptionLabel.setText("Jumlah/Deskripsi:");
                amountOrDescriptionField.setPromptText("Cth: 100000 (uang), Selimut (barang)");
            }
        });


        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button submitButton = new Button("Kirim Donasi");
        submitButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5;");
        submitButton.setOnAction(e -> processDonation(primaryStage)); // Panggil method baru

        Button backButton = new Button("Kembali");
        backButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5;");
        backButton.setOnAction(e -> primaryStage.close());

        buttonBox.getChildren().addAll(submitButton, backButton);

        root.getChildren().addAll(titleLabel, formGrid, buttonBox);

        Scene scene = new Scene(root, 500, 500); // Sesuaikan ukuran
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
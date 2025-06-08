import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class DonationPage extends Application {

    private String currentUserEmail; // Email user yang login

    // Konstruktor default, akan digunakan jika dipanggil tanpa email
    public DonationPage() {
        this.currentUserEmail = "anonymous@example.com"; // Default email jika tidak ada yang diteruskan
    }

    // Konstruktor untuk menerima email dari Dashboard
    public DonationPage(String userEmail) {
        this.currentUserEmail = userEmail;
    }

    @Override
    public void start(Stage primaryStage) {
        // Jika start dipanggil langsung tanpa konstruktor, gunakan default email
        start(primaryStage, this.currentUserEmail);
    }

    public void start(Stage primaryStage, String userEmail) {
        this.currentUserEmail = userEmail != null ? userEmail : "anonymous@example.com";

        primaryStage.setTitle("Halaman Donasi");

        VBox root = new VBox(15);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Donasi untuk Korban Bencana");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // --- Pilihan Laporan Bencana ---
        Label chooseDisasterLabel = new Label("Pilih Laporan Bencana:");
        chooseDisasterLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ComboBox<LaporanBencanaData> disasterComboBox = new ComboBox<>();
        disasterComboBox.setPrefWidth(300);
        // Mendapatkan data laporan bencana dari Dashboard
        ObservableList<LaporanBencanaData> laporanBencanaList = FXCollections.observableArrayList(Dashboard.getAllLaporanBencana());
        disasterComboBox.setItems(laporanBencanaList);
        disasterComboBox.setPromptText("Pilih bencana yang ingin didonasikan");
        // Custom cell factory untuk menampilkan laporan bencana dengan lebih baik
        disasterComboBox.setCellFactory(lv -> new ListCell<LaporanBencanaData>() {
            @Override
            protected void updateItem(LaporanBencanaData item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getJenisBencana() + " di " + item.getLokasi());
            }
        });
        disasterComboBox.setButtonCell(new ListCell<LaporanBencanaData>() {
            @Override
            protected void updateItem(LaporanBencanaData item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "Pilih bencana yang ingin didonasikan" : item.getJenisBencana() + " di " + item.getLokasi());
            }
        });


        // --- Detail Donasi ---
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10, 0, 10, 0));
        formGrid.setAlignment(Pos.CENTER);

        Label donationTypeLabel = new Label("Jenis Donasi:");
        ComboBox<String> donationTypeComboBox = new ComboBox<>();
        donationTypeComboBox.setItems(FXCollections.observableArrayList("Uang", "Barang", "Tenaga Sukarela"));
        donationTypeComboBox.setPromptText("Pilih jenis donasi");
        donationTypeComboBox.setPrefWidth(200);

        Label amountLabel = new Label("Jumlah/Deskripsi:");
        TextField amountField = new TextField();
        amountField.setPromptText("Cth: 100000 (uang), Selimut (barang), Tim medis (tenaga)");
        amountField.setPrefWidth(200);

        formGrid.add(donationTypeLabel, 0, 0);
        formGrid.add(donationTypeComboBox, 1, 0);
        formGrid.add(amountLabel, 0, 1);
        formGrid.add(amountField, 1, 1);

        // --- Tombol Aksi ---
        Button donateButton = new Button("Kirim Donasi");
        donateButton.setPrefSize(150, 40);
        donateButton.setStyle("-fx-font-size: 16px; -fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 5;");

        Button backButton = new Button("Kembali");
        backButton.setPrefSize(100, 40);
        backButton.setStyle("-fx-font-size: 16px; -fx-background-color: #6c757d; -fx-text-fill: white; -fx-background-radius: 5;");
        backButton.setOnAction(e -> primaryStage.close());

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(donateButton, backButton);

        // --- Aksi Donasi ---
        donateButton.setOnAction(e -> {
            LaporanBencanaData selectedDisaster = disasterComboBox.getSelectionModel().getSelectedItem();
            String donationType = donationTypeComboBox.getSelectionModel().getSelectedItem();
            String amountOrDesc = amountField.getText();

            if (selectedDisaster == null) {
                showAlert(Alert.AlertType.ERROR, "Kesalahan Donasi", "Mohon pilih laporan bencana terlebih dahulu.");
            } else if (donationType == null || donationType.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Kesalahan Donasi", "Mohon pilih jenis donasi.");
            } else if (amountOrDesc.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Kesalahan Donasi", "Mohon masukkan jumlah atau deskripsi donasi.");
            } else {
                // Buat objek DonationData baru
                DonationData newDonation = new DonationData(
                    selectedDisaster.getJenisBencana(),
                    selectedDisaster.getLokasi(),
                    donationType,
                    amountOrDesc,
                    currentUserEmail
                );

                // Tambahkan donasi ke database global di Dashboard
                Dashboard.addDonation(newDonation);

                showAlert(Alert.AlertType.INFORMATION, "Donasi Berhasil",
                          "Terima kasih atas donasi Anda!\n\n" +
                          "Bencana: " + selectedDisaster.getJenisBencana() + " di " + selectedDisaster.getLokasi() + "\n" +
                          "Jenis Donasi: " + donationType + "\n" +
                          "Jumlah/Deskripsi: " + amountOrDesc + "\n" +
                          "Donor: " + currentUserEmail);

                // Opsional: Reset form setelah donasi berhasil
                disasterComboBox.getSelectionModel().clearSelection();
                donationTypeComboBox.getSelectionModel().clearSelection();
                amountField.clear();
            }
        });

        root.getChildren().addAll(titleLabel, chooseDisasterLabel, disasterComboBox, formGrid, buttonBox);

        Scene scene = new Scene(root, 600, 600); // Ukuran scene disesuaikan
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Metode main untuk testing terpisah, tapi biasanya akan dipanggil dari Dashboard
    public static void main(String[] args) {
        launch(args);
    }
}
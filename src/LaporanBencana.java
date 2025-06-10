// File: src/LaporanBencana.java

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LaporanBencana extends Application {

    // --- MODIFIKASI DIMULAI ---
    // Tambahkan field untuk mengambil nilai dari form
    private ComboBox<String> jenisCombo;
    private TextField lokasiField;
    private TextArea deskripsiArea;
    private ComboBox<String> tingkatCombo;
    private TextField korbanField;
    // --- MODIFIKASI BERAKHIR ---

    @Override
    public void start(Stage primaryStage) {
        Text title = new Text("Laporan Bencana");
        title.setFont(Font.font("Arial", 20));
        title.setStyle("-fx-font-weight: bold;");

        Label jenisLabel = new Label("Jenis Bencana:");
        jenisLabel.setStyle("-fx-font-weight: bold;");
        jenisCombo = new ComboBox<>(); // Inisialisasi field
        jenisCombo.getItems().addAll("Banjir", "Gempa Bumi", "Kebakaran", "Longsor", "Tsunami", "Angin Topan");
        jenisCombo.setPrefWidth(300);

        Label lokasiLabel = new Label("Lokasi Bencana:");
        lokasiLabel.setStyle("-fx-font-weight: bold;");
        lokasiField = new TextField(); // Inisialisasi field
        lokasiField.setPromptText("Masukkan lokasi bencana");
        lokasiField.setPrefWidth(300);

        Label deskripsiLabel = new Label("Deskripsi Bencana:");
        deskripsiLabel.setStyle("-fx-font-weight: bold;");
        deskripsiArea = new TextArea(); // Inisialisasi field
        deskripsiArea.setPromptText("Jelaskan detail bencana yang terjadi");
        deskripsiArea.setPrefRowCount(4);
        deskripsiArea.setPrefWidth(300);
        deskripsiArea.setWrapText(true);

        Label tingkatLabel = new Label("Tingkat Keparahan:");
        tingkatLabel.setStyle("-fx-font-weight: bold;");
        tingkatCombo = new ComboBox<>(); // Inisialisasi field
        tingkatCombo.getItems().addAll("Ringan", "Sedang", "Berat");
        tingkatCombo.setPrefWidth(300);

        Label korbanLabel = new Label("Jumlah Korban (Jiwa):");
        korbanLabel.setStyle("-fx-font-weight: bold;");
        korbanField = new TextField(); // Inisialisasi field
        korbanField.setPromptText("Masukkan jumlah korban (angka)");
        korbanField.setPrefWidth(300);

        Button batalButton = new Button("Batal");
        batalButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        batalButton.setOnAction(e -> primaryStage.close()); // Tutup form

        Button laporButton = new Button("Lapor");
        laporButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        laporButton.setOnAction(e -> {
            // --- MODIFIKASI DIMULAI ---
            // Buat objek LaporanBencanaData baru dan simpan ke Dashboard
            String jenis = jenisCombo.getValue();
            String lokasi = lokasiField.getText();
            String deskripsi = deskripsiArea.getText();
            String tingkat = tingkatCombo.getValue();
            String jumlahKorban = korbanField.getText();

            if (jenis == null || jenis.isEmpty() || lokasi.isEmpty() || deskripsi.isEmpty() || tingkat == null || tingkat.isEmpty() || jumlahKorban.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Harap isi semua field laporan bencana!");
                return;
            }

            LaporanBencanaData newLaporan = new LaporanBencanaData(jenis, lokasi, deskripsi, tingkat, jumlahKorban);
            Dashboard.addLaporanBencana(newLaporan); // Simpan laporan ke database di Dashboard

            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Laporan Bencana berhasil dikirim!\n" +
                               "Jenis: " + jenis + "\n" +
                               "Lokasi: " + lokasi + "\n" +
                               "Tingkat: " + tingkat);
            primaryStage.close(); // Tutup form setelah berhasil melaporkan
            // --- MODIFIKASI BERAKHIR ---
        });

        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(batalButton, laporButton);

        mainLayout.getChildren().addAll(
            title,
            jenisLabel, jenisCombo,
            lokasiLabel, lokasiField,
            deskripsiLabel, deskripsiArea,
            tingkatLabel, tingkatCombo,
            korbanLabel, korbanField,
            buttonLayout
        );

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(500, 600);

        Scene scene = new Scene(scrollPane, 500, 600);
        primaryStage.setTitle("Laporan Bencana");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- MODIFIKASI DIMULAI ---
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // --- MODIFIKASI BERAKHIR ---

    public static void main(String[] args) {
        launch(args);
    }
}
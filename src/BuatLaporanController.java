import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent; // Import MouseEvent
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node; // Import Node untuk mendapatkan Stage dari event source
import javafx.scene.media.Media; // Untuk pemutar video
import javafx.scene.media.MediaPlayer; // Untuk pemutar video
import javafx.scene.media.MediaView; // Untuk pemutar video
import javafx.scene.Scene;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors; // Untuk mengumpulkan path file

public class BuatLaporanController implements Initializable {

    @FXML private ComboBox<String> provinsiCombo;
    @FXML private ComboBox<String> kotaCombo;
    @FXML private ComboBox<String> kecamatanCombo;
    @FXML private TextArea lokasiDetailArea;
    @FXML private ComboBox<String> jenisBencanaCombo;
    @FXML private TextField korbanHilangField;
    @FXML private TextField korbanLukaField;
    @FXML private TextArea deskripsiArea;
    @FXML private FlowPane fotoContainer;
    @FXML private FlowPane videoContainer;
    // Hapus deklarasi @FXML Button ini karena tidak ada di FXML
    // @FXML private Button tambahFotoButton;
    // @FXML private Button tambahVideoButton;
    @FXML private Button kirimLaporanButton;
    @FXML private Button backButton;

    private List<File> selectedPhotos = new ArrayList<>();
    private List<File> selectedVideos = new ArrayList<>();

    // Pertahankan referensi ke placeholder agar bisa dimanipulasi
    private VBox photoPlaceholder;
    private VBox videoPlaceholder;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupComboBoxes();
        setupMediaContainers();
        // Pastikan tidak ada tombol di FXML yang memiliki onAction ke handleTambahFoto/handleTambahVideo jika tidak ada @FXML Button
    }

    private void setupComboBoxes() {
        provinsiCombo.getItems().addAll(
            "DKI Jakarta", "Jawa Barat", "Jawa Tengah", "Jawa Timur",
            "Sumatera Barat", "Sumatera Utara", "Bali", "Nusa Tenggara Barat"
        );

        kotaCombo.getItems().addAll(
            "Jakarta Pusat", "Jakarta Selatan", "Jakarta Utara", "Jakarta Barat", "Jakarta Timur",
            "Bandung", "Bekasi", "Depok", "Bogor", "Tangerang"
        );

        kecamatanCombo.getItems().addAll(
            "Menteng", "Kebayoran Baru", "Kelapa Gading", "Cengkareng", "Matraman",
            "Coblong", "Cicendo", "Sukajadi", "Dago", "Antapani"
        );

        jenisBencanaCombo.getItems().addAll(
            "Gempa Bumi", "Banjir", "Longsor", "Kebakaran", "Tsunami",
            "Angin Topan", "Kekeringan", "Erupsi Gunung Api"
        );
    }

    private void setupMediaContainers() {
        // Buat placeholder dan simpan referensinya
        photoPlaceholder = createMediaPlaceholder("📷", "Tambah Foto");
        // Gunakan setOnMouseClicked untuk VBox placeholder
        photoPlaceholder.setOnMouseClicked(this::handlePlaceholderClick);
        fotoContainer.getChildren().add(photoPlaceholder);

        videoPlaceholder = createMediaPlaceholder("🎥", "Tambah Video");
        // Gunakan setOnMouseClicked untuk VBox placeholder
        videoPlaceholder.setOnMouseClicked(this::handlePlaceholderClick); // Gunakan metode yang sama atau berbeda
        videoContainer.getChildren().add(videoPlaceholder);
    }

    private VBox createMediaPlaceholder(String icon, String text) {
        VBox placeholder = new VBox(5);
        placeholder.setAlignment(javafx.geometry.Pos.CENTER);
        placeholder.setPrefSize(120, 80);
        placeholder.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-style: dashed; -fx-border-width: 2; -fx-background-radius: 8; -fx-border-radius: 8; -fx-cursor: hand;");

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 24px;");

        Label textLabel = new Label(text);
        textLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #6c757d;");

        placeholder.getChildren().addAll(iconLabel, textLabel);
        return placeholder;
    }

    // Metode umum untuk menangani klik pada placeholder
    private void handlePlaceholderClick(MouseEvent event) {
        Node source = (Node) event.getSource(); // Dapatkan node yang diklik (placeholder VBox)

        if (source == photoPlaceholder) {
            handleTambahFotoAction(source); // Panggil metode untuk memilih foto
        } else if (source == videoPlaceholder) {
            handleTambahVideoAction(source); // Panggil metode untuk memilih video
        }
    }

    // Metode untuk membuka FileChooser untuk foto
    private void handleTambahFotoAction(Node sourceNode) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Foto Bencana");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) sourceNode.getScene().getWindow(); // Dapatkan Stage dari node yang diklik
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            selectedPhotos.add(selectedFile);
            addPhotoToContainer(selectedFile);
        }
    }

    // Metode untuk membuka FileChooser untuk video
    private void handleTambahVideoAction(Node sourceNode) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Video Bencana");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mov", "*.mkv")
        );

        Stage stage = (Stage) sourceNode.getScene().getWindow(); // Dapatkan Stage dari node yang diklik
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            selectedVideos.add(selectedFile);
            addVideoToContainer(selectedFile);
        }
    }

    private void addPhotoToContainer(File photoFile) {
        try {
            ImageView imageView = new ImageView();
            imageView.setFitWidth(120);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(false);
            imageView.setStyle("-fx-background-radius: 8;");

            Image image = new Image(photoFile.toURI().toString());
            imageView.setImage(image);

            // Hapus placeholder dari FlowPane
            if (fotoContainer.getChildren().contains(photoPlaceholder)) {
                fotoContainer.getChildren().remove(photoPlaceholder);
            }

            // Tambahkan gambar
            fotoContainer.getChildren().add(imageView);

            // Tambahkan kembali placeholder di akhir
            fotoContainer.getChildren().add(photoPlaceholder);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat foto: " + e.getMessage());
            e.printStackTrace(); // Penting untuk debugging
        }
    }

    private void addVideoToContainer(File videoFile) {
        VBox videoBox = new VBox(5);
        videoBox.setAlignment(javafx.geometry.Pos.CENTER);
        videoBox.setPrefSize(120, 80);
        videoBox.setStyle("-fx-background-color: #343a40; -fx-background-radius: 8;");

        Label playIcon = new Label("▶");
        playIcon.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        Label fileName = new Label(videoFile.getName());
        fileName.setStyle("-fx-font-size: 10px; -fx-text-fill: white; -fx-text-alignment: center;");
        fileName.setWrapText(true);

        videoBox.getChildren().addAll(playIcon, fileName);

        // Tambahkan fungsionalitas untuk memutar video saat diklik
        videoBox.setOnMouseClicked(event -> {
            try {
                Media media = new Media(videoFile.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(mediaPlayer);

                Stage videoStage = new Stage();
                videoStage.setTitle("Play Video: " + videoFile.getName());
                VBox root = new VBox(mediaView);
                root.setAlignment(javafx.geometry.Pos.CENTER);
                Scene scene = new Scene(root, 600, 400); // Sesuaikan ukuran
                videoStage.setScene(scene);
                videoStage.show();

                mediaPlayer.play();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal memutar video: " + e.getMessage());
                e.printStackTrace(); // Penting untuk debugging
            }
        });

        // Hapus placeholder dari FlowPane
        if (videoContainer.getChildren().contains(videoPlaceholder)) {
            videoContainer.getChildren().remove(videoPlaceholder);
        }

        // Tambahkan video
        videoContainer.getChildren().add(videoBox);

        // Tambahkan kembali placeholder di akhir
        videoContainer.getChildren().add(videoPlaceholder);
    }

    @FXML
    private void handleKirimLaporan(ActionEvent event) {
        if (validateForm()) {
            String jenis = jenisBencanaCombo.getValue();
            String lokasi = provinsiCombo.getValue() + ", " + kotaCombo.getValue() + ", " + kecamatanCombo.getValue() + ", " + lokasiDetailArea.getText();
            String deskripsi = deskripsiArea.getText();
            String tingkat = "Sedang"; // Default or add tingkat selection

            // Validasi dan konversi korban hilang/luka ke int
            int korbanHilang = 0;
            try {
                if (!korbanHilangField.getText().isEmpty()) {
                    korbanHilang = Integer.parseInt(korbanHilangField.getText());
                }
            } catch (NumberFormatException e) {
                // Should be caught by validateForm, but as a fallback
                showAlert(Alert.AlertType.ERROR, "Input Error", "Jumlah korban hilang harus angka.");
                return;
            }

            int korbanLuka = 0;
            try {
                if (!korbanLukaField.getText().isEmpty()) {
                    korbanLuka = Integer.parseInt(korbanLukaField.getText());
                }
            } catch (NumberFormatException e) {
                // Should be caught by validateForm, but as a fallback
                showAlert(Alert.AlertType.ERROR, "Input Error", "Jumlah korban luka harus angka.");
                return;
            }

            // Kumpulkan path foto dan video
            List<String> photoPaths = selectedPhotos.stream()
                                                    .map(File::getAbsolutePath)
                                                    .collect(Collectors.toList());
            List<String> videoPaths = selectedVideos.stream()
                                                    .map(File::getAbsolutePath)
                                                    .collect(Collectors.toList());

            // Anda perlu memodifikasi LaporanBencanaData untuk menerima List<String> pathFoto dan List<String> pathVideo
            // Contoh constructor yang diperbarui:
            // LaporanBencanaData(String jenis, String lokasi, String deskripsi, String tingkat, int korbanHilang, int korbanLuka, List<String> photoPaths, List<String> videoPaths)
            // Atau Anda bisa menambahkan setter di LaporanBencanaData
            // LaporanBencanaData newLaporan = new LaporanBencanaData(jenis, lokasi, deskripsi, tingkat, String.valueOf(korbanLuka)); // Korban hanya luka
            // newLaporan.setKorbanHilang(korbanHilang); // Jika ada setter
            // newLaporan.setPhotoPaths(photoPaths); // Jika ada setter
            // newLaporan.setVideoPaths(videoPaths); // Jika ada setter

            // Untuk sementara, saya akan tetap menggunakan constructor lama karena LaporanBencanaData Anda belum dimodifikasi
            // Anda perlu menyesuaikan ini setelah LaporanBencanaData diperbarui
            String korbanGabungan = "Luka: " + korbanLuka + ", Hilang: " + korbanHilang;
            LaporanBencanaData newLaporan = new LaporanBencanaData(jenis, lokasi, deskripsi, tingkat, korbanGabungan);

            Dashboard.addLaporanBencana(newLaporan);

            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Laporan bencana berhasil dikirim!");

            try {
                // Asumsi Main.showLaporanList() mengembalikan ke daftar laporan
                Main.showLaporanList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateForm() {
        if (provinsiCombo.getValue() == null || provinsiCombo.getValue().isEmpty() ||
            kotaCombo.getValue() == null || kotaCombo.getValue().isEmpty() ||
            kecamatanCombo.getValue() == null || kecamatanCombo.getValue().isEmpty() || // Tambahkan validasi untuk kecamatan
            lokasiDetailArea.getText().trim().isEmpty() ||
            jenisBencanaCombo.getValue() == null || jenisBencanaCombo.getValue().isEmpty() ||
            deskripsiArea.getText().trim().isEmpty()) {

            showAlert(Alert.AlertType.WARNING, "Validasi", "Harap isi semua field yang wajib (Provinsi, Kota, Kecamatan, Detail Lokasi, Jenis Bencana, Deskripsi)!");
            return false;
        }

        // Validasi untuk korban hilang dan luka
        try {
            int korbanHilang = 0;
            if (!korbanHilangField.getText().isEmpty()) {
                korbanHilang = Integer.parseInt(korbanHilangField.getText());
            }
            int korbanLuka = 0;
            if (!korbanLukaField.getText().isEmpty()) {
                korbanLuka = Integer.parseInt(korbanLukaField.getText());
            }

            if (korbanHilang < 0 || korbanLuka < 0) {
                showAlert(Alert.AlertType.WARNING, "Validasi", "Jumlah korban tidak bisa negatif.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validasi", "Jumlah korban harus berupa angka.");
            return false;
        }

        // Jika Anda ingin media wajib, aktifkan validasi ini
        // if (selectedPhotos.isEmpty()) {
        //     showAlert(Alert.AlertType.WARNING, "Validasi", "Harap tambahkan setidaknya satu foto bencana.");
        //     return false;
        // }

        return true;
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Main.showLaporanList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
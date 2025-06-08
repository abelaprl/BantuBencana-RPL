import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    @FXML private Button tambahFotoButton;
    @FXML private Button tambahVideoButton;
    @FXML private Button kirimLaporanButton;
    @FXML private Button backButton;

    private List<File> selectedPhotos = new ArrayList<>();
    private List<File> selectedVideos = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupComboBoxes();
        setupMediaContainers();
    }

    private void setupComboBoxes() {
        // Setup Provinsi
        provinsiCombo.getItems().addAll(
            "DKI Jakarta", "Jawa Barat", "Jawa Tengah", "Jawa Timur", 
            "Sumatera Barat", "Sumatera Utara", "Bali", "Nusa Tenggara Barat"
        );

        // Setup Kota (will be populated based on provinsi selection)
        kotaCombo.getItems().addAll(
            "Jakarta Pusat", "Jakarta Selatan", "Jakarta Utara", "Jakarta Barat", "Jakarta Timur",
            "Bandung", "Bekasi", "Depok", "Bogor", "Tangerang"
        );

        // Setup Kecamatan
        kecamatanCombo.getItems().addAll(
            "Menteng", "Kebayoran Baru", "Kelapa Gading", "Cengkareng", "Matraman",
            "Coblong", "Cicendo", "Sukajadi", "Dago", "Antapani"
        );

        // Setup Jenis Bencana
        jenisBencanaCombo.getItems().addAll(
            "Gempa Bumi", "Banjir", "Longsor", "Kebakaran", "Tsunami", 
            "Angin Topan", "Kekeringan", "Erupsi Gunung Api"
        );
    }

    private void setupMediaContainers() {
        // Add placeholder for photos
        addPhotoPlaceholder();
        addVideoPlaceholder();
    }

    private void addPhotoPlaceholder() {
        VBox placeholder = createMediaPlaceholder("📷", "Tambah Foto");
        placeholder.setOnMouseClicked(e -> handleTambahFoto(null));
        fotoContainer.getChildren().add(placeholder);
    }

    private void addVideoPlaceholder() {
        VBox placeholder = createMediaPlaceholder("🎥", "Tambah Video");
        placeholder.setOnMouseClicked(e -> handleTambahVideo(null));
        videoContainer.getChildren().add(placeholder);
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

    @FXML
    private void handleTambahFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Foto Bencana");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) tambahFotoButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            selectedPhotos.add(selectedFile);
            addPhotoToContainer(selectedFile);
        }
    }

    @FXML
    private void handleTambahVideo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Video Bencana");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mov", "*.mkv")
        );

        Stage stage = (Stage) tambahVideoButton.getScene().getWindow();
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

            // Remove placeholder if this is the first photo
            if (selectedPhotos.size() == 1) {
                fotoContainer.getChildren().clear();
            }

            fotoContainer.getChildren().add(imageView);
            
            // Add new placeholder
            addPhotoPlaceholder();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat foto: " + e.getMessage());
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

        // Remove placeholder if this is the first video
        if (selectedVideos.size() == 1) {
            videoContainer.getChildren().clear();
        }

        videoContainer.getChildren().add(videoBox);
        
        // Add new placeholder
        addVideoPlaceholder();
    }

    @FXML
    private void handleKirimLaporan(ActionEvent event) {
        if (validateForm()) {
            // Create new laporan
            String jenis = jenisBencanaCombo.getValue();
            String lokasi = provinsiCombo.getValue() + ", " + kotaCombo.getValue();
            String deskripsi = deskripsiArea.getText();
            String tingkat = "Sedang"; // Default or add tingkat selection
            String korban = korbanLukaField.getText();

            LaporanBencanaData newLaporan = new LaporanBencanaData(jenis, lokasi, deskripsi, tingkat, korban);
            Dashboard.addLaporanBencana(newLaporan);

            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Laporan bencana berhasil dikirim!");
            
            try {
                Main.showLaporanList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateForm() {
        if (provinsiCombo.getValue() == null || kotaCombo.getValue() == null || 
            jenisBencanaCombo.getValue() == null || deskripsiArea.getText().trim().isEmpty()) {
            
            showAlert(Alert.AlertType.WARNING, "Validasi", "Harap isi semua field yang wajib!");
            return false;
        }
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

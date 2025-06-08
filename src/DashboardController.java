// src/DashboardController.java
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label userEmailLabel;
    @FXML
    private VBox bencanaListContainer;

    private BencanaRepository bencanaRepository = new BencanaRepository();
    private String loggedInUserEmail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic, if any
    }

    public void initData(String email) {
        this.loggedInUserEmail = email;
        userEmailLabel.setText(email);
        System.out.println("DEBUG: User " + email + " logged in. Loading dashboard data.");
        loadBencanaData();
    }

    private void loadBencanaData() {
        System.out.println("DEBUG: Starting to load bencana data.");
        List<Bencana> bencanas = bencanaRepository.findAll();
        bencanaListContainer.getChildren().clear();

        if (bencanas.isEmpty()) {
            System.out.println("DEBUG: No bencana data found in repository.");
            Label noDataLabel = new Label("Tidak ada data bencana yang tersedia.");
            noDataLabel.setTextFill(Color.GRAY);
            noDataLabel.setFont(new Font(16));
            bencanaListContainer.getChildren().add(noDataLabel);
        } else {
            System.out.println("DEBUG: Found " + bencanas.size() + " bencana entries.");
            for (Bencana bencana : bencanas) {
                bencanaListContainer.getChildren().add(createBencanaCard(bencana));
            }
        }
    }

    private VBox createBencanaCard(Bencana bencana) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        card.setMaxWidth(600);

        ImageView imageView = new ImageView();

        // --- DEBUGGING EKSTRA ---
        System.out.println("\n--- Memproses Bencana: " + bencana.getJudul() + " ---");
        String namaFileGambarDariObjek = bencana.getNamaFileGambar();
        System.out.println("DEBUG: Nama file gambar dari objek Bencana: '" + namaFileGambarDariObjek + "'");

        // Construct the full relative path
        String relativePathForImage = "/../img/bencana_photos/" + namaFileGambarDariObjek;
        System.out.println("DEBUG: Path relatif yang dicoba untuk gambar: '" + relativePathForImage + "'");

        URL imageUrl = Main.class.getResource(relativePathForImage);

        if (imageUrl != null) {
            System.out.println("DEBUG: Gambar DITEMUKAN di URL: " + imageUrl.toExternalForm());
            try {
                Image image = new Image(imageUrl.toExternalForm());
                if (image.isError()) {
                    System.err.println("ERROR: Gambar gagal dimuat setelah ditemukan URL. Pesan error: " + image.exceptionProperty().get().getMessage());
                    Label errorLabel = new Label("Gambar rusak atau tidak dapat dimuat: " + namaFileGambarDariObjek);
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setFont(new Font(12));
                    imageView.setFitWidth(570);
                    imageView.setFitHeight(100);
                    card.getChildren().add(errorLabel);
                } else {
                    imageView.setImage(image);
                    imageView.setFitWidth(570);
                    imageView.setFitHeight(200);
                    imageView.setPreserveRatio(false);
                }
            } catch (Exception e) {
                System.err.println("ERROR: Pengecualian saat membuat objek Image: " + e.getMessage());
                Label errorLabel = new Label("Error memuat gambar: " + namaFileGambarDariObjek);
                errorLabel.setTextFill(Color.RED);
                errorLabel.setFont(new Font(12));
                imageView.setFitWidth(570);
                imageView.setFitHeight(100);
                card.getChildren().add(errorLabel);
            }
        } else {
            System.err.println("ERROR: Gambar TIDAK DITEMUKAN di path: '" + relativePathForImage + "'");
            System.err.println("DEBUG: Current working directory (saat java dijalankan) kemungkinan di 'D:\\TUGAS SEM 5\\BantuBencana-RPL\\src'");
            System.err.println("DEBUG: Pastikan file gambar ada di 'D:\\TUGAS SEM 5\\BantuBencana-RPL\\img\\bencana_photos\\" + namaFileGambarDariObjek + "'");
            Label errorLabel = new Label("Gambar tidak ditemukan: " + namaFileGambarDariObjek);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setFont(new Font(12));
            imageView.setFitWidth(570);
            imageView.setFitHeight(100);
            card.getChildren().add(errorLabel);
        }

        Label judulLabel = new Label(bencana.getJudul());
        judulLabel.setFont(new Font("System Bold", 18));
        judulLabel.setWrapText(true);

        Label lokasiLabel = new Label("Lokasi: " + bencana.getLokasi());
        lokasiLabel.setFont(new Font(14));

        Label tanggalLabel = new Label("Tanggal: " + bencana.getTanggal());
        tanggalLabel.setFont(new Font(14));

        Label deskripsiLabel = new Label(bencana.getDeskripsiSingkat());
        deskripsiLabel.setFont(new Font(13));
        deskripsiLabel.setWrapText(true);

        HBox buttonBox = new HBox(10);
        Button detailButton = new Button("Lihat Detail");
        Button donasiButton = new Button("Donasi");
        buttonBox.getChildren().addAll(detailButton, donasiButton);

        card.getChildren().addAll(imageView, judulLabel, lokasiLabel, tanggalLabel, deskripsiLabel, buttonBox);

        return card;
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            System.out.println("DEBUG: Logging out and returning to LoginView.");
            Main.showLoginView();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: Gagal kembali ke halaman login.");
        }
    }
}
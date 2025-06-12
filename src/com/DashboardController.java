package com;
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
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;


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
        if (userEmailLabel != null) {
            userEmailLabel.setText(email);
        }
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

        System.out.println("\n--- Memproses Bencana: " + bencana.getJudul() + " ---");
        String namaFileGambarDariObjek = bencana.getNamaFileGambar();
        System.out.println("DEBUG: Nama file gambar dari objek Bencana: '" + namaFileGambarDariObjek + "'");

        String relativePathForImage = "img/bencana_photos/" + namaFileGambarDariObjek;
        // String relativePathForImage = namaFileGambarDariObjek;
        System.out.println("DEBUG: Path relatif yang dicoba untuk gambar: '" + relativePathForImage + "'");

        File file = new File("img/bencana_photos/" + namaFileGambarDariObjek);
        if (file.exists()) {
            System.out.println("DEBUG: Load gambar dari file system langsung.");
            Image image = new Image(file.toURI().toString());
            if (image.isError()) {
                System.err.println("ERROR: Gambar gagal dimuat: " + image.getException());
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
        } else {
            System.err.println("ERROR: Gambar tidak ditemukan di lokasi: " + file.getAbsolutePath());
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
        detailButton.setOnAction(e -> {
            try {
                Main.showDetailLaporan(bencana); // Memanggil Main.showDetailLaporan dengan objek Bencana
                System.out.println("DEBUG: Membuka detail laporan untuk: " + bencana.getJudul());
            } catch (IOException ex) { // Tangkap IOException
                ex.printStackTrace();
                System.err.println("ERROR: Gagal membuka detail laporan.");
            }
        });
        Button donasiButton = new Button("Donasi");
        donasiButton.setOnAction(e -> {
            try {
                new DonationPage(loggedInUserEmail).start(new Stage());
                System.out.println("DEBUG: Membuka halaman Donasi untuk: " + bencana.getJudul());
            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("ERROR: Gagal membuka halaman donasi dari card.");
            }
        });
        buttonBox.getChildren().addAll(detailButton, donasiButton);

        card.getChildren().addAll(imageView, judulLabel, lokasiLabel, tanggalLabel, deskripsiLabel, buttonBox);

        return card;
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            System.out.println("DEBUG: Logging out and returning to LoginView.");
            // Menggunakan Main.showLoginView() yang sudah didefinisikan di kelas Main Anda
            Main.showLoginView();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: Gagal kembali ke halaman login.");
        }
    }

    @FXML
    private void handleLaporanBencana(ActionEvent event) {
        try {
            System.out.println("DEBUG: Navigating to Laporan Bencana form.");
            Main.showBuatLaporan(); // Menggunakan Main.showBuatLaporan()
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: Gagal membuka halaman laporan bencana.");
        }
    }

    @FXML
    private void handleFeedback(ActionEvent event) {
        try {
            System.out.println("DEBUG: Navigating to Feedback form.");
            new Feedback().start(new Stage(), loggedInUserEmail);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR: Gagal membuka halaman feedback.");
        }
    }

    @FXML
    private void handleDonasi(ActionEvent event) {
        try {
            System.out.println("DEBUG: Navigating to Donation page.");
            new DonationPage(loggedInUserEmail).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR: Gagal membuka halaman donasi.");
        }
    }
    
    @FXML
    private void handleLihatSemuaLaporan(ActionEvent event) {
        try {
            System.out.println("DEBUG: Navigating to Laporan List View.");
            Main.showLaporanList(); // Memanggil method dari Main.java
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: Gagal membuka halaman daftar laporan.");
        }
    }

}
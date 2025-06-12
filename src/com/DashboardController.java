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
        // Load current user from session
        if (UserSessionManager.isLoggedIn()) {
            User currentUser = UserSessionManager.getCurrentUser();
            if (currentUser != null && userEmailLabel != null) {
                userEmailLabel.setText("Selamat datang, " + currentUser.getName() + " (" + currentUser.getEmail() + ")");
                this.loggedInUserEmail = currentUser.getEmail();
            }
        }
        loadBencanaData();
    }

    public void initData(String email) {
        this.loggedInUserEmail = email;
        if (userEmailLabel != null) {
            User user = new UserRepository().findByEmail(email);
            if (user != null) {
                userEmailLabel.setText("Selamat datang, " + user.getName() + " (" + email + ")");
            } else {
                userEmailLabel.setText(email);
            }
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

        System.out.println("\n--- Memproses Bencana: " + bencana.getJudul() + " ---");

        // Tambahkan label informasi tanpa gambar
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
                Main.showDetailLaporan(bencana);
                System.out.println("DEBUG: Membuka detail laporan untuk: " + bencana.getJudul());
            } catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("ERROR: Gagal membuka detail laporan.");
            }
        });
        
        // Tombol donasi dihilangkan sesuai request
        buttonBox.getChildren().add(detailButton);

        card.getChildren().addAll(judulLabel, lokasiLabel, tanggalLabel, deskripsiLabel, buttonBox);

        return card;
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            System.out.println("DEBUG: Logging out user: " + loggedInUserEmail);
            
            // Clear user session
            UserSessionManager.clearSession();
            
            // Navigate back to login
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
            String currentEmail = UserSessionManager.getCurrentUserEmail();
            new Feedback().start(new Stage(), currentEmail != null ? currentEmail : loggedInUserEmail);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR: Gagal membuka halaman feedback.");
        }
    }

    @FXML
    private void handleDonasi(ActionEvent event) {
        try {
            System.out.println("DEBUG: Navigating to Donation page.");
            String currentEmail = UserSessionManager.getCurrentUserEmail();
            new DonationPage(currentEmail != null ? currentEmail : loggedInUserEmail).start(new Stage());
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

package com;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailLaporanController implements Initializable {

    @FXML private Label idLaporanLabel;
    @FXML private Label lokasiLabel;
    @FXML private Label alamatLabel;
    @FXML private TextArea jenisBencanaArea;
    @FXML private TextArea korbanHilangArea;
    @FXML private TextArea korbanLukaArea;
    @FXML private TextArea deskripsiArea;
    @FXML private FlowPane fotoContainer;
    @FXML private FlowPane videoContainer;
    @FXML private Label statusLabel;
    @FXML private Button backButton;

    private LaporanBencanaData currentLaporan;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
    }

    public void initData(LaporanBencanaData laporan) {
        this.currentLaporan = laporan;
        loadLaporanData();
    }

    private void loadLaporanData() {
        if (currentLaporan != null) {
            // Set basic info
            idLaporanLabel.setText("LBP-001"); // You can generate this based on index or add ID field
            lokasiLabel.setText(currentLaporan.getLokasi());
            alamatLabel.setText("Jl. Contoh No. 123"); // Add address field if needed
            
            // Set text areas
            jenisBencanaArea.setText(currentLaporan.getJenisBencana());
            korbanHilangArea.setText("2"); // Add these fields to LaporanBencanaData if needed
            korbanLukaArea.setText(currentLaporan.getJumlahKorban());
            deskripsiArea.setText(currentLaporan.getDeskripsi());
            
            // Set status
            statusLabel.setText("Terverifikasi");
            statusLabel.setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;");
            
            // Load media (placeholder images for now)
            loadMediaPlaceholders();
        }
    }

    private void loadMediaPlaceholders() {
        // Add placeholder images for photos
        try {
            for (int i = 0; i < 2; i++) {
                ImageView imageView = new ImageView();
                imageView.setFitWidth(120);
                imageView.setFitHeight(80);
                imageView.setPreserveRatio(false);
                imageView.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 8;");
                
                // Try to load actual disaster images
                String imagePath = "/BantuBencana-RPL/img/bencana_photos/" + 
                    (i == 0 ? "jakarta" : "lombok") + ".jpg";
                URL imageUrl = getClass().getResource(imagePath);
                if (imageUrl != null) {
                    Image image = new Image(imageUrl.toExternalForm());
                    imageView.setImage(image);
                }
                
                fotoContainer.getChildren().add(imageView);
            }
            
            // Add placeholder for video
            ImageView videoView = new ImageView();
            videoView.setFitWidth(120);
            videoView.setFitHeight(80);
            videoView.setPreserveRatio(false);
            videoView.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 8;");
            
            // Add play button overlay (you can create a proper video player later)
            VBox videoContainer = new VBox();
            videoContainer.getChildren().add(videoView);
            Label playLabel = new Label("▶");
            playLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 50%; -fx-padding: 8;");
            videoContainer.getChildren().add(playLabel);
            
            this.videoContainer.getChildren().add(videoContainer);
            
        } catch (Exception e) {
            System.err.println("Error loading media: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Main.showDashboardView();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error returning to dashboard.");
        }
    }
}

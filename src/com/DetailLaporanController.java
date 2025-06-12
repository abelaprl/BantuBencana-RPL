package com;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DetailLaporanController implements Initializable {

    @FXML private Label idLaporanLabel;
    @FXML private Label lokasiLabel;
    @FXML private Label alamatLabel;
    @FXML private TextArea jenisBencanaArea;
    @FXML private TextArea korbanHilangArea;
    @FXML private TextArea korbanLukaArea;
    @FXML private TextArea deskripsiArea;
    @FXML private Label createdByLabel; // New label to show who created the report
    @FXML private Label createdAtLabel; // New label to show when the report was created
    @FXML private Button backButton;

    private LaporanBencanaData currentLaporan;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("DEBUG: DetailLaporanController initialized");
    }

    public void initData(LaporanBencanaData laporan) {
        this.currentLaporan = laporan;
        System.out.println("DEBUG: Loading laporan data: " + laporan.getJenisBencana());
        loadLaporanData();
    }

    private void loadLaporanData() {
        if (currentLaporan != null) {
            // Set basic info
            idLaporanLabel.setText("LBP-" + String.format("%03d", currentLaporan.getTimestamp() % 1000));
            lokasiLabel.setText(currentLaporan.getLokasi());
            alamatLabel.setText("Jl. " + currentLaporan.getLokasi() + " No. 123");
            
            // Set text areas
            jenisBencanaArea.setText(currentLaporan.getJenisBencana());
            korbanHilangArea.setText("2"); // Default value
            korbanLukaArea.setText(currentLaporan.getJumlahKorban());
            deskripsiArea.setText(currentLaporan.getDeskripsi());
            
            // Set creator info if available
            if (createdByLabel != null) {
                String createdBy = currentLaporan.getCreatedBy();
                if (createdBy != null && !createdBy.equals("anonymous")) {
                    User creator = new UserRepository().findByEmail(createdBy);
                    if (creator != null) {
                        createdByLabel.setText("Dibuat oleh: " + creator.getName() + " (" + createdBy + ")");
                    } else {
                        createdByLabel.setText("Dibuat oleh: " + createdBy);
                    }
                } else {
                    createdByLabel.setText("Dibuat oleh: Anonim");
                }
            }
            
            // Set creation time if available
            if (createdAtLabel != null) {
                long timestamp = currentLaporan.getTimestamp();
                if (timestamp > 0) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String formattedDate = formatter.format(new Date(timestamp));
                    createdAtLabel.setText("Dibuat pada: " + formattedDate);
                } else {
                    createdAtLabel.setText("Dibuat pada: Tidak diketahui");
                }
            }
            
            System.out.println("DEBUG: Laporan data loaded successfully");
        } else {
            System.err.println("ERROR: currentLaporan is null");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            System.out.println("DEBUG: Returning to dashboard");
            Main.showDashboardView();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: Failed to return to dashboard");
        }
    }
}

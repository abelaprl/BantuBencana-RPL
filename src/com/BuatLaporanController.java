package com;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuatLaporanController {

    @FXML private TextField fieldJenis;
    @FXML private TextField fieldLokasi;
    @FXML private TextArea fieldDeskripsi;
    @FXML private ComboBox<String> comboTingkatKeparahan;
    @FXML private TextField fieldJumlahKorban;
    @FXML private Button btnSimpan;
    @FXML private Label labelStatus;

    @FXML
    private void initialize() {
        System.out.println("🚀 Inisialisasi BuatLaporanController...");
        
        // Setup combo box tingkat keparahan
        comboTingkatKeparahan.getItems().addAll("Ringan", "Sedang", "Berat", "Sangat Berat");
        comboTingkatKeparahan.setValue("Sedang");
        
        // Setup button actions
        btnSimpan.setOnAction(e -> simpanLaporan());
        
        System.out.println("✓ BuatLaporanController berhasil diinisialisasi");
    }

    @FXML
    private void simpanLaporan() {
        System.out.println("💾 Menyimpan laporan bencana...");
        
        // Validasi input
        if (!validateInput()) {
            return;
        }

        try {
            // Get current user email
            String currentUserEmail = UserSessionManager.isLoggedIn() ? 
                UserSessionManager.getCurrentUserEmail() : "anonymous";
            
            // Buat objek laporan bencana
            LaporanBencanaData laporan = new LaporanBencanaData(
                fieldJenis.getText().trim(),
                fieldLokasi.getText().trim(),
                fieldDeskripsi.getText().trim(),
                comboTingkatKeparahan.getValue(),
                fieldJumlahKorban.getText().trim(),
                currentUserEmail
            );

            // Simpan ke database
            Dashboard.addLaporanBencana(laporan);
            
            // Show success message
            labelStatus.setText("Laporan berhasil disimpan!");
            labelStatus.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            
            System.out.println("✓ Laporan bencana berhasil disimpan oleh user: " + currentUserEmail);
            
            // Reset form
            resetForm();

        } catch (Exception e) {
            System.err.println("✗ Error menyimpan laporan: " + e.getMessage());
            e.printStackTrace();
            
            labelStatus.setText("Error: " + e.getMessage());
            labelStatus.setStyle("-fx-text-fill: red;");
        }
    }

    private boolean validateInput() {
        if (fieldJenis.getText().trim().isEmpty()) {
            showError("Jenis bencana harus diisi!");
            return false;
        }
        
        if (fieldLokasi.getText().trim().isEmpty()) {
            showError("Lokasi harus diisi!");
            return false;
        }
        
        if (fieldDeskripsi.getText().trim().isEmpty()) {
            showError("Deskripsi harus diisi!");
            return false;
        }
        
        if (comboTingkatKeparahan.getValue() == null) {
            showError("Tingkat keparahan harus dipilih!");
            return false;
        }
        
        if (fieldJumlahKorban.getText().trim().isEmpty()) {
            showError("Jumlah korban harus diisi!");
            return false;
        }
        
        return true;
    }

    private void showError(String message) {
        labelStatus.setText(message);
        labelStatus.setStyle("-fx-text-fill: red;");
        System.err.println("⚠ Validasi error: " + message);
    }

    private void resetForm() {
        fieldJenis.clear();
        fieldLokasi.clear();
        fieldDeskripsi.clear();
        comboTingkatKeparahan.setValue("Sedang");
        fieldJumlahKorban.clear();
    }

    @FXML
    private void handleKembali() {
        try {
            Main.showDashboardView();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error kembali ke dashboard: " + e.getMessage());
        }
    }
}

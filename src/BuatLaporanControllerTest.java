// Pastikan Anda menempatkan ini di paket yang sesuai
// Misalnya, jika BuatLaporanController ada di root src, biarkan tanpa package statement.
// Jika BuatLaporanController ada di com.bantubencana.controller, maka tambahkan:
// package com.bantubencana.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Import kelas yang akan diuji
// Anda harus mengimpor kelas BuatLaporanController yang benar.
// Jika BuatLaporanController ada di root src, mungkin tidak perlu import.
// Jika ada di paket, ganti ini dengan paket yang sesuai, misal:
// import com.bantubencana.controller.BuatLaporanController;
// Karena kode Anda tidak menunjukkan statement package, saya asumsikan ia ada di root
// sehingga saya menggunakan kelas BuatLaporanController langsung.
// Anda mungkin perlu menambahkan baris ini jika BuatLaporanController Anda berada dalam paket:
// import your.package.name.BuatLaporanController;

import javafx.scene.control.ComboBox; // Perlu untuk menginisialisasi ComboBox
import javafx.scene.control.TextArea; // Perlu untuk menginisialisasi TextArea
import javafx.scene.control.TextField; // Perlu untuk menginisialisasi TextField

class BuatLaporanControllerTest {

    private BuatLaporanController controller;

    // Metode ini akan dijalankan sebelum setiap test method
    @BeforeEach
    void setUp() {
        controller = new BuatLaporanController();

        // --- Inisialisasi FXML elements secara manual untuk testing ---
        // Karena unit test tidak me-load FXML, kita harus membuat instance
        // dari ComboBox, TextArea, TextField dan "menghubungkannya" ke controller secara manual.
        // Ini adalah cara untuk "mock" atau mensimulasikan FXML elements.

        controller.provinsiCombo = new ComboBox<>();
        controller.kotaCombo = new ComboBox<>();
        controller.kecamatanCombo = new ComboBox<>();
        controller.lokasiDetailArea = new TextArea();
        controller.jenisBencanaCombo = new ComboBox<>();
        controller.deskripsiArea = new TextArea();
        controller.korbanHilangField = new TextField();
        controller.korbanLukaField = new TextField();
        // ComboBox perlu diisi itemnya agar getValue() tidak null
        controller.provinsiCombo.getItems().add("DKI Jakarta");
        controller.kotaCombo.getItems().add("Jakarta Pusat");
        controller.kecamatanCombo.getItems().add("Menteng");
        controller.jenisBencanaCombo.getItems().add("Banjir");
    }

    // --- Test Case 1: Semua input valid ---
    @Test
    void testValidateForm_allValid() {
        // Mengisi semua field dengan data valid
        controller.provinsiCombo.setValue("DKI Jakarta");
        controller.kotaCombo.setValue("Jakarta Pusat");
        controller.kecamatanCombo.setValue("Menteng");
        controller.lokasiDetailArea.setText("Jalan Contoh No. 1");
        controller.jenisBencanaCombo.setValue("Banjir");
        controller.deskripsiArea.setText("Deskripsi laporan bencana yang lengkap.");
        controller.korbanHilangField.setText("10");
        controller.korbanLukaField.setText("5");

        // Panggil metode validasi
        boolean result = controller.validateForm();

        // Verifikasi hasilnya
        assertTrue(result, "Form seharusnya valid ketika semua input benar.");
    }

    // --- Test Case 2: Field wajib kosong (provinsi) ---
    @Test
    void testValidateForm_emptyProvinsi() {
        // Kosongkan provinsi, isi yang lain valid
        controller.provinsiCombo.setValue(null); // Atau "".getValue()
        controller.kotaCombo.setValue("Jakarta Pusat");
        controller.kecamatanCombo.setValue("Menteng");
        controller.lokasiDetailArea.setText("Jalan Contoh No. 1");
        controller.jenisBencanaCombo.setValue("Banjir");
        controller.deskripsiArea.setText("Deskripsi laporan bencana yang lengkap.");
        controller.korbanHilangField.setText("10");
        controller.korbanLukaField.setText("5");

        boolean result = controller.validateForm();
        assertFalse(result, "Form seharusnya tidak valid ketika provinsi kosong.");
    }

    // --- Test Case 3: Field wajib kosong (deskripsi) ---
    @Test
    void testValidateForm_emptyDeskripsi() {
        controller.provinsiCombo.setValue("DKI Jakarta");
        controller.kotaCombo.setValue("Jakarta Pusat");
        controller.kecamatanCombo.setValue("Menteng");
        controller.lokasiDetailArea.setText("Jalan Contoh No. 1");
        controller.jenisBencanaCombo.setValue("Banjir");
        controller.deskripsiArea.setText(""); // Deskripsi kosong
        controller.korbanHilangField.setText("10");
        controller.korbanLukaField.setText("5");

        boolean result = controller.validateForm();
        assertFalse(result, "Form seharusnya tidak valid ketika deskripsi kosong.");
    }

    // --- Test Case 4: Korban hilang bukan angka ---
    @Test
    void testValidateForm_korbanHilangNotNumeric() {
        controller.provinsiCombo.setValue("DKI Jakarta");
        controller.kotaCombo.setValue("Jakarta Pusat");
        controller.kecamatanCombo.setValue("Menteng");
        controller.lokasiDetailArea.setText("Jalan Contoh No. 1");
        controller.jenisBencanaCombo.setValue("Banjir");
        controller.deskripsiArea.setText("Deskripsi valid.");
        controller.korbanHilangField.setText("abc"); // Bukan angka
        controller.korbanLukaField.setText("5");

        boolean result = controller.validateForm();
        assertFalse(result, "Form seharusnya tidak valid ketika korban hilang bukan angka.");
    }

    // --- Test Case 5: Korban luka negatif ---
    @Test
    void testValidateForm_korbanLukaNegative() {
        controller.provinsiCombo.setValue("DKI Jakarta");
        controller.kotaCombo.setValue("Jakarta Pusat");
        controller.kecamatanCombo.setValue("Menteng");
        controller.lokasiDetailArea.setText("Jalan Contoh No. 1");
        controller.jenisBencanaCombo.setValue("Banjir");
        controller.deskripsiArea.setText("Deskripsi valid.");
        controller.korbanHilangField.setText("10");
        controller.korbanLukaField.setText("-5"); // Angka negatif

        boolean result = controller.validateForm();
        assertFalse(result, "Form seharusnya tidak valid ketika korban luka negatif.");
    }
    // Anda bisa tambahkan lebih banyak test case sesuai kebutuhan
    // Contoh: kombinasi kosong, semua null, dll.
}
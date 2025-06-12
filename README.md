# 🌪️ BantuBencana - Sistem Informasi Laporan Bencana

## 🧾 Penjelasan Singkat Aplikasi

**BantuBencana** adalah aplikasi desktop berbasis JavaFX yang digunakan untuk mencatat dan mengelola laporan bencana di suatu wilayah. Pengguna dapat membuat laporan baru, melihat daftar laporan yang masuk, serta mengelola data laporan yang tersimpan. Aplikasi ini ditujukan untuk mendukung transparansi dan kecepatan dalam penanganan bencana.


## ▶️ Cara Menjalankan Aplikasi

1. **Pastikan Java dan JavaFX sudah terpasang.**

   * Gunakan JDK versi 17 atau lebih tinggi.
   * Pastikan javafx-sdk sudah diunduh dan disimpan di direktori, misalnya `C:\JavaFX\javafx-sdk-24.0.1`.


3. **Jalankan aplikasi melalui file batch**

   

   double-click compile.bat
   


   Script ini akan:

   * Menghapus dan membuat ulang folder bin
   * Mengompilasi source code dan test
   * Menjalankan unit test JUnit
   * Menjalankan aplikasi JavaFX


## 📁 Struktur Folder Proyek

BantuBencana-RPL/
├── src/                      # Kode sumber aplikasi Java
│   └── com/                 # Package utama
│       ├── Main.java  
│       ├── *.java          # Seluruh file source Java
│       └── *.fxml          # File antarmuka JavaFX
├── tests/                   # Unit test JUnit
│   └── *.java
├── lib/                     # Library eksternal
│   └── junit-platform-console-standalone-1.13.0-M3.jar
├── bin/                     # Output hasil kompilasi
├── img/                     # Gambar tampilan aplikasi, untuk dokumentasi
├── doc/                     # Dokumen laporan proyek lengkap dengan screen capture
├── compile.bat              # Skrip kompilasi dan eksekusi aplikasi
└── README.md                # Dokumentasi utama proyek
## 📦 Daftar Modul yang Diimplementasi

| No | Nama Modul                  | Nama File Fisik                                                                                                                                       | Nama File Eksekusi |
|----|-----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------|
| 1  | Dashboard Home              | `Dashboard.java`, `DashboardController.java`, `DashboardView.fxml`, LaporanBencanaData.java                                                        | compile.bat       |
| 2  | Form Buat Laporan Bencana  | `Bencana.java`, `BencanaRepository.java`, `LaporanBencana.java`, `LaporanBencanaData.java`, `BuatLaporanController.java`, BuatLaporanView.fxml    | compile.bat       |
| 3  | Daftar Laporan Bencana     | `Bencana.java`, `BencanaRepository.java`, `LaporanBencana.java`, `LaporanBencanaData.java`, `LaporanListController.java`, `LaporanListView.fxml`, DatabaseViewer.java | compile.bat       |
| 4  | Detail Laporan Bencana     | `Bencana.java`, `BencanaRepository.java`, `LaporanBencana.java`, `LaporanBencanaData.java`, `DetailLaporanController.java`, DetailLaporanView.java | compile.bat       |
| 5  | Form Donasi                | `DonationData.java`, DonationPage.java                                                                                                              | compile.bat       |
| 6  | Form Feedback Pasca-Bencana | `Feedback.java`, FeedbackData.java                                                                                                                  | compile.bat       |
| 7  | Login                      | `User.java`, `UserController.java`, `UserRepository.java`, `LoginController.java`, LoginView.fxml                                                  | compile.bat       |
| 8  | Register                   | `User.java`, `UserController.java`, `UserRepository.java`, `RegisterController.java`, RegisterView.fxml                                            | compile.bat       |

> **Catatan**: Gambar screen capture dapat dilihat di laporan (folder doc).


## 🗃️ Daftar Tabel Basis Data (Berbasis File)

Meskipun tidak menggunakan DBMS relasional, aplikasi menyimpan data laporan ke dalam file .txt menggunakan serialisasi objek Java. Berikut adalah struktur pseudo-tabel yang digunakan:

### Tabel: LaporanBencana

| Atribut            | Tipe Data | Deskripsi                                |
| ------------------ | --------- | ---------------------------------------- |
| jenisBencana     | String    | Jenis bencana yang dilaporkan            |
| lokasi           | String    | Lokasi kejadian bencana                  |
| deskripsi        | String    | Deskripsi kronologi bencana              |
| tingkatKeparahan | String    | Skala keparahan (misalnya ringan, berat) |
| jumlahKorban     | String    | Jumlah korban terdampak                  |
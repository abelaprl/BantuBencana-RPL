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
import javafx.event.ActionEvent; // Penting untuk menangani event Logout

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label userEmailLabel; // Untuk menampilkan email pengguna yang login
    @FXML
    private VBox bencanaListContainer; // Container untuk daftar bencana

    private BencanaRepository bencanaRepository = new BencanaRepository();
    private String loggedInUserEmail; // Untuk menyimpan email pengguna yang login

    // Metode ini dipanggil secara otomatis setelah FXML dimuat.
    // Kami akan memuat data bencana setelah email user diset (melalui initData).
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Implementasi logika inisialisasi awal jika ada
    }

    // Metode untuk menerima data (email) dari Main.java setelah login
    public void initData(String email) {
        this.loggedInUserEmail = email;
        userEmailLabel.setText(email); // Tampilkan email pengguna di sidebar
        loadBencanaData(); // Muat data bencana setelah email diatur
    }

    // Metode untuk memuat dan menampilkan data bencana
    private void loadBencanaData() {
        List<Bencana> bencanas = bencanaRepository.findAll(); // Ambil semua data bencana
        bencanaListContainer.getChildren().clear(); // Bersihkan container dari elemen sebelumnya

        if (bencanas.isEmpty()) {
            Label noDataLabel = new Label("Tidak ada data bencana yang tersedia.");
            noDataLabel.setTextFill(Color.GRAY);
            noDataLabel.setFont(new Font(16));
            bencanaListContainer.getChildren().add(noDataLabel);
        } else {
            // Untuk setiap objek Bencana, buatkan "card" tampilannya
            for (Bencana bencana : bencanas) {
                bencanaListContainer.getChildren().add(createBencanaCard(bencana));
            }
        }
    }

    // Metode untuk membuat "card" (tampilan visual) untuk setiap bencana
    private VBox createBencanaCard(Bencana bencana) {
        VBox card = new VBox(10); // VBox dengan spasi 10px antar elemen
        card.setPadding(new Insets(15)); // Padding di dalam card
        card.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        card.setMaxWidth(600); // Batasi lebar card untuk tampilan yang rapi

        // --- Gambar Bencana ---
        ImageView imageView = new ImageView();
        // Path relatif dari lokasi aplikasi dijalankan (yaitu root proyek: BantuBencana-RPL)
        // Karena `Main.class.getResource()` akan mencari di classpath yang saat ini adalah src/
        // dan `new Image()` dari String path akan relatif terhadap CWD (Current Working Directory)
        // yang saat menjalankan `java -cp . Main` adalah `src/`, maka path ini sedikit berbeda.
        // Solusi paling mudah untuk saat ini adalah dengan jalur absolut ke file gambar.
        // Atau, jika Anda menjalankan dari `BantuBencana-RPL` (parent dari src), maka
        // `file:img/bencana_photos/` akan berfungsi.
        // Untuk skenario flat di src/ dan dijalankan dari src/,
        // kita perlu menggunakan path file absolut atau memastikan `img` di root proyek.

        // Solusi terbaik untuk struktur saat ini (semua di src) dan dijalankan dari src/:
        // Asumsikan `img` berada di PARENT dari `src`.
        // Dapatkan path dari `src/Main.java` (yang sama dengan DashboardController)
        // Kemudian naik satu level ke `BantuBencana-RPL/` dan turun ke `img/bencana_photos/`
        URL imageUrl = Main.class.getResource("/../img/bencana_photos/" + bencana.getNamaFileGambar());

        if (imageUrl != null) {
            Image image = new Image(imageUrl.toExternalForm());
            imageView.setImage(image);
            imageView.setFitWidth(570); // Lebar gambar dalam card
            imageView.setFitHeight(200); // Tinggi gambar
            imageView.setPreserveRatio(false); // Biarkan gambar mengisi area
        } else {
            System.err.println("Gagal memuat gambar: /../img/bencana_photos/" + bencana.getNamaFileGambar() + " - File tidak ditemukan atau path salah.");
            Label errorLabel = new Label("Gambar tidak ditemukan");
            errorLabel.setTextFill(Color.RED);
            imageView.setFitWidth(570);
            imageView.setFitHeight(100);
            // Tambahkan errorLabel jika gambar tidak ditemukan
            card.getChildren().add(errorLabel);
        }

        // --- Judul Bencana ---
        Label judulLabel = new Label(bencana.getJudul());
        judulLabel.setFont(new Font("System Bold", 18));
        judulLabel.setWrapText(true); // Mengizinkan teks untuk wrap

        // --- Lokasi ---
        Label lokasiLabel = new Label("Lokasi: " + bencana.getLokasi());
        lokasiLabel.setFont(new Font(14));

        // --- Tanggal ---
        Label tanggalLabel = new Label("Tanggal: " + bencana.getTanggal());
        tanggalLabel.setFont(new Font(14));

        // --- Deskripsi Singkat ---
        Label deskripsiLabel = new Label(bencana.getDeskripsiSingkat());
        deskripsiLabel.setFont(new Font(13));
        deskripsiLabel.setWrapText(true);

        // --- Tombol "Lihat Detail" atau "Donasi" (opsional) ---
        HBox buttonBox = new HBox(10); // HBox dengan spasi 10px antar tombol
        Button detailButton = new Button("Lihat Detail");
        Button donasiButton = new Button("Donasi");
        buttonBox.getChildren().addAll(detailButton, donasiButton);

        // Tambahkan semua elemen ke card
        card.getChildren().addAll(imageView, judulLabel, lokasiLabel, tanggalLabel, deskripsiLabel, buttonBox);

        return card;
    }

    // Metode untuk menangani logout
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Main.showLoginView(); // Kembali ke tampilan login
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error jika tidak bisa kembali ke login
            System.err.println("Gagal kembali ke halaman login.");
        }
    }

    // TODO: Tambahkan metode lain untuk navigasi sidebar jika tombol-tombol sidebar
    // memiliki fx:id dan onAction yang terhubung di FXML
    // Contoh:
    // @FXML
    // private void handleLaporanBencanaClick(ActionEvent event) {
    //     // Logic untuk beralih ke tampilan laporan bencana
    // }
}
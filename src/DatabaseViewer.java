import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class DatabaseViewer {
    private Stage stage;
    private TabPane tabPane;
    private VBox feedbackContent;
    private VBox laporanContent;
    private VBox donationContent; // BARU: VBox untuk konten donasi

    public DatabaseViewer() {
        stage = new Stage();
        stage.setTitle("Database Viewer");

        tabPane = new TabPane();

        // Tab untuk Feedback
        Tab feedbackTab = new Tab("Feedback");
        feedbackTab.setClosable(false);
        feedbackContent = new VBox(10);
        feedbackContent.setPadding(new Insets(10));
        ScrollPane feedbackScrollPane = new ScrollPane(feedbackContent);
        feedbackScrollPane.setFitToWidth(true);
        feedbackTab.setContent(feedbackScrollPane);

        // Tab untuk Laporan Bencana
        Tab laporanTab = new Tab("Laporan Bencana");
        laporanTab.setClosable(false);
        laporanContent = new VBox(10);
        laporanContent.setPadding(new Insets(10));
        ScrollPane laporanScrollPane = new ScrollPane(laporanContent);
        laporanScrollPane.setFitToWidth(true);
        laporanTab.setContent(laporanScrollPane);

        // BARU: Tab untuk Donasi
        Tab donationTab = new Tab("Donasi");
        donationTab.setClosable(false);
        donationContent = new VBox(10);
        donationContent.setPadding(new Insets(10));
        ScrollPane donationScrollPane = new ScrollPane(donationContent);
        donationScrollPane.setFitToWidth(true);
        donationTab.setContent(donationScrollPane);


        // Tambahkan semua tab ke TabPane
        tabPane.getTabs().addAll(feedbackTab, laporanTab, donationTab); // BARU: Tambahkan donationTab

        Scene scene = new Scene(tabPane, 800, 600);
        stage.setScene(scene);

        // Refresh data saat tab diaktifkan
        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if (newTab == feedbackTab) {
                refreshFeedbackList();
            } else if (newTab == laporanTab) {
                refreshLaporanList();
            } else if (newTab == donationTab) { // BARU: Tambahkan logika untuk tab donasi
                refreshDonationList();
            }
        });

        // Pastikan data dimuat saat pertama kali DatabaseViewer dibuka
        refreshData();
    }

    // Metode untuk menampilkan stage
    public void showStage() {
        stage.show();
    }

    // Metode untuk menyembunyikan stage (jika diperlukan)
    public void hideStage() {
        stage.hide();
    }

    // Metode untuk me-refresh semua data di semua tab
    public void refreshData() {
        refreshFeedbackList();
        refreshLaporanList();
        refreshDonationList(); // BARU: Refresh data donasi
    }

    private void refreshFeedbackList() {
        feedbackContent.getChildren().clear();
        List<FeedbackData> feedbackList = Dashboard.getAllFeedback();
        if (feedbackList == null || feedbackList.isEmpty()) {
            feedbackContent.getChildren().add(new Label("Belum ada data feedback."));
            return;
        }

        for (int i = 0; i < feedbackList.size(); i++) {
            FeedbackData feedback = feedbackList.get(i);
            HBox feedbackBox = createFeedbackBox(feedback, i);
            feedbackContent.getChildren().add(feedbackBox);
        }
    }

    private HBox createFeedbackBox(FeedbackData feedback, int index) {
        HBox box = new HBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: #e6f7ff; -fx-background-radius: 5;");

        VBox detailsBox = new VBox(5);
        detailsBox.setPrefWidth(650);
        detailsBox.getChildren().addAll(
            new Label("Laporan: " + feedback.getLaporanBencana()),
            new Label("Jenis: " + feedback.getJenisBencana()),
            new Label("Lokasi: " + feedback.getLokasiLaporan()),
            new Label("Email Pengguna: " + feedback.getUserEmail()),
            new Label("Rating Penanganan: " + feedback.getRatingPenanganan() + " Bintang"),
            new Label("Rating Kinerja Relawan: " + feedback.getRatingKinerjaRelawan() + " Bintang"),
            new Label("Rating Alokasi Donasi: " + feedback.getRatingAlokasiDonasi() + " Bintang"),
            new Label("Rating Pengalaman Buruk: " + feedback.getRatingPengalamanBuruk() + " Bintang"),
            new Label("Rata-rata Rating: " + String.format("%.1f", feedback.getAverageRating())),
            new Label("Timestamp: " + feedback.getTimestamp()),
            new Label("Media Pendukung: " + (feedback.getMediaPendukungPath() != null ? new File(feedback.getMediaPendukungPath()).getName() : "Tidak ada")),
            new Label("Feedback Penanganan: " + feedback.getFeedbackPenanganan()),
            new Label("Feedback Kinerja Relawan: " + feedback.getFeedbackKinerjaRelawan()),
            new Label("Feedback Alokasi Donasi: " + feedback.getFeedbackAlokasiDonasi()),
            new Label("Feedback Pengalaman Buruk: " + feedback.getFeedbackPengalamanBuruk()),
            new Label("Evaluasi Tambahan: " + feedback.getEvaluasiTambahan())
        );

        if (feedback.getMediaPendukungPath() != null && !feedback.getMediaPendukungPath().isEmpty()) {
            File imageFile = new File(feedback.getMediaPendukungPath());
            if (imageFile.exists()) {
                try {
                    Image image = new Image(new FileInputStream(imageFile));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);
                    imageView.setPreserveRatio(true);
                    detailsBox.getChildren().add(imageView);
                } catch (FileNotFoundException e) {
                    System.err.println("Gambar tidak ditemukan: " + imageFile.getAbsolutePath());
                }
            } else {
                System.err.println("File gambar tidak ada di path: " + imageFile.getAbsolutePath());
            }
        }

        VBox actionsBox = new VBox(10);
        actionsBox.setAlignment(Pos.CENTER);

        Button deleteButton = new Button("Hapus");
        deleteButton.setStyle("-fx-background-color: #ff6b6b;");
        deleteButton.setOnAction(e -> {
            Dashboard.removeFeedbackFromDatabase(index);
            refreshFeedbackList();
        });

        actionsBox.getChildren().add(deleteButton);
        box.getChildren().addAll(detailsBox, actionsBox);
        return box;
    }

    private void refreshLaporanList() {
        laporanContent.getChildren().clear();
        List<LaporanBencanaData> laporanList = Dashboard.getAllLaporanBencana();
        if (laporanList == null || laporanList.isEmpty()) {
            laporanContent.getChildren().add(new Label("Belum ada data laporan bencana."));
            return;
        }

        for (int i = 0; i < laporanList.size(); i++) {
            LaporanBencanaData laporan = laporanList.get(i);
            HBox laporanBox = createLaporanBox(laporan, i);
            laporanContent.getChildren().add(laporanBox);
        }
    }

    private HBox createLaporanBox(LaporanBencanaData laporan, int index) {
        HBox box = new HBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: #f0f8ff; -fx-background-radius: 5;");

        VBox detailsBox = new VBox(5);
        detailsBox.setPrefWidth(650);
        detailsBox.getChildren().addAll(
            new Label("Jenis Bencana: " + laporan.getJenisBencana()),
            new Label("Lokasi: " + laporan.getLokasi()),
            new Label("Deskripsi: " + laporan.getDeskripsi()),
            new Label("Tingkat Keparahan: " + laporan.getTingkatKeparahan()),
            new Label("Jumlah Korban: " + laporan.getJumlahKorban())
        );

        VBox actionsBox = new VBox(10);
        actionsBox.setAlignment(Pos.CENTER);

        Button deleteButton = new Button("Hapus");
        deleteButton.setStyle("-fx-background-color: #ff6b6b;");
        deleteButton.setOnAction(e -> {
            Dashboard.removeLaporanBencanaFromDatabase(index);
            refreshLaporanList();
        });

        actionsBox.getChildren().add(deleteButton);
        box.getChildren().addAll(detailsBox, actionsBox);
        return box;
    }

    // BARU: Metode untuk me-refresh daftar donasi
    private void refreshDonationList() {
        donationContent.getChildren().clear();
        List<DonationData> donationList = Dashboard.getAllDonations(); // Mengambil data donasi
        if (donationList == null || donationList.isEmpty()) {
            donationContent.getChildren().add(new Label("Belum ada data donasi."));
            return;
        }

        for (int i = 0; i < donationList.size(); i++) {
            DonationData donation = donationList.get(i);
            HBox donationBox = createDonationBox(donation, i);
            donationContent.getChildren().add(donationBox);
        }
    }

    // BARU: Metode untuk membuat HBox tampilan untuk satu data donasi
    private HBox createDonationBox(DonationData donation, int index) {
        HBox box = new HBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: #fff0f5; -fx-background-radius: 5;"); // Warna pink muda

        VBox detailsBox = new VBox(5);
        detailsBox.setPrefWidth(650);
        detailsBox.getChildren().addAll(
            new Label("Bencana: " + donation.getDisasterType() + " di " + donation.getLocation()),
            new Label("Jenis Donasi: " + donation.getDonationType()),
            new Label("Jumlah/Deskripsi: " + donation.getAmountOrDescription()),
            new Label("Email Donor: " + donation.getDonorEmail()),
            new Label("Waktu Donasi: " + donation.getTimestamp())
        );

        VBox actionsBox = new VBox(10);
        actionsBox.setAlignment(Pos.CENTER);

        Button deleteButton = new Button("Hapus");
        deleteButton.setStyle("-fx-background-color: #ff6b6b;");
        deleteButton.setOnAction(e -> {
            // Anda perlu menambahkan metode removeDonationFromDatabase di Dashboard
            // Dashboard.removeDonationFromDatabase(index); // Ini akan menyebabkan error jika belum ada
            // Untuk sementara, kita bisa menghapus dari list lokal dan refresh.
            // Namun, untuk persistensi yang benar, Anda butuh metode di Dashboard dan DataManager.
            // Saya asumsikan Anda akan menambahkan ini di Dashboard nanti.
            // Jika Anda sudah menambahkan Dashboard.removeDonationFromDatabase(index); maka uncomment baris di bawah
            // Dashboard.removeDonationFromDatabase(index);
            // Dan pastikan DataManager.saveDonationData() dipanggil setelah penghapusan.

            // Solusi sementara jika Anda belum punya removeDonationFromDatabase di Dashboard:
            // Ini akan menghapus dari tampilan tapi tidak dari file/database.
            // Untuk solusi yang persist, Anda harus buat method di Dashboard dan DataManager.
            // Sebagai solusi sementara, jika Anda belum menambahkan method di Dashboard untuk menghapus donasi:
            List<DonationData> currentDonations = Dashboard.getAllDonations();
            if (index >= 0 && index < currentDonations.size()) {
                currentDonations.remove(index);
                DataManager.saveDonationData(); // Pastikan data disimpan setelah dihapus
                System.out.println("Donasi berhasil dihapus dari database (sementara)!");
            }
            refreshDonationList();
        });

        actionsBox.getChildren().add(deleteButton);
        box.getChildren().addAll(detailsBox, actionsBox);
        return box;
    }
}
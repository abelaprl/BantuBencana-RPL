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

        tabPane.getTabs().addAll(feedbackTab, laporanTab);

        Scene scene = new Scene(tabPane, 800, 600);
        stage.setScene(scene);

        // Refresh data saat tab diaktifkan
        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if (newTab == feedbackTab) {
                refreshFeedbackList();
            } else if (newTab == laporanTab) {
                refreshLaporanList();
            }
        });
    }

    // Metode untuk menampilkan stage
    public void showStage() { // Ubah nama dari show() menjadi showStage() untuk menghindari konflik
        stage.show();
    }

    // Metode untuk menyembunyikan stage (jika diperlukan)
    public void hideStage() {
        stage.hide();
    }

    public void refreshData() {
        refreshFeedbackList();
        refreshLaporanList();
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
        box.setStyle("-fx-background-color: #e6f7ff; -fx-background-radius: 5;"); // Warna sedikit berbeda

        VBox detailsBox = new VBox(5);
        detailsBox.setPrefWidth(650); // Sesuaikan lebar
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

        // Tambahkan gambar jika ada media pendukung
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
}
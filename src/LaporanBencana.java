import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LaporanBencana extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Judul form
        Text title = new Text("Laporan Bencana");
        title.setFont(Font.font("Arial", 20));
        title.setStyle("-fx-font-weight: bold;");
        
        // Form fields
        Label jenisLabel = new Label("Jenis Bencana:");
        jenisLabel.setStyle("-fx-font-weight: bold;");
        ComboBox<String> jenisCombo = new ComboBox<>();
        jenisCombo.getItems().addAll("Banjir", "Gempa Bumi", "Kebakaran", "Longsor", "Tsunami", "Angin Topan");
        jenisCombo.setPrefWidth(300);
        
        Label lokasiLabel = new Label("Lokasi Bencana:");
        lokasiLabel.setStyle("-fx-font-weight: bold;");
        TextField lokasiField = new TextField();
        lokasiField.setPromptText("Masukkan lokasi bencana");
        lokasiField.setPrefWidth(300);
        
        Label deskripsiLabel = new Label("Deskripsi Bencana:");
        deskripsiLabel.setStyle("-fx-font-weight: bold;");
        TextArea deskripsiArea = new TextArea();
        deskripsiArea.setPromptText("Jelaskan detail bencana yang terjadi");
        deskripsiArea.setPrefRowCount(4);
        deskripsiArea.setPrefWidth(300);
        
        Label tingkatLabel = new Label("Tingkat Keparahan:");
        tingkatLabel.setStyle("-fx-font-weight: bold;");
        ComboBox<String> tingkatCombo = new ComboBox<>();
        tingkatCombo.getItems().addAll("Ringan", "Sedang", "Berat", "Sangat Berat");
        tingkatCombo.setPrefWidth(300);
        
        Label korbanLabel = new Label("Perkiraan Jumlah Korban:");
        korbanLabel.setStyle("-fx-font-weight: bold;");
        TextField korbanField = new TextField();
        korbanField.setPromptText("Masukkan jumlah korban (jika ada)");
        korbanField.setPrefWidth(300);
        
        // Buttons
        Button batalButton = new Button("Batal");
        batalButton.setStyle("-fx-background-color: white; -fx-border-color: #ff6b6b; -fx-text-fill: #ff6b6b; -fx-pref-width: 100;");
        batalButton.setOnAction(e -> primaryStage.close());
        
        Button laporButton = new Button("Kirim Laporan");
        laporButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-pref-width: 120;");
        laporButton.setOnAction(e -> {
            // Validasi input
            if (jenisCombo.getValue() == null || lokasiField.getText().trim().isEmpty() || 
                deskripsiArea.getText().trim().isEmpty() || tingkatCombo.getValue() == null) {
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Tidak Lengkap");
                alert.setHeaderText(null);
                alert.setContentText("Mohon lengkapi semua field yang wajib diisi!");
                alert.showAndWait();
                return;
            }
            
            // Buat object laporan bencana baru
            LaporanBencanaData laporanBaru = new LaporanBencanaData(
                jenisCombo.getValue(),
                lokasiField.getText().trim(),
                deskripsiArea.getText().trim(),
                tingkatCombo.getValue(),
                korbanField.getText().trim().isEmpty() ? "0" : korbanField.getText().trim()
            );
            
            // Simpan ke database global
            Dashboard.addLaporanBencanaToDatabase(laporanBaru);
            
            // Tampilkan konfirmasi
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Laporan Terkirim");
            alert.setHeaderText(null);
            alert.setContentText("Laporan bencana berhasil dikirim dan disimpan ke database!\n\n" +
                               "Jenis: " + jenisCombo.getValue() + "\n" +
                               "Lokasi: " + lokasiField.getText() + "\n" +
                               "Tingkat: " + tingkatCombo.getValue());
            alert.showAndWait();
            
            // Reset form
            jenisCombo.setValue(null);
            lokasiField.clear();
            deskripsiArea.clear();
            tingkatCombo.setValue(null);
            korbanField.clear();
        });
        
        // Layout
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        
        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(batalButton, laporButton);
        
        mainLayout.getChildren().addAll(
            title,
            jenisLabel, jenisCombo,
            lokasiLabel, lokasiField,
            deskripsiLabel, deskripsiArea,
            tingkatLabel, tingkatCombo,
            korbanLabel, korbanField,
            buttonLayout
        );
        
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(500, 600);
        
        Scene scene = new Scene(scrollPane, 500, 600);
        primaryStage.setTitle("Laporan Bencana");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class LaporanListController implements Initializable {

    @FXML private TabPane tabPane;
    @FXML private Tab publikTab;
    @FXML private Tab olehAndaTab;
    @FXML private TableView<LaporanBencanaDisplay> publikTable;
    @FXML private TableView<LaporanBencanaDisplay> olehAndaTable;
    @FXML private Button buatLaporanButton;

    private String currentUserEmail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTables();
        loadData();
    }

    public void initData(String userEmail) {
        this.currentUserEmail = userEmail;
        loadData();
    }

    private void setupTables() {
        setupTable(publikTable);
        setupTable(olehAndaTable);
    }

    private void setupTable(TableView<LaporanBencanaDisplay> table) {
        // Jenis Bencana Column
        TableColumn<LaporanBencanaDisplay, String> jenisCol = new TableColumn<>("Jenis Bencana");
        jenisCol.setCellValueFactory(new PropertyValueFactory<>("jenisBencana"));
        jenisCol.setPrefWidth(150);

        // Lokasi Column
        TableColumn<LaporanBencanaDisplay, String> lokasiCol = new TableColumn<>("Lokasi");
        lokasiCol.setCellValueFactory(new PropertyValueFactory<>("lokasi"));
        lokasiCol.setPrefWidth(180);

        // Status Column
        TableColumn<LaporanBencanaDisplay, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(120);
        statusCol.setCellFactory(column -> new TableCell<LaporanBencanaDisplay, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    switch (status.toLowerCase()) {
                        case "diverifikasi":
                            setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;");
                            break;
                        case "diproses":
                            setStyle("-fx-text-fill: #ffc107; -fx-font-weight: bold;");
                            break;
                        case "ditolak":
                            setStyle("-fx-text-fill: #dc3545; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("-fx-text-fill: #6c757d;");
                    }
                }
            }
        });

        // Waktu Column
        TableColumn<LaporanBencanaDisplay, String> waktuCol = new TableColumn<>("Waktu");
        waktuCol.setCellValueFactory(new PropertyValueFactory<>("waktu"));
        waktuCol.setPrefWidth(120);

        // Action Column
        TableColumn<LaporanBencanaDisplay, Void> actionCol = new TableColumn<>("Aksi");
        actionCol.setPrefWidth(200);
        actionCol.setCellFactory(column -> new TableCell<LaporanBencanaDisplay, Void>() {
            private final Button lihatDetailBtn = new Button("Lihat Detail");
            private final Button donasiBtn = new Button("Donasi");
            private final HBox actionBox = new HBox(8);

            {
                lihatDetailBtn.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-background-radius: 15; -fx-font-size: 11px; -fx-padding: 5 12;");
                donasiBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-background-radius: 15; -fx-font-size: 11px; -fx-padding: 5 12;");
                
                actionBox.setAlignment(Pos.CENTER);
                actionBox.getChildren().addAll(lihatDetailBtn, donasiBtn);

                lihatDetailBtn.setOnAction(event -> {
                    LaporanBencanaDisplay laporan = getTableView().getItems().get(getIndex());
                    handleLihatDetail(laporan);
                });

                donasiBtn.setOnAction(event -> {
                    LaporanBencanaDisplay laporan = getTableView().getItems().get(getIndex());
                    handleDonasi(laporan);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    LaporanBencanaDisplay laporan = getTableView().getItems().get(getIndex());
                    
                    // Show different buttons based on status and tab
                    actionBox.getChildren().clear();
                    actionBox.getChildren().add(lihatDetailBtn);
                    
                    if (laporan.getStatus().equals("Diverifikasi")) {
                        actionBox.getChildren().add(donasiBtn);
                    }
                    
                    setGraphic(actionBox);
                }
            }
        });

        table.getColumns().addAll(jenisCol, lokasiCol, statusCol, waktuCol, actionCol);
        table.setRowFactory(tv -> {
            TableRow<LaporanBencanaDisplay> row = new TableRow<>();
            row.setStyle("-fx-background-color: white; -fx-border-color: #e9ecef; -fx-border-width: 0 0 1 0;");
            return row;
        });
    }

    private void loadData() {
        List<LaporanBencanaData> allLaporan = Dashboard.getAllLaporanBencana();
        ObservableList<LaporanBencanaDisplay> publikData = FXCollections.observableArrayList();
        ObservableList<LaporanBencanaDisplay> olehAndaData = FXCollections.observableArrayList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String currentTime = LocalDateTime.now().format(formatter);

        for (int i = 0; i < allLaporan.size(); i++) {
            LaporanBencanaData laporan = allLaporan.get(i);
            String status = getRandomStatus(i);
            
            LaporanBencanaDisplay display = new LaporanBencanaDisplay(
                laporan.getJenisBencana(),
                laporan.getLokasi(),
                status,
                currentTime,
                laporan
            );
            
            publikData.add(display);
            
            // Add some items to "Oleh Anda" tab (simulate user's reports)
            if (i < 3) {
                olehAndaData.add(display);
            }
        }

        publikTable.setItems(publikData);
        olehAndaTable.setItems(olehAndaData);
    }

    private String getRandomStatus(int index) {
        String[] statuses = {"Diverifikasi", "Diproses", "Ditolak"};
        return statuses[index % statuses.length];
    }

    private void handleLihatDetail(LaporanBencanaDisplay laporan) {
        try {
            Main.showDetailLaporan(laporan.getOriginalData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDonasi(LaporanBencanaDisplay laporan) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Donasi");
        alert.setHeaderText(null);
        alert.setContentText("Fitur donasi untuk " + laporan.getJenisBencana() + " di " + laporan.getLokasi() + " akan segera tersedia.");
        alert.showAndWait();
    }

    @FXML
    private void handleBuatLaporan(ActionEvent event) {
        try {
            Main.showBuatLaporan();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inner class for table display
    public static class LaporanBencanaDisplay {
        private String jenisBencana;
        private String lokasi;
        private String status;
        private String waktu;
        private LaporanBencanaData originalData;

        public LaporanBencanaDisplay(String jenisBencana, String lokasi, String status, String waktu, LaporanBencanaData originalData) {
            this.jenisBencana = jenisBencana;
            this.lokasi = lokasi;
            this.status = status;
            this.waktu = waktu;
            this.originalData = originalData;
        }

        // Getters
        public String getJenisBencana() { return jenisBencana; }
        public String getLokasi() { return lokasi; }
        public String getStatus() { return status; }
        public String getWaktu() { return waktu; }
        public LaporanBencanaData getOriginalData() { return originalData; }
    }
}

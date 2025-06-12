package com;
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
import java.util.Date;
import java.text.SimpleDateFormat;
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
        
        // Get current user email from session
        if (UserSessionManager.isLoggedIn()) {
            currentUserEmail = UserSessionManager.getCurrentUserEmail();
        } else {
            currentUserEmail = "anonymous";
        }
        
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
        jenisCol.setPrefWidth(200);

        // Lokasi Column
        TableColumn<LaporanBencanaDisplay, String> lokasiCol = new TableColumn<>("Lokasi");
        lokasiCol.setCellValueFactory(new PropertyValueFactory<>("lokasi"));
        lokasiCol.setPrefWidth(200);

        // Waktu Column
        TableColumn<LaporanBencanaDisplay, String> waktuCol = new TableColumn<>("Waktu");
        waktuCol.setCellValueFactory(new PropertyValueFactory<>("waktu"));
        waktuCol.setPrefWidth(150);

        // Action Column - hanya tombol Lihat Detail
        TableColumn<LaporanBencanaDisplay, Void> actionCol = new TableColumn<>("Aksi");
        actionCol.setPrefWidth(150);
        actionCol.setCellFactory(column -> new TableCell<LaporanBencanaDisplay, Void>() {
            private final Button lihatDetailBtn = new Button("Lihat Detail");
            private final HBox actionBox = new HBox(8);

            {
                lihatDetailBtn.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-background-radius: 15; -fx-font-size: 11px; -fx-padding: 5 12;");
                
                actionBox.setAlignment(Pos.CENTER);
                actionBox.getChildren().add(lihatDetailBtn);

                lihatDetailBtn.setOnAction(event -> {
                    LaporanBencanaDisplay laporan = getTableView().getItems().get(getIndex());
                    handleLihatDetail(laporan);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionBox);
                }
            }
        });

        table.getColumns().addAll(jenisCol, lokasiCol, waktuCol, actionCol);
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

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (LaporanBencanaData laporan : allLaporan) {
            // Format timestamp to readable date
            String formattedDate = formatter.format(new Date(laporan.getTimestamp()));
            
            LaporanBencanaDisplay display = new LaporanBencanaDisplay(
                laporan.getJenisBencana(),
                laporan.getLokasi(),
                formattedDate,
                laporan
            );
            
            // Add to public tab
            publikData.add(display);
            
            // Add to "Oleh Anda" tab only if created by current user
            if (laporan.getCreatedBy().equals(currentUserEmail)) {
                olehAndaData.add(display);
            }
        }

        publikTable.setItems(publikData);
        olehAndaTable.setItems(olehAndaData);
        
        // Update tab titles with count
        publikTab.setText("Semua Laporan (" + publikData.size() + ")");
        olehAndaTab.setText("Oleh Anda (" + olehAndaData.size() + ")");
    }

    private void handleLihatDetail(LaporanBencanaDisplay laporan) {
        try {
            Main.showDetailLaporan(laporan.getOriginalData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBuatLaporan(ActionEvent event) {
        try {
            Main.showBuatLaporan();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inner class for table display - status field removed
    public static class LaporanBencanaDisplay {
        private String jenisBencana;
        private String lokasi;
        private String waktu;
        private LaporanBencanaData originalData;

        public LaporanBencanaDisplay(String jenisBencana, String lokasi, String waktu, LaporanBencanaData originalData) {
            this.jenisBencana = jenisBencana;
            this.lokasi = lokasi;
            this.waktu = waktu;
            this.originalData = originalData;
        }

        // Getters - status getter removed
        public String getJenisBencana() { return jenisBencana; }
        public String getLokasi() { return lokasi; }
        public String getWaktu() { return waktu; }
        public LaporanBencanaData getOriginalData() { return originalData; }
    }
}

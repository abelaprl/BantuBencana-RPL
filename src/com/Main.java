package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        showLoginView();
    }

    public static void showLoginView() throws IOException {
        System.out.println("DEBUG: Loading LoginView...");
        URL fxmlLocation = Main.class.getResource("/com/LoginView.fxml");
        if (fxmlLocation == null) {
            System.err.println("ERROR: LoginView.fxml not found! Trying alternative path...");
            fxmlLocation = Main.class.getResource("LoginView.fxml");
            if (fxmlLocation == null) {
                System.err.println("ERROR: LoginView.fxml not found in any location!");
                return;
            }
        }
        
        System.out.println("DEBUG: Found LoginView.fxml at: " + fxmlLocation);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Login - Disaster Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showRegisterView() throws IOException {
        System.out.println("DEBUG: Loading RegisterView...");
        URL fxmlLocation = Main.class.getResource("/com/RegisterView.fxml");
        if (fxmlLocation == null) {
            System.err.println("ERROR: RegisterView.fxml not found! Trying alternative path...");
            fxmlLocation = Main.class.getResource("RegisterView.fxml");
            if (fxmlLocation == null) {
                System.err.println("ERROR: RegisterView.fxml not found in any location!");
                return;
            }
        }
        
        System.out.println("DEBUG: Found RegisterView.fxml at: " + fxmlLocation);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Register - Disaster Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showDashboardView() throws IOException {
        System.out.println("DEBUG: Loading DashboardView...");
        URL fxmlLocation = Main.class.getResource("/com/DashboardView.fxml");
        if (fxmlLocation == null) {
            System.err.println("ERROR: DashboardView.fxml not found! Trying alternative path...");
            fxmlLocation = Main.class.getResource("DashboardView.fxml");
            if (fxmlLocation == null) {
                System.err.println("ERROR: DashboardView.fxml not found in any location!");
                return;
            }
        }
        
        System.out.println("DEBUG: Found DashboardView.fxml at: " + fxmlLocation);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        
        DashboardController controller = loader.getController();
        if (controller != null) {
            controller.initData("user@example.com");
        }
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Dashboard - Disaster Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showBuatLaporan() throws IOException {
        System.out.println("DEBUG: Loading BuatLaporan form...");
        URL fxmlLocation = Main.class.getResource("/com/BuatLaporanView.fxml");
        if (fxmlLocation == null) {
            System.err.println("ERROR: BuatLaporanView.fxml not found! Trying alternative path...");
            fxmlLocation = Main.class.getResource("BuatLaporanView.fxml");
            if (fxmlLocation == null) {
                System.err.println("ERROR: BuatLaporanView.fxml not found in any location!");
                return;
            }
        }
        
        System.out.println("DEBUG: Found BuatLaporanView.fxml at: " + fxmlLocation);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Buat Laporan Bencana - Disaster Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showDetailLaporan(Object laporan) throws IOException {
        System.out.println("DEBUG: Loading DetailLaporan...");
        URL fxmlLocation = Main.class.getResource("/com/DetailLaporanView.fxml");
        if (fxmlLocation == null) {
            System.err.println("ERROR: DetailLaporanView.fxml not found! Trying alternative path...");
            fxmlLocation = Main.class.getResource("DetailLaporanView.fxml");
            if (fxmlLocation == null) {
                System.err.println("ERROR: DetailLaporanView.fxml not found in any location!");
                return;
            }
        }
        
        System.out.println("DEBUG: Found DetailLaporanView.fxml at: " + fxmlLocation);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        
        DetailLaporanController controller = loader.getController();
        if (controller != null && laporan != null) {
            if (laporan instanceof Bencana) {
                // Convert Bencana to LaporanBencanaData if needed
                Bencana bencana = (Bencana) laporan;
                LaporanBencanaData laporanData = new LaporanBencanaData(
                    bencana.getJudul(),
                    bencana.getLokasi(),
                    bencana.getDeskripsiSingkat(),
                    "Sedang", // default tingkat keparahan
                    "0" // default jumlah korban
                );
                controller.initData(laporanData);
            } else if (laporan instanceof LaporanBencanaData) {
                controller.initData((LaporanBencanaData) laporan);
            }
        }
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Detail Laporan - Disaster Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showLaporanList() throws IOException {
        System.out.println("DEBUG: Loading LaporanList...");
        URL fxmlLocation = Main.class.getResource("/com/LaporanListView.fxml");
        if (fxmlLocation == null) {
            System.err.println("ERROR: LaporanListView.fxml not found! Trying alternative path...");
            fxmlLocation = Main.class.getResource("LaporanListView.fxml");
            if (fxmlLocation == null) {
                System.err.println("ERROR: LaporanListView.fxml not found in any location!");
                return;
            }
        }
        
        System.out.println("DEBUG: Found LaporanListView.fxml at: " + fxmlLocation);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Daftar Laporan - Disaster Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

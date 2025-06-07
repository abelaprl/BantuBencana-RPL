package com.bantubencana.controller;

import com.bantubencana.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;

public class HomeController {

    @FXML
    private Button ButtonHome;
    @FXML
    private Button ButtonLaporanBencana;
    @FXML
    private Button ButtonLaporanDistribusi;
    @FXML
    private Button ButtonRelawanVolunteer;
    @FXML
    private Button ButtonFeedback;
    @FXML
    private Button ButtonSettings;
    @FXML
    private Button ButtonDonasi;
    @FXML
    private Button ButtonBantu; // This seems to be "Form Distribusi Bantuan"

    @FXML
    public void initialize() {
        // Initialization logic if needed (e.g., load user specific data)
    }

    @FXML
    private void handleHome(ActionEvent event) throws IOException {
        System.out.println("Navigating to Home (already here)");
        // If already on home, might refresh or do nothing
    }

    @FXML
    private void handleLaporanBencana(ActionEvent event) {
        System.out.println("Navigating to Laporan Bencana");
        // TODO: Implement navigation to Laporan Bencana scene
    }

    @FXML
    private void handleLaporanDistribusi(ActionEvent event) {
        System.out.println("Navigating to Laporan Distribusi");
        // TODO: Implement navigation to Laporan Distribusi scene
    }

    @FXML
    private void handleRelawanVolunteer(ActionEvent event) {
        System.out.println("Navigating to Relawan/Volunteer Registration");
        // TODO: Implement navigation to Relawan/Volunteer scene
    }

    @FXML
    private void handleFeedback(ActionEvent event) {
        System.out.println("Navigating to Feedback");
        // TODO: Implement navigation to Feedback scene
    }

    @FXML
    private void handleSettings(ActionEvent event) {
        System.out.println("Navigating to Settings");
        // TODO: Implement navigation to Settings scene
    }

    @FXML
    private void handleDonasi(ActionEvent event) {
        System.out.println("Navigating to Donasi Form");
        // TODO: Implement navigation to Donasi Form scene
    }

    @FXML
    private void handleBantu(ActionEvent event) {
        System.out.println("Navigating to Form Distribusi Bantuan");
        // TODO: Implement navigation to Form Distribusi Bantuan scene
    }

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        System.out.println("Logging out...");
        // TODO: Implement actual Firebase logout if client-side auth is used
        // For Admin SDK, there's no "logout" for a client session directly.
        // It's more about clearing local user session and returning to login.
        MainApp.showLoginScene();
    }
}
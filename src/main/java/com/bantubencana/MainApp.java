package com.bantubencana;

import com.bantubencana.util.FirebaseUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("Bantu Bencana App");

        // Initialize Firebase
        FirebaseUtil.initializeFirebase();

        // Load Login Scene initially
        showLoginScene();
    }

    public static void showLoginScene() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource("view/Login.fxml")));
        primaryStage.setScene(new Scene(root, 800, 600)); // Sesuaikan ukuran
        primaryStage.show();
    }

    public static void showRegisterScene() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource("view/Register.fxml")));
        primaryStage.setScene(new Scene(root, 800, 600)); // Sesuaikan ukuran
        primaryStage.show();
    }

    public static void showHomeScene() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource("view/Home.fxml")));
        primaryStage.setScene(new Scene(root, 1000, 700)); // Sesuaikan ukuran
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
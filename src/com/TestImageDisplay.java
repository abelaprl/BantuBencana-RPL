package com;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class TestImageDisplay extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20;");
        
        Label statusLabel = new Label("Pilih gambar untuk test...");
        statusLabel.setStyle("-fx-font-size: 14px;");
        
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        
        Button selectButton = new Button("Pilih Gambar");
        selectButton.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
        selectButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Pilih Gambar");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
            );
            
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                statusLabel.setText("File dipilih: " + selectedFile.getName());
                System.out.println("=== TEST IMAGE DISPLAY ===");
                System.out.println("File dipilih: " + selectedFile.getAbsolutePath());
                
                // Test save image
                String savedPath = ImageManager.saveImage(selectedFile, "test");
                if (savedPath != null) {
                    statusLabel.setText("Gambar disimpan ke: " + savedPath);
                    
                    // Test load image
                    ImageManager.setImageToImageView(imageView, savedPath);
                } else {
                    statusLabel.setText("GAGAL menyimpan gambar!");
                    statusLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                }
            }
        });
        
        Button testExistingButton = new Button("Test Gambar yang Ada");
        testExistingButton.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
        testExistingButton.setOnAction(e -> {
            // Test dengan gambar yang mungkin sudah ada
            File imgDir = new File("img/bencana_photos");
            if (imgDir.exists() && imgDir.isDirectory()) {
                File[] files = imgDir.listFiles((dir, name) -> 
                    name.toLowerCase().endsWith(".jpg") || 
                    name.toLowerCase().endsWith(".png") || 
                    name.toLowerCase().endsWith(".jpeg"));
                
                if (files != null && files.length > 0) {
                    File firstImage = files[0];
                    statusLabel.setText("Test gambar: " + firstImage.getName());
                    ImageManager.setImageToImageView(imageView, firstImage.getAbsolutePath());
                } else {
                    statusLabel.setText("Tidak ada gambar di folder img/bencana_photos");
                }
            } else {
                statusLabel.setText("Folder img/bencana_photos tidak ada");
            }
        });
        
        root.getChildren().addAll(statusLabel, selectButton, testExistingButton, imageView);
        
        Scene scene = new Scene(root, 450, 400);
        primaryStage.setTitle("Test Image Display - Disaster Management");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        System.out.println("=== TEST IMAGE DISPLAY STARTED ===");
        System.out.println("Working directory: " + System.getProperty("user.dir"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

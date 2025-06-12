package com;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ImageManager {
    private static final String PHOTO_DIR = "img/bencana_photos/";
    private static final String VIDEO_DIR = "img/bencana_videos/";
    
    static {
        // Pastikan direktori ada saat class dimuat
        createDirectories();
    }
    
    private static void createDirectories() {
        try {
            Files.createDirectories(Paths.get(PHOTO_DIR));
            Files.createDirectories(Paths.get(VIDEO_DIR));
            System.out.println("✓ Direktori gambar berhasil dibuat/diverifikasi");
        } catch (IOException e) {
            System.err.println("✗ Error membuat direktori: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static String saveImage(File sourceFile, String prefix) {
        if (sourceFile == null || !sourceFile.exists()) {
            System.err.println("✗ File sumber tidak ada: " + sourceFile);
            return null;
        }
        
        try {
            String fileName = prefix + "_" + System.currentTimeMillis() + getFileExtension(sourceFile.getName());
            Path targetPath = Paths.get(PHOTO_DIR + fileName);
            
            System.out.println("📁 Menyimpan gambar:");
            System.out.println("   Dari: " + sourceFile.getAbsolutePath());
            System.out.println("   Ke: " + targetPath.toAbsolutePath());
            
            Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            
            // Verifikasi file tersimpan
            if (Files.exists(targetPath)) {
                System.out.println("✓ Gambar berhasil disimpan: " + fileName);
                return targetPath.toString();
            } else {
                System.err.println("✗ File tidak tersimpan dengan benar");
                return null;
            }
            
        } catch (IOException e) {
            System.err.println("✗ Error menyimpan gambar: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public static List<String> saveMultipleImages(List<File> files, String prefix) {
        List<String> savedPaths = new ArrayList<>();
        
        if (files == null || files.isEmpty()) {
            System.out.println("⚠ Tidak ada file untuk disimpan");
            return savedPaths;
        }
        
        System.out.println("📁 Menyimpan " + files.size() + " gambar...");
        
        for (int i = 0; i < files.size(); i++) {
            String path = saveImage(files.get(i), prefix + "_" + (i + 1));
            if (path != null) {
                savedPaths.add(path);
            }
        }
        
        System.out.println("✓ Berhasil menyimpan " + savedPaths.size() + " dari " + files.size() + " gambar");
        return savedPaths;
    }
    
    public static Image loadImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            System.err.println("✗ Path gambar kosong");
            return null;
        }
        
        try {
            File imageFile = new File(imagePath);
            System.out.println("🖼 Memuat gambar:");
            System.out.println("   Path: " + imagePath);
            System.out.println("   Absolute: " + imageFile.getAbsolutePath());
            System.out.println("   Exists: " + imageFile.exists());
            System.out.println("   Size: " + (imageFile.exists() ? imageFile.length() + " bytes" : "N/A"));
            
            if (!imageFile.exists()) {
                System.err.println("✗ File gambar tidak ditemukan: " + imagePath);
                return null;
            }
            
            // Coba load dengan file URL
            String fileUrl = imageFile.toURI().toString();
            System.out.println("   File URL: " + fileUrl);
            
            Image image = new Image(fileUrl);
            
            if (image.isError()) {
                System.err.println("✗ Error loading image: " + image.getException().getMessage());
                return null;
            }
            
            System.out.println("✓ Gambar berhasil dimuat: " + image.getWidth() + "x" + image.getHeight());
            return image;
            
        } catch (Exception e) {
            System.err.println("✗ Exception saat memuat gambar: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public static void setImageToImageView(ImageView imageView, String imagePath) {
        if (imageView == null) {
            System.err.println("✗ ImageView null");
            return;
        }
        
        System.out.println("🖼 Setting image ke ImageView: " + imagePath);
        
        Image image = loadImage(imagePath);
        if (image != null) {
            imageView.setImage(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);
            System.out.println("✓ Image berhasil di-set ke ImageView");
        } else {
            System.err.println("✗ Gagal set image ke ImageView");
            // Set placeholder atau clear image
            imageView.setImage(null);
        }
    }
    
    public static List<Image> loadMultipleImages(List<String> imagePaths) {
        List<Image> images = new ArrayList<>();
        
        if (imagePaths == null || imagePaths.isEmpty()) {
            System.out.println("⚠ Tidak ada path gambar untuk dimuat");
            return images;
        }
        
        System.out.println("🖼 Memuat " + imagePaths.size() + " gambar...");
        
        for (String path : imagePaths) {
            Image image = loadImage(path);
            if (image != null) {
                images.add(image);
            }
        }
        
        System.out.println("✓ Berhasil memuat " + images.size() + " dari " + imagePaths.size() + " gambar");
        return images;
    }
    
    private static String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return lastDot > 0 ? fileName.substring(lastDot) : ".jpg";
    }
    
    public static boolean deleteImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return false;
        }
        
        try {
            Path path = Paths.get(imagePath);
            boolean deleted = Files.deleteIfExists(path);
            System.out.println(deleted ? "✓ Gambar dihapus: " + imagePath : "⚠ Gambar tidak ditemukan: " + imagePath);
            return deleted;
        } catch (IOException e) {
            System.err.println("✗ Error menghapus gambar: " + e.getMessage());
            return false;
        }
    }
    
    // Method baru untuk memeriksa apakah gambar ada
    public static boolean imageExists(String imageName) {
        if (imageName == null || imageName.trim().isEmpty()) {
            return false;
        }
        
        // Cek apakah ini path lengkap atau hanya nama file
        File imageFile;
        if (imageName.contains("/") || imageName.contains("\\")) {
            // Ini adalah path lengkap
            imageFile = new File(imageName);
        } else {
            // Ini hanya nama file, cari di direktori foto
            imageFile = new File(PHOTO_DIR + imageName);
        }
        
        boolean exists = imageFile.exists();
        System.out.println("🔍 Cek gambar " + imageName + ": " + (exists ? "Ada" : "Tidak ada"));
        return exists;
    }
    
    // Method baru untuk mendapatkan path lengkap gambar
    public static String getImagePath(String imageName) {
        if (imageName == null || imageName.trim().isEmpty()) {
            return null;
        }
        
        // Cek apakah ini path lengkap atau hanya nama file
        if (imageName.contains("/") || imageName.contains("\\")) {
            // Ini adalah path lengkap
            return imageName;
        } else {
            // Ini hanya nama file, kembalikan path lengkap
            return PHOTO_DIR + imageName;
        }
    }
}

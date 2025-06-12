package com;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dashboard {
    private static final String LAPORAN_DATA_FILE = "laporan_data.txt";
    private static List<LaporanBencanaData> laporanBencanaList = new ArrayList<>();
    
    // Legacy support for other components
    public static List<LaporanBencanaData> laporanBencanaDatabase = new ArrayList<>();
    public static List<FeedbackData> feedbackDatabase = new ArrayList<>();
    public static List<DonationData> donationDatabase = new ArrayList<>();
    
    // Flag to control file operations (for testing)
    private static boolean enableFileOperations = true;
    
    static {
        loadLaporanBencanaFromFile();
        // Sync with legacy database
        laporanBencanaDatabase = new ArrayList<>(laporanBencanaList);
        
        // Initialize dummy data if needed
        initializeDummyData();
    }

    // Method to enable/disable file operations (for testing)
    public static void setFileOperationsEnabled(boolean enabled) {
        enableFileOperations = enabled;
    }

    // Method to clear all data (for testing)
    public static void clearAllData() {
        laporanBencanaList.clear();
        laporanBencanaDatabase.clear();
        feedbackDatabase.clear();
        donationDatabase.clear();
    }

    // New methods for LaporanBencanaData
    public static void addLaporanBencana(LaporanBencanaData laporan) {
        laporanBencanaList.add(laporan);
        laporanBencanaDatabase.add(laporan); // Sync with legacy
        if (enableFileOperations) {
            saveLaporanBencanaToFile();
        }
        System.out.println("Laporan bencana baru ditambahkan ke database!");
    }

    public static List<LaporanBencanaData> getAllLaporanBencana() {
        return new ArrayList<>(laporanBencanaList);
    }
    
    public static List<LaporanBencanaData> getLaporanBencanaByUser(String userEmail) {
        return laporanBencanaList.stream()
            .filter(laporan -> laporan.getCreatedBy().equals(userEmail))
            .collect(Collectors.toList());
    }

    public static void removeLaporanBencana(LaporanBencanaData laporan) {
        laporanBencanaList.remove(laporan);
        laporanBencanaDatabase.remove(laporan); // Sync with legacy
        if (enableFileOperations) {
            saveLaporanBencanaToFile();
        }
        System.out.println("Laporan bencana berhasil dihapus dari database!");
    }

    // Legacy methods for backward compatibility
    public static void removeLaporanBencanaFromDatabase(int index) {
        if (index >= 0 && index < laporanBencanaList.size()) {
            laporanBencanaList.remove(index);
            if (index < laporanBencanaDatabase.size()) {
                laporanBencanaDatabase.remove(index);
            }
            if (enableFileOperations) {
                saveLaporanBencanaToFile();
            }
            System.out.println("Laporan bencana berhasil dihapus dari database!");
        }
    }

    // Feedback methods
    public static void addFeedback(FeedbackData feedback) {
        feedbackDatabase.add(feedback);
        System.out.println("Feedback baru ditambahkan ke database!");
    }

    public static List<FeedbackData> getAllFeedback() {
        return new ArrayList<>(feedbackDatabase);
    }

    public static void removeFeedbackFromDatabase(int index) {
        if (index >= 0 && index < feedbackDatabase.size()) {
            feedbackDatabase.remove(index);
            System.out.println("Feedback berhasil dihapus dari database!");
        }
    }

    // Donation methods
    public static void addDonation(DonationData donation) {
        donationDatabase.add(donation);
        System.out.println("Donasi baru ditambahkan ke database!");
    }

    public static List<DonationData> getAllDonations() {
        return new ArrayList<>(donationDatabase);
    }

    public static void removeDonationFromDatabase(int index) {
        if (index >= 0 && index < donationDatabase.size()) {
            donationDatabase.remove(index);
            System.out.println("Donasi berhasil dihapus dari database!");
        }
    }

    // Initialize dummy data
    public static void initializeDummyData() {
        if (donationDatabase.isEmpty()) {
            // Add some dummy donations
            donationDatabase.add(new DonationData("John Doe", "john@example.com", 100000, "Bantuan untuk korban banjir"));
            donationDatabase.add(new DonationData("Jane Smith", "jane@example.com", 250000, "Donasi untuk korban gempa"));
        }
    }

    // File operations
    private static void saveLaporanBencanaToFile() {
        if (!enableFileOperations) return;
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(LAPORAN_DATA_FILE))) {
            for (LaporanBencanaData laporan : laporanBencanaList) {
                writer.println(laporan.toFileString());
            }
            System.out.println("DEBUG: Data laporan bencana berhasil disimpan ke file: " + LAPORAN_DATA_FILE);
        } catch (IOException e) {
            System.err.println("ERROR: Gagal menyimpan data laporan bencana ke file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void loadLaporanBencanaFromFile() {
        if (!enableFileOperations) return;
        
        File file = new File(LAPORAN_DATA_FILE);
        if (!file.exists()) {
            System.out.println("DEBUG: File laporan bencana tidak ditemukan, akan dibuat baru");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            laporanBencanaList.clear();
            
            while ((line = reader.readLine()) != null) {
                LaporanBencanaData laporan = LaporanBencanaData.fromFileString(line);
                if (laporan != null) {
                    laporanBencanaList.add(laporan);
                }
            }
            System.out.println("DEBUG: " + laporanBencanaList.size() + " laporan bencana berhasil dimuat dari file");
        } catch (IOException e) {
            System.err.println("ERROR: Gagal memuat data laporan bencana dari file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to reload data from file (for manual refresh)
    public static void reloadFromFile() {
        if (enableFileOperations) {
            loadLaporanBencanaFromFile();
            laporanBencanaDatabase = new ArrayList<>(laporanBencanaList);
        }
    }

    // Method to force save to file
    public static void saveToFile() {
        if (enableFileOperations) {
            saveLaporanBencanaToFile();
        }
    }
}

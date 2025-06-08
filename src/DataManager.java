// File: src/DataManager.java
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String FEEDBACK_FILE = "feedback_data.txt";
    private static final String LAPORAN_FILE = "laporan_data.txt";
    private static final String USER_FILE = "users.txt";
    private static final String DONASI_FILE = "donasi_data.txt"; // BARU: Tambahkan ini untuk file donasi

    // Method untuk menyimpan data feedback ke file
    public static void saveFeedbackData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FEEDBACK_FILE))) {
            oos.writeObject(Dashboard.feedbackDatabase);
            System.out.println("DEBUG: Data feedback berhasil disimpan ke file: " + FEEDBACK_FILE);
        } catch (IOException e) {
            System.err.println("Error saat menyimpan data feedback: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method untuk memuat data feedback dari file
    @SuppressWarnings("unchecked")
    public static void loadFeedbackData() {
        File file = new File(FEEDBACK_FILE);
        if (!file.exists()) {
            System.out.println("DEBUG: File data feedback tidak ditemukan. Membuat database baru.");
            Dashboard.feedbackDatabase = new ArrayList<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Dashboard.feedbackDatabase = (List<FeedbackData>) ois.readObject();
            System.out.println("DEBUG: Data feedback berhasil dimuat dari file. Total: " + Dashboard.feedbackDatabase.size());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error saat memuat data feedback: " + e.getMessage());
            Dashboard.feedbackDatabase = new ArrayList<>();
        }
    }

    // Method untuk menyimpan data laporan bencana ke file
    public static void saveLaporanBencanaData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(LAPORAN_FILE))) {
            oos.writeObject(Dashboard.laporanBencanaDatabase);
            System.out.println("DEBUG: Data laporan bencana berhasil disimpan ke file: " + LAPORAN_FILE);
        } catch (IOException e) {
            System.err.println("Error saat menyimpan data laporan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method untuk memuat data laporan bencana dari file
    @SuppressWarnings("unchecked")
    public static void loadLaporanBencanaData() {
        File file = new File(LAPORAN_FILE);
        if (!file.exists()) {
            System.out.println("DEBUG: File data laporan bencana tidak ditemukan. Membuat database baru.");
            Dashboard.laporanBencanaDatabase = new ArrayList<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Dashboard.laporanBencanaDatabase = (List<LaporanBencanaData>) ois.readObject();
            System.out.println("DEBUG: Data laporan bencana berhasil dimuat dari file. Total: " + Dashboard.laporanBencanaDatabase.size());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error saat memuat data laporan: " + e.getMessage());
            Dashboard.laporanBencanaDatabase = new ArrayList<>();
        }
    }

    // BARU: Method untuk menyimpan data donasi ke file
    public static void saveDonationData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DONASI_FILE))) {
            oos.writeObject(Dashboard.donationDatabase); // Mengakses database donasi dari Dashboard
            System.out.println("DEBUG: Data donasi berhasil disimpan ke file: " + DONASI_FILE);
        } catch (IOException e) {
            System.err.println("Error saat menyimpan data donasi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // BARU: Method untuk memuat data donasi dari file
    @SuppressWarnings("unchecked")
    public static void loadDonationData() {
        File file = new File(DONASI_FILE);
        if (!file.exists() || file.length() == 0) { // Cek file ada dan tidak kosong
            System.out.println("DEBUG: File data donasi tidak ditemukan atau kosong. Membuat database baru.");
            Dashboard.donationDatabase = new ArrayList<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Dashboard.donationDatabase = (List<DonationData>) ois.readObject();
            System.out.println("DEBUG: Data donasi berhasil dimuat dari file. Total: " + Dashboard.donationDatabase.size());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error saat memuat data donasi: " + e.getMessage());
            Dashboard.donationDatabase = new ArrayList<>();
        }
    }

    // Metode ini untuk menyimpan data User
    public static void saveUserData(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(users);
            System.out.println("DEBUG: Data user berhasil disimpan ke file: " + USER_FILE);
        } catch (IOException e) {
            System.err.println("Error saat menyimpan data user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Metode ini untuk memuat data User
    @SuppressWarnings("unchecked")
    public static List<User> loadUserData() {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            System.out.println("DEBUG: File data user tidak ditemukan. Menginisialisasi list user kosong.");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<User> users = (List<User>) ois.readObject();
            System.out.println("DEBUG: Data user berhasil dimuat dari file. Total: " + users.size());
            return users;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error saat memuat data user: " + e.getMessage());
            return new ArrayList<>(); // Kembalikan list kosong jika ada error
        }
    }

    // Method untuk menghapus file data (bermanfaat untuk testing)
    public static void deleteDataFiles() {
        File feedbackFile = new File(FEEDBACK_FILE);
        if (feedbackFile.exists()) {
            feedbackFile.delete();
            System.out.println("DEBUG: File feedback data berhasil dihapus.");
        }

        File laporanFile = new File(LAPORAN_FILE);
        if (laporanFile.exists()) {
            laporanFile.delete();
            System.out.println("DEBUG: File laporan data berhasil dihapus.");
        }

        File userFile = new File(USER_FILE);
        if (userFile.exists()) {
            userFile.delete();
            System.out.println("DEBUG: File user data berhasil dihapus.");
        }

        File donasiFile = new File(DONASI_FILE); // BARU: Tambahkan ini
        if (donasiFile.exists()) {
            donasiFile.delete();
            System.out.println("DEBUG: File donasi data berhasil dihapus.");
        }
    }
}
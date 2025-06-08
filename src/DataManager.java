import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String FEEDBACK_FILE = "feedback_data.txt";
    private static final String LAPORAN_FILE = "laporan_data.txt";
    
    // Method untuk menyimpan data feedback ke file
    public static void saveFeedbackData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FEEDBACK_FILE))) {
            oos.writeObject(Dashboard.feedbackDatabase);
            System.out.println("Data feedback berhasil disimpan ke file: " + FEEDBACK_FILE);
        } catch (IOException e) {
            System.err.println("Error saat menyimpan data feedback: " + e.getMessage());
            e.printStackTrace(); // Tambahkan stack trace untuk debugging
        }
    }
    
    // Method untuk memuat data feedback dari file
    @SuppressWarnings("unchecked")
    public static void loadFeedbackData() {
        File file = new File(FEEDBACK_FILE);
        if (!file.exists()) {
            System.out.println("File data feedback tidak ditemukan. Membuat database baru.");
            Dashboard.feedbackDatabase = new ArrayList<>();
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Dashboard.feedbackDatabase = (List<FeedbackData>) ois.readObject();
            System.out.println("Data feedback berhasil dimuat dari file. Total: " + Dashboard.feedbackDatabase.size());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error saat memuat data feedback: " + e.getMessage());
            Dashboard.feedbackDatabase = new ArrayList<>();
        }
    }
    
    // Method untuk menyimpan data laporan bencana ke file
    public static void saveLaporanBencanaData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(LAPORAN_FILE))) {
            oos.writeObject(Dashboard.laporanBencanaDatabase);
            System.out.println("Data laporan berhasil disimpan ke file: " + LAPORAN_FILE);
        } catch (IOException e) {
            System.err.println("Error saat menyimpan data laporan: " + e.getMessage());
            e.printStackTrace(); // Tambahkan stack trace untuk debugging
        }
    }
    
    // Method untuk memuat data laporan bencana dari file
    @SuppressWarnings("unchecked")
    public static void loadLaporanBencanaData() {
        File file = new File(LAPORAN_FILE);
        if (!file.exists()) {
            System.out.println("File data laporan tidak ditemukan. Membuat database baru.");
            Dashboard.laporanBencanaDatabase = new ArrayList<>();
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Dashboard.laporanBencanaDatabase = (List<LaporanBencanaData>) ois.readObject();
            System.out.println("Data laporan berhasil dimuat dari file. Total: " + Dashboard.laporanBencanaDatabase.size());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error saat memuat data laporan: " + e.getMessage());
            Dashboard.laporanBencanaDatabase = new ArrayList<>();
        }
    }
    
    // Method untuk menghapus file data
    public static void deleteDataFiles() {
        File feedbackFile = new File(FEEDBACK_FILE);
        if (feedbackFile.exists()) {
            feedbackFile.delete();
            System.out.println("File feedback data berhasil dihapus.");
        }
        
        File laporanFile = new File(LAPORAN_FILE);
        if (laporanFile.exists()) {
            laporanFile.delete();
            System.out.println("File laporan data berhasil dihapus.");
        }
    }
}
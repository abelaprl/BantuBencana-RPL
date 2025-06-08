import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataManager {
    private static final String FEEDBACK_FILE = "feedback_data.txt";
    private static final String LAPORAN_FILE = "laporan_data.txt";
    
    // Simpan data feedback ke file
    public static void saveFeedbackData(List<FeedbackData> feedbackList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FEEDBACK_FILE))) {
            for (FeedbackData feedback : feedbackList) {
                writer.println("=== FEEDBACK START ===");
                writer.println("LAPORAN_BENCANA:" + (feedback.getLaporanBencana() != null ? feedback.getLaporanBencana() : ""));
                writer.println("JENIS_BENCANA:" + (feedback.getJenisBencana() != null ? feedback.getJenisBencana() : ""));
                writer.println("LOKASI_LAPORAN:" + (feedback.getLokasiLaporan() != null ? feedback.getLokasiLaporan() : ""));
                writer.println("RATING_PENANGANAN:" + feedback.getRatingPenanganan());
                writer.println("RATING_RELAWAN:" + feedback.getRatingKinerjaRelawan());
                writer.println("RATING_DONASI:" + feedback.getRatingAlokasiDonasi());
                writer.println("RATING_PENGALAMAN:" + feedback.getRatingPengalamanBuruk());
                writer.println("FEEDBACK_PENANGANAN:" + (feedback.getFeedbackPenanganan() != null ? feedback.getFeedbackPenanganan().replace("\n", "\\n") : ""));
                writer.println("FEEDBACK_RELAWAN:" + (feedback.getFeedbackKinerjaRelawan() != null ? feedback.getFeedbackKinerjaRelawan().replace("\n", "\\n") : ""));
                writer.println("FEEDBACK_DONASI:" + (feedback.getFeedbackAlokasiDonasi() != null ? feedback.getFeedbackAlokasiDonasi().replace("\n", "\\n") : ""));
                writer.println("FEEDBACK_PENGALAMAN:" + (feedback.getFeedbackPengalamanBuruk() != null ? feedback.getFeedbackPengalamanBuruk().replace("\n", "\\n") : ""));
                writer.println("EVALUASI_TAMBAHAN:" + (feedback.getEvaluasiTambahan() != null ? feedback.getEvaluasiTambahan().replace("\n", "\\n") : ""));
                writer.println("MEDIA_PENDUKUNG:" + (feedback.getMediaPendukung() != null ? feedback.getMediaPendukung().getAbsolutePath() : ""));
                writer.println("=== FEEDBACK END ===");
            }
            System.out.println("Data feedback berhasil disimpan ke file: " + FEEDBACK_FILE);
        } catch (IOException e) {
            System.err.println("Error menyimpan data feedback: " + e.getMessage());
        }
    }
    
    // Muat data feedback dari file
    public static List<FeedbackData> loadFeedbackData() {
        List<FeedbackData> feedbackList = new ArrayList<>();
        File file = new File(FEEDBACK_FILE);
        
        if (!file.exists()) {
            System.out.println("File feedback tidak ditemukan. Memulai dengan database kosong.");
            return feedbackList;
        }
        
        try (Scanner scanner = new Scanner(file)) {
            FeedbackData currentFeedback = null;
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                
                if (line.equals("=== FEEDBACK START ===")) {
                    currentFeedback = new FeedbackData();
                } else if (line.equals("=== FEEDBACK END ===")) {
                    if (currentFeedback != null) {
                        feedbackList.add(currentFeedback);
                        currentFeedback = null;
                    }
                } else if (currentFeedback != null && line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        String key = parts[0];
                        String value = parts[1];
                        
                        switch (key) {
                            case "LAPORAN_BENCANA":
                                currentFeedback.setLaporanBencana(value.isEmpty() ? null : value);
                                break;
                            case "JENIS_BENCANA":
                                currentFeedback.setJenisBencana(value.isEmpty() ? null : value);
                                break;
                            case "LOKASI_LAPORAN":
                                currentFeedback.setLokasiLaporan(value.isEmpty() ? null : value);
                                break;
                            case "RATING_PENANGANAN":
                                currentFeedback.setRatingPenanganan(Integer.parseInt(value));
                                break;
                            case "RATING_RELAWAN":
                                currentFeedback.setRatingKinerjaRelawan(Integer.parseInt(value));
                                break;
                            case "RATING_DONASI":
                                currentFeedback.setRatingAlokasiDonasi(Integer.parseInt(value));
                                break;
                            case "RATING_PENGALAMAN":
                                currentFeedback.setRatingPengalamanBuruk(Integer.parseInt(value));
                                break;
                            case "FEEDBACK_PENANGANAN":
                                currentFeedback.setFeedbackPenanganan(value.replace("\\n", "\n"));
                                break;
                            case "FEEDBACK_RELAWAN":
                                currentFeedback.setFeedbackKinerjaRelawan(value.replace("\\n", "\n"));
                                break;
                            case "FEEDBACK_DONASI":
                                currentFeedback.setFeedbackAlokasiDonasi(value.replace("\\n", "\n"));
                                break;
                            case "FEEDBACK_PENGALAMAN":
                                currentFeedback.setFeedbackPengalamanBuruk(value.replace("\\n", "\n"));
                                break;
                            case "EVALUASI_TAMBAHAN":
                                currentFeedback.setEvaluasiTambahan(value.replace("\\n", "\n"));
                                break;
                            case "MEDIA_PENDUKUNG":
                                if (!value.isEmpty()) {
                                    currentFeedback.setMediaPendukung(new File(value));
                                }
                                break;
                        }
                    }
                }
            }
            System.out.println("Data feedback berhasil dimuat dari file. Total: " + feedbackList.size());
        } catch (IOException e) {
            System.err.println("Error memuat data feedback: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing data feedback: " + e.getMessage());
        }
        
        return feedbackList;
    }
    
    // Simpan data laporan bencana ke file
    public static void saveLaporanData(List<LaporanBencanaData> laporanList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LAPORAN_FILE))) {
            for (LaporanBencanaData laporan : laporanList) {
                writer.println("=== LAPORAN START ===");
                writer.println("JENIS_BENCANA:" + laporan.getJenisBencana());
                writer.println("LOKASI_BENCANA:" + laporan.getLokasiBencana());
                writer.println("DESKRIPSI:" + laporan.getDeskripsi().replace("\n", "\\n"));
                writer.println("TINGKAT_KEPARAHAN:" + laporan.getTingkatKeparahan());
                writer.println("JUMLAH_KORBAN:" + laporan.getJumlahKorban());
                writer.println("TANGGAL_LAPORAN:" + laporan.getTanggalLaporan());
                writer.println("=== LAPORAN END ===");
            }
            System.out.println("Data laporan berhasil disimpan ke file: " + LAPORAN_FILE);
        } catch (IOException e) {
            System.err.println("Error menyimpan data laporan: " + e.getMessage());
        }
    }
    
    // Muat data laporan bencana dari file
    public static List<LaporanBencanaData> loadLaporanData() {
        List<LaporanBencanaData> laporanList = new ArrayList<>();
        File file = new File(LAPORAN_FILE);
        
        if (!file.exists()) {
            System.out.println("File laporan tidak ditemukan. Memulai dengan database kosong.");
            return laporanList;
        }
        
        try (Scanner scanner = new Scanner(file)) {
            LaporanBencanaData currentLaporan = null;
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                
                if (line.equals("=== LAPORAN START ===")) {
                    currentLaporan = new LaporanBencanaData();
                } else if (line.equals("=== LAPORAN END ===")) {
                    if (currentLaporan != null) {
                        laporanList.add(currentLaporan);
                        currentLaporan = null;
                    }
                } else if (currentLaporan != null && line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        String key = parts[0];
                        String value = parts[1];
                        
                        switch (key) {
                            case "JENIS_BENCANA":
                                currentLaporan.setJenisBencana(value);
                                break;
                            case "LOKASI_BENCANA":
                                currentLaporan.setLokasiBencana(value);
                                break;
                            case "DESKRIPSI":
                                currentLaporan.setDeskripsi(value.replace("\\n", "\n"));
                                break;
                            case "TINGKAT_KEPARAHAN":
                                currentLaporan.setTingkatKeparahan(value);
                                break;
                            case "JUMLAH_KORBAN":
                                currentLaporan.setJumlahKorban(value);
                                break;
                            case "TANGGAL_LAPORAN":
                                currentLaporan.setTanggalLaporan(value);
                                break;
                        }
                    }
                }
            }
            System.out.println("Data laporan berhasil dimuat dari file. Total: " + laporanList.size());
        } catch (IOException e) {
            System.err.println("Error memuat data laporan: " + e.getMessage());
        }
        
        return laporanList;
    }
    
    // Hapus semua file data
    public static void clearAllData() {
        File feedbackFile = new File(FEEDBACK_FILE);
        File laporanFile = new File(LAPORAN_FILE);
        
        if (feedbackFile.exists()) {
            feedbackFile.delete();
            System.out.println("File feedback dihapus.");
        }
        
        if (laporanFile.exists()) {
            laporanFile.delete();
            System.out.println("File laporan dihapus.");
        }
    }
}
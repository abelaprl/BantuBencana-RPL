import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FeedbackData {
    private String laporanBencana;
    private String jenisBencana;
    private String lokasiLaporan;
    
    // Rating untuk setiap pertanyaan (1-5 bintang)
    private int ratingPenanganan;
    private int ratingKinerjaRelawan;
    private int ratingAlokasiDonasi;
    private int ratingPengalamanBuruk;
    
    // Feedback text untuk setiap pertanyaan
    private String feedbackPenanganan;
    private String feedbackKinerjaRelawan;
    private String feedbackAlokasiDonasi;
    private String feedbackPengalamanBuruk;
    
    // Evaluasi tambahan
    private String evaluasiTambahan;
    
    // File media pendukung
    private File mediaPendukung;
    
    // Constructor
    public FeedbackData() {
        this.ratingPenanganan = 0;
        this.ratingKinerjaRelawan = 0;
        this.ratingAlokasiDonasi = 0;
        this.ratingPengalamanBuruk = 0;
    }
    
    // Getters and Setters
    public String getLaporanBencana() { return laporanBencana; }
    public void setLaporanBencana(String laporanBencana) { this.laporanBencana = laporanBencana; }
    
    public String getJenisBencana() { return jenisBencana; }
    public void setJenisBencana(String jenisBencana) { this.jenisBencana = jenisBencana; }
    
    public String getLokasiLaporan() { return lokasiLaporan; }
    public void setLokasiLaporan(String lokasiLaporan) { this.lokasiLaporan = lokasiLaporan; }
    
    public int getRatingPenanganan() { return ratingPenanganan; }
    public void setRatingPenanganan(int ratingPenanganan) { this.ratingPenanganan = ratingPenanganan; }
    
    public int getRatingKinerjaRelawan() { return ratingKinerjaRelawan; }
    public void setRatingKinerjaRelawan(int ratingKinerjaRelawan) { this.ratingKinerjaRelawan = ratingKinerjaRelawan; }
    
    public int getRatingAlokasiDonasi() { return ratingAlokasiDonasi; }
    public void setRatingAlokasiDonasi(int ratingAlokasiDonasi) { this.ratingAlokasiDonasi = ratingAlokasiDonasi; }
    
    public int getRatingPengalamanBuruk() { return ratingPengalamanBuruk; }
    public void setRatingPengalamanBuruk(int ratingPengalamanBuruk) { this.ratingPengalamanBuruk = ratingPengalamanBuruk; }
    
    public String getFeedbackPenanganan() { return feedbackPenanganan; }
    public void setFeedbackPenanganan(String feedbackPenanganan) { this.feedbackPenanganan = feedbackPenanganan; }
    
    public String getFeedbackKinerjaRelawan() { return feedbackKinerjaRelawan; }
    public void setFeedbackKinerjaRelawan(String feedbackKinerjaRelawan) { this.feedbackKinerjaRelawan = feedbackKinerjaRelawan; }
    
    public String getFeedbackAlokasiDonasi() { return feedbackAlokasiDonasi; }
    public void setFeedbackAlokasiDonasi(String feedbackAlokasiDonasi) { this.feedbackAlokasiDonasi = feedbackAlokasiDonasi; }
    
    public String getFeedbackPengalamanBuruk() { return feedbackPengalamanBuruk; }
    public void setFeedbackPengalamanBuruk(String feedbackPengalamanBuruk) { this.feedbackPengalamanBuruk = feedbackPengalamanBuruk; }
    
    public String getEvaluasiTambahan() { return evaluasiTambahan; }
    public void setEvaluasiTambahan(String evaluasiTambahan) { this.evaluasiTambahan = evaluasiTambahan; }
    
    public File getMediaPendukung() { return mediaPendukung; }
    public void setMediaPendukung(File mediaPendukung) { this.mediaPendukung = mediaPendukung; }
    
    @Override
    public String toString() {
        return "FeedbackData{" +
                "laporanBencana='" + laporanBencana + '\'' +
                ", jenisBencana='" + jenisBencana + '\'' +
                ", lokasiLaporan='" + lokasiLaporan + '\'' +
                ", ratingPenanganan=" + ratingPenanganan +
                ", ratingKinerjaRelawan=" + ratingKinerjaRelawan +
                ", ratingAlokasiDonasi=" + ratingAlokasiDonasi +
                ", ratingPengalamanBuruk=" + ratingPengalamanBuruk +
                ", mediaPendukung=" + (mediaPendukung != null ? mediaPendukung.getName() : "null") +
                '}';
    }
}
import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FeedbackData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String laporanBencana;
    private String jenisBencana;
    private String lokasiLaporan;
    private String userEmail = "user@example.com";
    private int ratingPenanganan;
    private int ratingKinerjaRelawan;
    private int ratingAlokasiDonasi;
    private int ratingPengalamanBuruk;
    private String timestamp;
    
    // Menggunakan String untuk menyimpan path file, bukan File object
    private String mediaPendukungPath;
    
    private String feedbackPenanganan;
    private String feedbackKinerjaRelawan;
    private String feedbackAlokasiDonasi;
    private String feedbackPengalamanBuruk;
    private String evaluasiTambahan;
    
    public FeedbackData() {
        this.timestamp = LocalDateTime.now().toString();
    }
    
    public String getLaporanBencana() {
        return laporanBencana;
    }
    
    public void setLaporanBencana(String laporanBencana) {
        this.laporanBencana = laporanBencana;
    }
    
    public String getJenisBencana() {
        return jenisBencana;
    }
    
    public void setJenisBencana(String jenisBencana) {
        this.jenisBencana = jenisBencana;
    }
    
    public String getLokasiLaporan() {
        return lokasiLaporan;
    }
    
    public void setLokasiLaporan(String lokasiLaporan) {
        this.lokasiLaporan = lokasiLaporan;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public int getRatingPenanganan() {
        return ratingPenanganan;
    }
    
    public void setRatingPenanganan(int ratingPenanganan) {
        this.ratingPenanganan = ratingPenanganan;
    }
    
    public int getRatingKinerjaRelawan() {
        return ratingKinerjaRelawan;
    }
    
    public void setRatingKinerjaRelawan(int ratingKinerjaRelawan) {
        this.ratingKinerjaRelawan = ratingKinerjaRelawan;
    }
    
    public int getRatingAlokasiDonasi() {
        return ratingAlokasiDonasi;
    }
    
    public void setRatingAlokasiDonasi(int ratingAlokasiDonasi) {
        this.ratingAlokasiDonasi = ratingAlokasiDonasi;
    }
    
    public int getRatingPengalamanBuruk() {
        return ratingPengalamanBuruk;
    }
    
    public void setRatingPengalamanBuruk(int ratingPengalamanBuruk) {
        this.ratingPengalamanBuruk = ratingPengalamanBuruk;
    }
    
    public double getAverageRating() {
        int totalRatings = 0;
        int countRatings = 0;
        
        if (ratingPenanganan > 0) {
            totalRatings += ratingPenanganan;
            countRatings++;
        }
        
        if (ratingKinerjaRelawan > 0) {
            totalRatings += ratingKinerjaRelawan;
            countRatings++;
        }
        
        if (ratingAlokasiDonasi > 0) {
            totalRatings += ratingAlokasiDonasi;
            countRatings++;
        }
        
        if (ratingPengalamanBuruk > 0) {
            totalRatings += ratingPengalamanBuruk;
            countRatings++;
        }
        
        if (countRatings == 0) {
            return 0;
        }
        
        return (double) totalRatings / countRatings;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    // Mengubah getter dan setter untuk mediaPendukung
    public File getMediaPendukung() {
        if (mediaPendukungPath != null && !mediaPendukungPath.isEmpty()) {
            return new File(mediaPendukungPath);
        }
        return null;
    }
    
    public void setMediaPendukung(File mediaPendukung) {
        if (mediaPendukung != null) {
            this.mediaPendukungPath = mediaPendukung.getAbsolutePath();
        } else {
            this.mediaPendukungPath = null;
        }
    }
    
    // Getter dan setter untuk path file
    public String getMediaPendukungPath() {
        return mediaPendukungPath;
    }
    
    public void setMediaPendukungPath(String mediaPendukungPath) {
        this.mediaPendukungPath = mediaPendukungPath;
    }
    
    public String getFeedbackPenanganan() {
        return feedbackPenanganan;
    }
    
    public void setFeedbackPenanganan(String feedbackPenanganan) {
        this.feedbackPenanganan = feedbackPenanganan;
    }
    
    public String getFeedbackKinerjaRelawan() {
        return feedbackKinerjaRelawan;
    }
    
    public void setFeedbackKinerjaRelawan(String feedbackKinerjaRelawan) {
        this.feedbackKinerjaRelawan = feedbackKinerjaRelawan;
    }
    
    public String getFeedbackAlokasiDonasi() {
        return feedbackAlokasiDonasi;
    }
    
    public void setFeedbackAlokasiDonasi(String feedbackAlokasiDonasi) {
        this.feedbackAlokasiDonasi = feedbackAlokasiDonasi;
    }
    
    public String getFeedbackPengalamanBuruk() {
        return feedbackPengalamanBuruk;
    }
    
    public void setFeedbackPengalamanBuruk(String feedbackPengalamanBuruk) {
        this.feedbackPengalamanBuruk = feedbackPengalamanBuruk;
    }
    
    public String getEvaluasiTambahan() {
        return evaluasiTambahan;
    }
    
    public void setEvaluasiTambahan(String evaluasiTambahan) {
        this.evaluasiTambahan = evaluasiTambahan;
    }
    
    public boolean isValid() {
        return laporanBencana != null && !laporanBencana.trim().isEmpty() &&
               jenisBencana != null && !jenisBencana.trim().isEmpty() &&
               lokasiLaporan != null && !lokasiLaporan.trim().isEmpty() &&
               userEmail != null && !userEmail.trim().isEmpty();
    }
    
    @Override
    public String toString() {
        return "FeedbackData{" +
                "laporanBencana='" + laporanBencana + '\'' +
                ", jenisBencana='" + jenisBencana + '\'' +
                ", lokasiLaporan='" + lokasiLaporan + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", ratingPenanganan=" + ratingPenanganan +
                ", ratingKinerjaRelawan=" + ratingKinerjaRelawan +
                ", ratingAlokasiDonasi=" + ratingAlokasiDonasi +
                ", ratingPengalamanBuruk=" + ratingPengalamanBuruk +
                ", averageRating=" + getAverageRating() +
                ", timestamp='" + timestamp + '\'' +
                ", mediaPendukung=" + (mediaPendukungPath != null ? new File(mediaPendukungPath).getName() : null) +
                '}';
    }
}
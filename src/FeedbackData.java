package com;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FeedbackData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String laporanBencana;
    private String jenisBencana;
    private String lokasiLaporan;
    private String userEmail;
    private int ratingPenanganan;
    private int ratingKinerjaRelawan;
    private int ratingAlokasiDonasi;
    private int ratingPengalamanBuruk;
    private String timestamp;

    private String mediaPendukungPath;

    private String feedbackPenanganan;
    private String feedbackKinerjaRelawan;
    private String feedbackAlokasiDonasi;
    private String feedbackPengalamanBuruk;
    private String evaluasiTambahan;

    // Default constructor
    public FeedbackData() {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Constructor dengan semua parameter untuk test
    public FeedbackData(String userEmail, String laporanBencana, String jenisBencana, String lokasiLaporan,
                       int ratingPenanganan, int ratingKinerjaRelawan, int ratingAlokasiDonasi, int ratingPengalamanBuruk,
                       String feedbackPenanganan, String feedbackKinerjaRelawan, String feedbackAlokasiDonasi,
                       String feedbackPengalamanBuruk, String evaluasiTambahan) {
        this.userEmail = userEmail;
        this.laporanBencana = laporanBencana;
        this.jenisBencana = jenisBencana;
        this.lokasiLaporan = lokasiLaporan;
        this.ratingPenanganan = ratingPenanganan;
        this.ratingKinerjaRelawan = ratingKinerjaRelawan;
        this.ratingAlokasiDonasi = ratingAlokasiDonasi;
        this.ratingPengalamanBuruk = ratingPengalamanBuruk;
        this.feedbackPenanganan = feedbackPenanganan;
        this.feedbackKinerjaRelawan = feedbackKinerjaRelawan;
        this.feedbackAlokasiDonasi = feedbackAlokasiDonasi;
        this.feedbackPengalamanBuruk = feedbackPengalamanBuruk;
        this.evaluasiTambahan = evaluasiTambahan;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Constructor dengan parameter minimal
    public FeedbackData(String userEmail, String laporanBencana, String jenisBencana, String lokasiLaporan) {
        this.userEmail = userEmail;
        this.laporanBencana = laporanBencana;
        this.jenisBencana = jenisBencana;
        this.lokasiLaporan = lokasiLaporan;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Getters and Setters
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

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

    public double getAverageRating() {
        int totalRating = ratingPenanganan + ratingKinerjaRelawan + ratingAlokasiDonasi + ratingPengalamanBuruk;
        int count = 0;
        if (ratingPenanganan > 0) count++;
        if (ratingKinerjaRelawan > 0) count++;
        if (ratingAlokasiDonasi > 0) count++;
        if (ratingPengalamanBuruk > 0) count++;

        return count > 0 ? (double) totalRating / count : 0.0;
    }

    public boolean isValid() {
        // Validasi minimal: laporan bencana, jenis bencana, lokasi, dan email tidak boleh kosong
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
                ", averageRating=" + String.format("%.2f", getAverageRating()) +
                ", timestamp='" + timestamp + '\'' +
                ", mediaPendukung=" + (mediaPendukungPath != null ? new File(mediaPendukungPath).getName() : "none") +
                '}';
    }
}
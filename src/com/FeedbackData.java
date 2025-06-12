package com;

import java.io.Serializable;

public class FeedbackData implements Serializable {
    private String userEmail;
    private String laporanBencana;
    private String jenisBencana;
    private String lokasiLaporan;
    private int ratingPenanganan;
    private int ratingKinerjaRelawan;
    private int ratingAlokasiDonasi;
    private int ratingPengalamanBuruk;
    private String feedbackPenanganan;
    private String feedbackKinerjaRelawan;
    private String feedbackAlokasiDonasi;
    private String feedbackPengalamanBuruk;
    private String evaluasiTambahan;
    private long timestamp;

    // Media pendukung path (legacy support)
    private String mediaPendukungPath;

    public FeedbackData() {
        this.timestamp = System.currentTimeMillis();
        this.ratingPenanganan = 0;
        this.ratingKinerjaRelawan = 0;
        this.ratingAlokasiDonasi = 0;
        this.ratingPengalamanBuruk = 0;
    }

    // Getters
    public String getUserEmail() {
        return userEmail;
    }

    public String getLaporanBencana() {
        return laporanBencana;
    }

    public String getJenisBencana() {
        return jenisBencana;
    }

    public String getLokasiLaporan() {
        return lokasiLaporan;
    }

    public int getRatingPenanganan() {
        return ratingPenanganan;
    }

    public int getRatingKinerjaRelawan() {
        return ratingKinerjaRelawan;
    }

    public int getRatingAlokasiDonasi() {
        return ratingAlokasiDonasi;
    }

    public int getRatingPengalamanBuruk() {
        return ratingPengalamanBuruk;
    }

    public String getFeedbackPenanganan() {
        return feedbackPenanganan;
    }

    public String getFeedbackKinerjaRelawan() {
        return feedbackKinerjaRelawan;
    }

    public String getFeedbackAlokasiDonasi() {
        return feedbackAlokasiDonasi;
    }

    public String getFeedbackPengalamanBuruk() {
        return feedbackPengalamanBuruk;
    }

    public String getEvaluasiTambahan() {
        return evaluasiTambahan;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMediaPendukungPath() {
        return mediaPendukungPath;
    }

    // Setters
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setLaporanBencana(String laporanBencana) {
        this.laporanBencana = laporanBencana;
    }

    public void setJenisBencana(String jenisBencana) {
        this.jenisBencana = jenisBencana;
    }

    public void setLokasiLaporan(String lokasiLaporan) {
        this.lokasiLaporan = lokasiLaporan;
    }

    public void setRatingPenanganan(int ratingPenanganan) {
        this.ratingPenanganan = ratingPenanganan;
    }

    public void setRatingKinerjaRelawan(int ratingKinerjaRelawan) {
        this.ratingKinerjaRelawan = ratingKinerjaRelawan;
    }

    public void setRatingAlokasiDonasi(int ratingAlokasiDonasi) {
        this.ratingAlokasiDonasi = ratingAlokasiDonasi;
    }

    public void setRatingPengalamanBuruk(int ratingPengalamanBuruk) {
        this.ratingPengalamanBuruk = ratingPengalamanBuruk;
    }

    public void setFeedbackPenanganan(String feedbackPenanganan) {
        this.feedbackPenanganan = feedbackPenanganan;
    }

    public void setFeedbackKinerjaRelawan(String feedbackKinerjaRelawan) {
        this.feedbackKinerjaRelawan = feedbackKinerjaRelawan;
    }

    public void setFeedbackAlokasiDonasi(String feedbackAlokasiDonasi) {
        this.feedbackAlokasiDonasi = feedbackAlokasiDonasi;
    }

    public void setFeedbackPengalamanBuruk(String feedbackPengalamanBuruk) {
        this.feedbackPengalamanBuruk = feedbackPengalamanBuruk;
    }

    public void setEvaluasiTambahan(String evaluasiTambahan) {
        this.evaluasiTambahan = evaluasiTambahan;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setMediaPendukungPath(String mediaPendukungPath) {
        this.mediaPendukungPath = mediaPendukungPath;
    }

    // Method to calculate average rating
    public double getAverageRating() {
        return (ratingPenanganan + ratingKinerjaRelawan + ratingAlokasiDonasi + ratingPengalamanBuruk) / 4.0;
    }

    // Validation method
    public boolean isValid() {
        return userEmail != null && !userEmail.isEmpty() &&
               laporanBencana != null && !laporanBencana.isEmpty() &&
               jenisBencana != null && !jenisBencana.isEmpty() &&
               lokasiLaporan != null && !lokasiLaporan.isEmpty() &&
               ratingPenanganan > 0 && ratingKinerjaRelawan > 0 &&
               ratingAlokasiDonasi > 0 && ratingPengalamanBuruk > 0;
    }

    @Override
    public String toString() {
        return "Feedback dari " + userEmail + " untuk " + jenisBencana + " di " + lokasiLaporan;
    }
}

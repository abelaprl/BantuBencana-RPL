package com;

import java.io.Serializable;

public class LaporanBencanaData implements Serializable {
    private String jenisBencana;
    private String lokasi;
    private String deskripsi;
    private String tingkatKeparahan;
    private String jumlahKorban;
    private String createdBy; // Email user yang membuat laporan
    private long timestamp; // Waktu pembuatan laporan

    public LaporanBencanaData(String jenisBencana, String lokasi, String deskripsi, String tingkatKeparahan, String jumlahKorban) {
        this.jenisBencana = jenisBencana;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
        this.tingkatKeparahan = tingkatKeparahan;
        this.jumlahKorban = jumlahKorban;
        this.timestamp = System.currentTimeMillis();
        
        // Default to current user if available
        if (UserSessionManager.isLoggedIn()) {
            this.createdBy = UserSessionManager.getCurrentUserEmail();
        } else {
            this.createdBy = "anonymous";
        }
    }

    public LaporanBencanaData(String jenisBencana, String lokasi, String deskripsi, String tingkatKeparahan, String jumlahKorban, String createdBy) {
        this(jenisBencana, lokasi, deskripsi, tingkatKeparahan, jumlahKorban);
        this.createdBy = createdBy;
    }

    // Getters
    public String getJenisBencana() {
        return jenisBencana;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getTingkatKeparahan() {
        return tingkatKeparahan;
    }

    public String getJumlahKorban() {
        return jumlahKorban;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public long getTimestamp() {
        return timestamp;
    }

    // Legacy getter for backward compatibility
    public String getJenis() {
        return jenisBencana;
    }

    // Setters
    public void setJenisBencana(String jenisBencana) {
        this.jenisBencana = jenisBencana;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setTingkatKeparahan(String tingkatKeparahan) {
        this.tingkatKeparahan = tingkatKeparahan;
    }

    public void setJumlahKorban(String jumlahKorban) {
        this.jumlahKorban = jumlahKorban;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return jenisBencana + " di " + lokasi;
    }
    
    // Method untuk konversi ke format string untuk penyimpanan file
    public String toFileString() {
        return jenisBencana + "|" + lokasi + "|" + deskripsi + "|" + 
               tingkatKeparahan + "|" + jumlahKorban + "|" + createdBy + "|" + timestamp;
    }
    
    // Method untuk membuat objek dari string file
    public static LaporanBencanaData fromFileString(String fileString) {
        String[] parts = fileString.split("\\|");
        if (parts.length >= 7) {
            LaporanBencanaData laporan = new LaporanBencanaData(
                parts[0], // jenisBencana
                parts[1], // lokasi
                parts[2], // deskripsi
                parts[3], // tingkatKeparahan
                parts[4]  // jumlahKorban
            );
            laporan.setCreatedBy(parts[5]); // createdBy
            try {
                laporan.setTimestamp(Long.parseLong(parts[6])); // timestamp
            } catch (NumberFormatException e) {
                laporan.setTimestamp(System.currentTimeMillis());
            }
            return laporan;
        } else if (parts.length >= 5) {
            // Legacy format support
            LaporanBencanaData laporan = new LaporanBencanaData(
                parts[0], // jenisBencana
                parts[1], // lokasi
                parts[2], // deskripsi
                parts[3], // tingkatKeparahan
                parts[4]  // jumlahKorban
            );
            laporan.setCreatedBy("legacy_user");
            return laporan;
        }
        return null;
    }
}

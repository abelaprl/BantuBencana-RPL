package com;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Bencana implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String judul;
    private String lokasi;
    private String tanggal;
    private String deskripsiSingkat;
    private String namaFileGambar; // Gambar utama
    private List<String> daftarGambar; // Multiple images
    private List<String> daftarVideo; // Multiple videos
    
    public Bencana() {
        this.daftarGambar = new ArrayList<>();
        this.daftarVideo = new ArrayList<>();
    }
    
    public Bencana(String judul, String lokasi, String tanggal, String deskripsiSingkat) {
        this.judul = judul;
        this.lokasi = lokasi;
        this.tanggal = tanggal;
        this.deskripsiSingkat = deskripsiSingkat;
        this.daftarGambar = new ArrayList<>();
        this.daftarVideo = new ArrayList<>();
    }
    
    public Bencana(String judul, String lokasi, String tanggal, String deskripsiSingkat, String namaFileGambar) {
        this.judul = judul;
        this.lokasi = lokasi;
        this.tanggal = tanggal;
        this.deskripsiSingkat = deskripsiSingkat;
        this.namaFileGambar = namaFileGambar;
        this.daftarGambar = new ArrayList<>();
        this.daftarVideo = new ArrayList<>();
    }
    
    // Method fromString untuk BencanaRepository
    public static Bencana fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        
        try {
            String[] parts = line.split("\\|"); // Menggunakan | sebagai delimiter
            if (parts.length >= 4) {
                String judul = parts[0].trim();
                String lokasi = parts[1].trim();
                String tanggal = parts[2].trim();
                String deskripsiSingkat = parts[3].trim();
                String namaFileGambar = parts.length > 4 ? parts[4].trim() : "";
                
                Bencana bencana = new Bencana(judul, lokasi, tanggal, deskripsiSingkat, namaFileGambar);
                
                // Jika ada data gambar tambahan (parts[5] dan seterusnya)
                if (parts.length > 5 && !parts[5].trim().isEmpty()) {
                    String[] gambarPaths = parts[5].split(",");
                    for (String path : gambarPaths) {
                        if (!path.trim().isEmpty()) {
                            bencana.addGambar(path.trim());
                        }
                    }
                }
                
                // Jika ada data video (parts[6] dan seterusnya)
                if (parts.length > 6 && !parts[6].trim().isEmpty()) {
                    String[] videoPaths = parts[6].split(",");
                    for (String path : videoPaths) {
                        if (!path.trim().isEmpty()) {
                            bencana.addVideo(path.trim());
                        }
                    }
                }
                
                return bencana;
            }
        } catch (Exception e) {
            System.err.println("Error parsing Bencana from string: " + line);
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Method toString untuk menyimpan ke file
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(judul != null ? judul : "").append("|");
        sb.append(lokasi != null ? lokasi : "").append("|");
        sb.append(tanggal != null ? tanggal : "").append("|");
        sb.append(deskripsiSingkat != null ? deskripsiSingkat : "").append("|");
        sb.append(namaFileGambar != null ? namaFileGambar : "").append("|");
        
        // Tambahkan daftar gambar
        if (daftarGambar != null && !daftarGambar.isEmpty()) {
            sb.append(String.join(",", daftarGambar));
        }
        sb.append("|");
        
        // Tambahkan daftar video
        if (daftarVideo != null && !daftarVideo.isEmpty()) {
            sb.append(String.join(",", daftarVideo));
        }
        
        return sb.toString();
    }
    
    // Getters and Setters
    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }
    
    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }
    
    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
    
    public String getDeskripsiSingkat() { return deskripsiSingkat; }
    public void setDeskripsiSingkat(String deskripsiSingkat) { this.deskripsiSingkat = deskripsiSingkat; }
    
    public String getNamaFileGambar() { return namaFileGambar; }
    public void setNamaFileGambar(String namaFileGambar) { this.namaFileGambar = namaFileGambar; }
    
    public List<String> getDaftarGambar() { return daftarGambar; }
    public void setDaftarGambar(List<String> daftarGambar) { this.daftarGambar = daftarGambar; }
    
    public List<String> getDaftarVideo() { return daftarVideo; }
    public void setDaftarVideo(List<String> daftarVideo) { this.daftarVideo = daftarVideo; }
    
    public void addGambar(String pathGambar) {
        if (this.daftarGambar == null) {
            this.daftarGambar = new ArrayList<>();
        }
        this.daftarGambar.add(pathGambar);
        
        // Set sebagai gambar utama jika belum ada
        if (this.namaFileGambar == null || this.namaFileGambar.isEmpty()) {
            this.namaFileGambar = pathGambar;
        }
    }
    
    public void addVideo(String pathVideo) {
        if (this.daftarVideo == null) {
            this.daftarVideo = new ArrayList<>();
        }
        this.daftarVideo.add(pathVideo);
    }
}
package data;
// File: src/LaporanBencanaData.java

import java.io.Serializable;

public class LaporanBencanaData implements Serializable {
    // --- MODIFIKASI DIMULAI ---
    private static final long serialVersionUID = 2L; // Ubah ini ke 2L atau nilai unik lainnya jika sudah ada 1L
    // --- MODIFIKASI BERAKHIR ---

    private String jenisBencana;
    private String lokasi;
    private String deskripsi;
    private String tingkatKeparahan;
    private String jumlahKorban;

    public LaporanBencanaData(String jenisBencana, String lokasi, String deskripsi, String tingkatKeparahan, String jumlahKorban) {
        this.jenisBencana = jenisBencana;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
        this.tingkatKeparahan = tingkatKeparahan;
        this.jumlahKorban = jumlahKorban;
    }

    public String getJenisBencana() {
        return jenisBencana;
    }

    public void setJenisBencana(String jenisBencana) {
        this.jenisBencana = jenisBencana;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getLokasiBencana() { // Method untuk kompatibilitas dengan Feedback.java
        return lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTingkatKeparahan() {
        return tingkatKeparahan;
    }

    public void setTingkatKeparahan(String tingkatKeparahan) {
        this.tingkatKeparahan = tingkatKeparahan;
    }

    public String getJumlahKorban() {
        return jumlahKorban;
    }

    public void setJumlahKorban(String jumlahKorban) {
        this.jumlahKorban = jumlahKorban;
    }

    public String getDisplayName() {
        return jenisBencana + " - " + lokasi + " (" + tingkatKeparahan + ")";
    }

    @Override
    public String toString() {
        return "LaporanBencanaData{" +
                "jenisBencana='" + jenisBencana + '\'' +
                ", lokasi='" + lokasi + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", tingkatKeparahan='" + tingkatKeparahan + '\'' +
                ", jumlahKorban='" + jumlahKorban + '\'' +
                '}';
    }
}
public class LaporanBencanaData {
    private String jenisBencana;
    private String lokasiBencana;
    private String deskripsi;
    private String tingkatKeparahan;
    private String jumlahKorban;
    private String tanggalLaporan;
    
    public LaporanBencanaData() {
        this.tanggalLaporan = java.time.LocalDateTime.now().toString();
    }
    
    public LaporanBencanaData(String jenisBencana, String lokasiBencana, String deskripsi, 
                             String tingkatKeparahan, String jumlahKorban) {
        this.jenisBencana = jenisBencana;
        this.lokasiBencana = lokasiBencana;
        this.deskripsi = deskripsi;
        this.tingkatKeparahan = tingkatKeparahan;
        this.jumlahKorban = jumlahKorban;
        this.tanggalLaporan = java.time.LocalDateTime.now().toString();
    }
    
    // Getters and Setters
    public String getJenisBencana() { return jenisBencana; }
    public void setJenisBencana(String jenisBencana) { this.jenisBencana = jenisBencana; }
    
    public String getLokasiBencana() { return lokasiBencana; }
    public void setLokasiBencana(String lokasiBencana) { this.lokasiBencana = lokasiBencana; }
    
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    
    public String getTingkatKeparahan() { return tingkatKeparahan; }
    public void setTingkatKeparahan(String tingkatKeparahan) { this.tingkatKeparahan = tingkatKeparahan; }
    
    public String getJumlahKorban() { return jumlahKorban; }
    public void setJumlahKorban(String jumlahKorban) { this.jumlahKorban = jumlahKorban; }
    
    public String getTanggalLaporan() { return tanggalLaporan; }
    public void setTanggalLaporan(String tanggalLaporan) { this.tanggalLaporan = tanggalLaporan; }
    
    // Method untuk mendapatkan display name untuk dropdown
    public String getDisplayName() {
        return jenisBencana + " - " + lokasiBencana + " (" + tingkatKeparahan + ")";
    }
    
    @Override
    public String toString() {
        return "LaporanBencanaData{" +
                "jenisBencana='" + jenisBencana + '\'' +
                ", lokasiBencana='" + lokasiBencana + '\'' +
                ", tingkatKeparahan='" + tingkatKeparahan + '\'' +
                ", jumlahKorban='" + jumlahKorban + '\'' +
                ", tanggalLaporan='" + tanggalLaporan + '\'' +
                '}';
    }
}
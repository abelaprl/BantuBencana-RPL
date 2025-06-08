import java.io.Serializable;

public class LaporanBencanaData implements Serializable {
    private static final long serialVersionUID = 1L;
    
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
    
    // Method untuk kompatibilitas dengan Feedback.java
    public String getLokasiBencana() {
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
    
    // Method untuk menampilkan nama display yang digunakan di ComboBox
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
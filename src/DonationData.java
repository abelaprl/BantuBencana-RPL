import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DonationData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String disasterType;
    private String location;
    private String donationType; // "Uang" atau "Barang"
    private String amountOrDescription; // Jumlah uang atau deskripsi barang
    private String donorEmail;
    private String timestamp;
    private String additionalInfo; // BARU: Untuk metode pembayaran atau lokasi drop-off
    // private String status; // Opsional: Untuk status barang (misal: "Menunggu Drop-off", "Diterima")

    public DonationData(String disasterType, String location, String donationType, String amountOrDescription, String donorEmail) {
        this.disasterType = disasterType;
        this.location = location;
        this.donationType = donationType;
        this.amountOrDescription = amountOrDescription;
        this.donorEmail = donorEmail;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.additionalInfo = ""; // Default kosong
    }

    // Constructor BARU jika Anda ingin menyimpan additionalInfo saat membuat objek
    public DonationData(String disasterType, String location, String donationType, String amountOrDescription, String donorEmail, String additionalInfo) {
        this(disasterType, location, donationType, amountOrDescription, donorEmail); // Panggil constructor yang sudah ada
        this.additionalInfo = additionalInfo;
    }


    // Getters
    public String getDisasterType() { return disasterType; }
    public String getLocation() { return location; }
    public String getDonationType() { return donationType; }
    public String getAmountOrDescription() { return amountOrDescription; }
    public String getDonorEmail() { return donorEmail; }
    public String getTimestamp() { return timestamp; }
    public String getAdditionalInfo() { return additionalInfo; } // BARU: Getter untuk additional info

    // Setters (jika Anda ingin bisa mengubah data setelah dibuat, misal status)
    // public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Donasi [Bencana: " + disasterType + ", Lokasi: " + location +
               ", Jenis: " + donationType + ", Detail: " + amountOrDescription +
               ", Info Tambahan: " + additionalInfo +
               ", Email Donor: " + donorEmail + ", Waktu: " + timestamp + "]";
    }
}
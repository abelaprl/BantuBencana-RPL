import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DonationData implements Serializable {
    private static final long serialVersionUID = 1L; // Untuk serialisasi
    private String disasterType;
    private String location;
    private String donationType; // Contoh: "Uang", "Barang", "Tenaga"
    private String amountOrDescription; // Jumlah uang atau deskripsi barang/tenaga
    private String donorEmail;
    private String timestamp;

    public DonationData(String disasterType, String location, String donationType, String amountOrDescription, String donorEmail) {
        this.disasterType = disasterType;
        this.location = location;
        this.donationType = donationType;
        this.amountOrDescription = amountOrDescription;
        this.donorEmail = donorEmail;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Getters
    public String getDisasterType() {
        return disasterType;
    }

    public String getLocation() {
        return location;
    }

    public String getDonationType() {
        return donationType;
    }

    public String getAmountOrDescription() {
        return amountOrDescription;
    }

    public String getDonorEmail() {
        return donorEmail;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Donasi [Jenis Bencana: " + disasterType + ", Lokasi: " + location +
               ", Jenis Donasi: " + donationType + ", Jumlah/Deskripsi: " + amountOrDescription +
               ", Email Donor: " + donorEmail + ", Waktu: " + timestamp + "]";
    }
}
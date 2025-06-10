// Kelas ini mendefinisikan struktur data untuk informasi Bencana
public class Bencana {
    private String judul;
    private String lokasi;
    private String deskripsiSingkat;
    private String tanggal; // Bisa juga LocalDate jika ingin lebih proper
    private String namaFileGambar; // Nama file gambar, misal: "gempa_palu.jpg"

    public Bencana(String judul, String lokasi, String deskripsiSingkat, String tanggal, String namaFileGambar) {
        this.judul = judul;
        this.lokasi = lokasi;
        this.deskripsiSingkat = deskripsiSingkat;
        this.tanggal = tanggal;
        this.namaFileGambar = namaFileGambar;
    }

    // --- Getters ---
    public String getJudul() {
        return judul;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getDeskripsiSingkat() {
        return deskripsiSingkat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getNamaFileGambar() {
        return namaFileGambar;
    }

    // --- Setters (opsional, tergantung kebutuhan) ---
    public void setJudul(String judul) { this.judul = judul; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }
    public void setDeskripsiSingkat(String deskripsiSingkat) { this.deskripsiSingkat = deskripsiSingkat; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
    public void setNamaFileGambar(String namaFileGambar) { this.namaFileGambar = namaFileGambar; }


    @Override
    public String toString() {
        // Format untuk penyimpanan di file teks, gunakan pemisah yang jarang muncul
        return judul + "|" + lokasi + "|" + deskripsiSingkat + "|" + tanggal + "|" + namaFileGambar;
    }

    // Metode statis untuk membuat objek Bencana dari string di file teks
    public static Bencana fromString(String line) {
        // Gunakan karakter pemisah yang jarang muncul di teks biasa, misal '|'
        String[] parts = line.split("\\|"); // "\\|" karena '|' adalah karakter khusus di regex
        if (parts.length == 5) {
            return new Bencana(parts[0], parts[1], parts[2], parts[3], parts[4]);
        }
        return null;
    }
}
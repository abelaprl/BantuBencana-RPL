// Kelas ini mendefinisikan struktur data untuk User (Email dan Password)
// Tidak ada import yang dibutuhkan karena tidak ada package lain yang digunakan.
public class User {
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter methods
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setter methods (opsional, tergantung kebutuhan)
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Metode toString() digunakan untuk mengubah objek User menjadi format string
    // yang akan disimpan di file teks (dipisahkan koma: email,password)
    @Override
    public String toString() {
        return email + "," + password;
    }

    // Metode statis untuk membuat objek User dari satu baris string dari file teks
    public static User fromString(String line) {
        String[] parts = line.split(","); // Pisahkan string berdasarkan koma
        if (parts.length == 2) { // Pastikan ada dua bagian (email dan password)
            return new User(parts[0], parts[1]);
        }
        return null; // Kembalikan null jika format tidak valid
    }
}
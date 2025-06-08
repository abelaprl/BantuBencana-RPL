import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Kelas ini bertanggung jawab untuk membaca dan menulis data User ke file teks
public class UserRepository {
    // Nama file tempat data pengguna akan disimpan.
    // File ini akan dibuat di direktori tempat aplikasi dijalankan (root proyek atau src tergantung cara run)
    private static final String FILE_PATH = "users.txt";

    // Mengambil semua user dari file teks
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        // Menggunakan try-with-resources untuk memastikan BufferedReader ditutup otomatis
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            // Baca setiap baris dari file
            while ((line = reader.readLine()) != null) {
                // Konversi setiap baris menjadi objek User
                User user = User.fromString(line);
                if (user != null) {
                    users.add(user); // Tambahkan ke daftar user jika valid
                }
            }
        } catch (IOException e) {
            // File mungkin belum ada, tidak perlu error serius, hanya cetak pesan
            System.err.println("File users.txt belum ada atau error membaca: " + e.getMessage());
            // Anda bisa tambahkan log di sini: e.printStackTrace();
        }
        return users;
    }

    // Mencari user berdasarkan email
    public Optional<User> findByEmail(String email) {
        // Ambil semua user, lalu filter untuk menemukan yang cocok dengan email
        return findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst(); // Ambil user pertama yang cocok (email harus unik)
    }

    // Menyimpan user baru ke file
    public void save(User user) {
        // Menggunakan try-with-resources untuk memastikan BufferedWriter ditutup otomatis
        // FileWriter(FILE_PATH, true) berarti akan menambahkan ke akhir file (append mode)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.toString()); // Tulis representasi string dari objek User
            writer.newLine(); // Tambahkan baris baru untuk user berikutnya
        } catch (IOException e) {
            System.err.println("Error menyimpan user ke file: " + e.getMessage());
            // e.printStackTrace();
        }
    }

    // Metode ini akan digunakan jika Anda ingin menghapus atau memperbarui user
    // (lebih kompleks karena harus menulis ulang seluruh file)
    public void saveAll(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) { // false = overwrite
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error menulis ulang file pengguna: " + e.getMessage());
        }
    }
}
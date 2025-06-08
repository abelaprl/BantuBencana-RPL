// File: src/UserRepository.java
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static List<User> userList;

    static {
        userList = DataManager.loadUserData(); // Muat data user dari file
    }

    public void register(User user) {
        // Periksa apakah username atau email sudah terdaftar
        if (isUsernameTaken(user.getUsername())) {
            System.out.println("Registrasi gagal: Username '" + user.getUsername() + "' sudah ada.");
        } else if (isEmailTaken(user.getEmail())) { // <-- Tambahkan pengecekan email
            System.out.println("Registrasi gagal: Email '" + user.getEmail() + "' sudah ada.");
        }
        else {
            userList.add(user);
            DataManager.saveUserData(userList); // Simpan data setelah registrasi
            System.out.println("User " + user.getUsername() + " berhasil didaftarkan.");
        }
    }

    // Metode autentikasi berdasarkan username (tetap ada jika suatu saat ingin pakai username)
    public User authenticate(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user; // Autentikasi berhasil
            }
        }
        return null; // Autentikasi gagal
    }

    // <-- Tambahkan metode autentikasi berdasarkan Email -->
    public User authenticateByEmail(String email, String password) {
        for (User user : userList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user; // Autentikasi berhasil
            }
        }
        return null; // Autentikasi gagal
    }

    public boolean isUsernameTaken(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // <-- Tambahkan metode untuk memeriksa apakah email sudah terdaftar -->
    public boolean isEmailTaken(String email) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userList);
    }
}
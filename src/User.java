// File: src/User.java
import java.io.Serializable; // Tambahkan import ini

public class User implements Serializable { // Implementasikan Serializable
    private static final long serialVersionUID = 1L; // Direkomendasikan untuk Serializable

    private String username;
    private String password; // Untuk contoh sederhana, kita simpan plain text. Dalam aplikasi nyata, gunakan hashing!
    private String email; // Tambahkan field email jika belum ada

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
               "username='" + username + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}
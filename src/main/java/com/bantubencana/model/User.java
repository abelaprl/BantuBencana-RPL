package com.bantubencana.model;

public class User {

    public enum Role {
        Admin, NGO, Pemerintah, Masyarakat, MasyarakatPelapor, MasyarakatDonatur, MasyarakatRelawan
    }

    private String userId;
    private String nama;
    private String email;
    private String password; // Warning: Jangan menyimpan password dalam bentuk plain text di objek setelah registrasi/login
    private Role role;
    private Boolean status; // true for active, false for inactive

    // Constructor
    public User(String userId, String nama, String email, String password, Role role, Boolean status) {
        this.userId = userId;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    // Constructor without userId (useful for new registrations)
    public User(String nama, String email, String password, Role role) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = true; // Default new user to active
    }

    // Default constructor for Firebase deserialization
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Boolean getStatus() {
        return status;
    }

    // Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    // Operasi yang diminta dalam dokumen (sesuai kebutuhan, implementasi dasar)
    public boolean login() {
        // Logika login akan ditangani di controller dan FirebaseUtil
        System.out.println("User " + email + " attempting to log in.");
        return false; // Implementasi akan di controller
    }

    public void logout() {
        System.out.println("User " + email + " logged out.");
        // Logika logout akan ditangani di controller
    }

    public void aktifkan() {
        this.status = true;
        System.out.println("Account for " + email + " activated.");
        // Update di database
    }

    public void nonaktifkan() {
        this.status = false;
        System.out.println("Account for " + email + " deactivated.");
        // Update di database
    }

    public void gantiRole(Role newRole) {
        this.role = newRole;
        System.out.println("Role for " + email + " changed to " + newRole);
        // Update di database
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", nama='" + nama + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}
package com;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String USER_DATA_FILE = "user_data.txt";
    private static List<User> users = new ArrayList<>();
    
    // Load users from file when class is first loaded
    static {
        loadUsersFromFile();
        
        // Add default users if file is empty or doesn't exist
        if (users.isEmpty()) {
            users.add(new User("Admin", "admin@example.com", "admin123"));
            users.add(new User("User Test", "user@example.com", "user123"));
            saveUsersToFile(); // Save default users to file
            System.out.println("DEBUG: Default users created and saved to file");
        }
    }

    public void save(User user) {
        users.add(user);
        saveUsersToFile();
        System.out.println("DEBUG: User saved: " + user.getEmail());
    }

    public boolean isEmailExists(String email) {
        boolean exists = users.stream().anyMatch(user -> user.getEmail().equals(email));
        System.out.println("DEBUG: Email exists check for " + email + ": " + exists);
        return exists;
    }

    public boolean authenticate(String email, String password) {
        boolean authenticated = users.stream()
            .anyMatch(user -> user.getEmail().equals(email) && user.getPassword().equals(password));
        System.out.println("DEBUG: Authentication for " + email + ": " + authenticated);
        return authenticated;
    }

    public User findByEmail(String email) {
        return users.stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst()
            .orElse(null);
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    // Save users to file
    private static void saveUsersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_DATA_FILE))) {
            for (User user : users) {
                // Format: name|email|password
                writer.println(user.getName() + "|" + user.getEmail() + "|" + user.getPassword());
            }
            System.out.println("DEBUG: User data berhasil disimpan ke file: " + USER_DATA_FILE);
        } catch (IOException e) {
            System.err.println("ERROR: Gagal menyimpan data user ke file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load users from file
    private static void loadUsersFromFile() {
        File file = new File(USER_DATA_FILE);
        if (!file.exists()) {
            System.out.println("DEBUG: File user data tidak ditemukan, akan dibuat baru");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            users.clear(); // Clear existing users before loading
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String name = parts[0];
                    String email = parts[1];
                    String password = parts[2];
                    users.add(new User(name, email, password));
                }
            }
            System.out.println("DEBUG: " + users.size() + " user berhasil dimuat dari file: " + USER_DATA_FILE);
        } catch (IOException e) {
            System.err.println("ERROR: Gagal memuat data user dari file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to reload users from file (useful for testing)
    public static void reloadUsers() {
        loadUsersFromFile();
    }

    // Method to get total user count
    public static int getUserCount() {
        return users.size();
    }

    // Method to delete user (for admin purposes)
    public boolean deleteUser(String email) {
        boolean removed = users.removeIf(user -> user.getEmail().equals(email));
        if (removed) {
            saveUsersToFile();
            System.out.println("DEBUG: User deleted: " + email);
        }
        return removed;
    }

    // Method to update user password
    public boolean updateUserPassword(String email, String newPassword) {
        User user = findByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            saveUsersToFile();
            System.out.println("DEBUG: Password updated for user: " + email);
            return true;
        }
        return false;
    }
}

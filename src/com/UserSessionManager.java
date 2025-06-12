package com;

import java.io.*;
import java.util.Properties;

public class UserSessionManager {
    private static final String SESSION_FILE = "user_session.properties";
    private static String currentUserEmail = null;
    private static User currentUser = null;

    // Save current user session
    public static void saveSession(String email) {
        currentUserEmail = email;
        currentUser = new UserRepository().findByEmail(email);
        
        Properties props = new Properties();
        props.setProperty("current_user_email", email);
        props.setProperty("login_time", String.valueOf(System.currentTimeMillis()));
        
        try (FileOutputStream out = new FileOutputStream(SESSION_FILE)) {
            props.store(out, "User Session Data");
            System.out.println("DEBUG: Session saved for user: " + email);
        } catch (IOException e) {
            System.err.println("ERROR: Failed to save session: " + e.getMessage());
        }
    }

    // Load user session
    public static void loadSession() {
        File file = new File(SESSION_FILE);
        if (!file.exists()) {
            System.out.println("DEBUG: No previous session found");
            return;
        }

        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(file)) {
            props.load(in);
            String email = props.getProperty("current_user_email");
            if (email != null && !email.isEmpty()) {
                currentUserEmail = email;
                currentUser = new UserRepository().findByEmail(email);
                System.out.println("DEBUG: Session loaded for user: " + email);
            }
        } catch (IOException e) {
            System.err.println("ERROR: Failed to load session: " + e.getMessage());
        }
    }

    // Clear session (logout)
    public static void clearSession() {
        currentUserEmail = null;
        currentUser = null;
        
        File file = new File(SESSION_FILE);
        if (file.exists()) {
            file.delete();
            System.out.println("DEBUG: Session cleared");
        }
    }

    // Get current user email
    public static String getCurrentUserEmail() {
        return currentUserEmail;
    }

    // Get current user object
    public static User getCurrentUser() {
        return currentUser;
    }

    // Check if user is logged in
    public static boolean isLoggedIn() {
        return currentUserEmail != null && currentUser != null;
    }
}

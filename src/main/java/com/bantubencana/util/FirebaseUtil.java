package com.bantubencana.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import com.bantubencana.model.User; // Import the User model

import com.google.firebase.ErrorCode;
import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuthException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class FirebaseUtil {

    private static FirebaseApp firebaseApp;
    private static FirebaseAuth firebaseAuth;
    private static Firestore firestore;

    public static void initializeFirebase() {
        if (firebaseApp == null) {
            try {
                // Pastikan nama file serviceAccountKey.json sesuai dengan yang Anda unduh
                FileInputStream serviceAccount = new FileInputStream("src/main/resources/bantubencana-cd05c-firebase-adminsdk-fbsvc-63b94af10d.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://bantubencana-cd05c") // Ganti dengan URL database Anda jika ada
                        .build();

                firebaseApp = FirebaseApp.initializeApp(options);
                firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
                firestore = FirestoreClient.getFirestore(firebaseApp);
                System.out.println("Firebase initialized successfully!");
            } catch (IOException e) {
                System.err.println("Error initializing Firebase: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            initializeFirebase(); // Ensure initialized
        }
        return firebaseAuth;
    }

    public static Firestore getFirestore() {
        if (firestore == null) {
            initializeFirebase(); // Ensure initialized
        }
        return firestore;
    }

    public static User registerUser(String email, String password, String nama, User.Role role) throws FirebaseAuthException, ExecutionException, InterruptedException {
        // Create user in Firebase Authentication
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setDisplayName(nama)
                .setEmailVerified(false) // Optionally set to true if you want email verification
                .setDisabled(false);

        UserRecord userRecord = getFirebaseAuth().createUser(request);
        System.out.println("Successfully created new user: " + userRecord.getUid());

        // Save user data to Firestore
        User newUser = new User(userRecord.getUid(), nama, email, password, role, true); // Storing password is not recommended for production
        getFirestore().collection("users").document(userRecord.getUid()).set(newUser).get();
        System.out.println("User data saved to Firestore for UID: " + userRecord.getUid());

        return newUser;
    }

    public static User loginUser(String email, String password) throws FirebaseAuthException {
        // Firebase Admin SDK does not directly support client-side password sign-in
        // You would typically use Firebase Client SDK (e.g., JavaScript, Android) for this.
        // For a desktop app using Admin SDK, you'd verify credentials against your own backend
        // or directly use Auth.signInWithEmailAndPassword via a REST call if not for production.

        // For demonstration, we'll try to get user by email and then compare password (NOT SECURE FOR PROD)
        // A more secure way would be to have a backend that handles authentication and returns a token.
        // For this project, we'll assume we can get user data and verify password (for demonstration only).

        try {
            UserRecord userRecord = getFirebaseAuth().getUserByEmail(email);
            // This is a simplified, INSECURE way to "login" with Admin SDK
            // In a real app, you would verify the password through Firebase client SDK or a backend.
            // Since we're storing password in our User model (for simplification of this project's scope),
            // we'll fetch the user data from Firestore and compare. This is still not ideal.

            // Fetch user from Firestore to get stored password and role
            User userFromFirestore = getFirestore().collection("users").document(userRecord.getUid()).get().get().toObject(User.class);

            if (userFromFirestore != null && userFromFirestore.getPassword().equals(password)) {
                System.out.println("User " + email + " logged in successfully.");
                return userFromFirestore;
            } else {
                throw new FirebaseAuthException(ErrorCode.INVALID_ARGUMENT, "Invalid credentials.", null, null, null);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new FirebaseAuthException(ErrorCode.NOT_FOUND, "User not found or other error.", null, null, null);
        }
    }


    // Method to fetch a user by UID from Firestore
    public static User getUserById(String uid) throws InterruptedException, ExecutionException {
        return getFirestore().collection("users").document(uid).get().get().toObject(User.class);
    }
}
package service;

import com.google.gson.Gson;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class FirebaseAuthService {
    private static final String API_KEY = "AIzaSyCKeC4g_ZfhYlVz9OeM0-F9YxvuzabJkpY";

    public static String login(String email, String password) throws Exception {
        URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);
        data.put("returnSecureToken", true);

        OutputStream os = conn.getOutputStream();
        os.write(new Gson().toJson(data).getBytes());
        os.flush(); os.close();

        if (conn.getResponseCode() == 200) {
            return new String(conn.getInputStream().readAllBytes());
        } else {
            throw new RuntimeException("Login gagal: " + new String(conn.getErrorStream().readAllBytes()));
        }
    }

    public static String register(String email, String password, String name) throws Exception {
        URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + API_KEY);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);
        data.put("returnSecureToken", true);

        OutputStream os = conn.getOutputStream();
        os.write(new Gson().toJson(data).getBytes());
        os.flush(); os.close();

        if (conn.getResponseCode() == 200) {
            return new String(conn.getInputStream().readAllBytes());
        } else {
            throw new RuntimeException("Registrasi gagal: " + new String(conn.getErrorStream().readAllBytes()));
        }
    }
}

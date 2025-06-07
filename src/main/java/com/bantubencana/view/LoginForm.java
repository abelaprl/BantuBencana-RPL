package view;

import service.FirebaseAuthService;
import java.util.Scanner;

public class LoginView {
    public void show() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n==== LOGIN ====");
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        try {
            String response = FirebaseAuthService.login(email, password);
            System.out.println("Login berhasil!\n" + response);
        } catch (Exception e) {
            System.out.println("Login gagal: " + e.getMessage());
        }
    }
}
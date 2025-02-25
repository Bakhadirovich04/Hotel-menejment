package org.example.service.adminPackage;

import java.util.Scanner;

public class UserService {
    static Scanner intScanner = new Scanner(System.in);
    static Scanner strScanner = new Scanner(System.in);
    static Scanner doubleScanner = new Scanner(System.in);

    public static void userService() {
        while (true) {
            System.out.println("""
                    
                    0: Back
                    1: Show all users
                    2: Add user
                    3: Get one user
                    
                    Choose!
                    """);
            int choice = intScanner.nextInt();
            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    showAllUsers();
                }
                case 2 -> {
                    addUser();
                }
                case 3 -> {
                    getOneUser();
                }
                default -> System.out.println("invalid choice");
            }
        }
    }

    private static void getOneUser() {
    }

    private static void addUser() {
    }

    private static void showAllUsers() {
    }
}

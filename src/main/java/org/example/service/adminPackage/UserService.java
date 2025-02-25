package org.example.service.adminPackage;

import org.example.entity.User;
import org.example.entity.enums.Role;

import java.util.Scanner;
import java.util.UUID;

import static org.example.db.Datasource.strScanner;
import static org.example.db.Datasource.users;

public class UserService {
    static Scanner intScanner = new Scanner(System.in);
    static Scanner strScanner = new Scanner(System.in);
    public static void userService() {
        while (true){
            System.out.println("""
                    
                    0: Back
                    1: Show all users
                    2: Add user
                    3: Get one user
                    
                    Choose!
                    """);
            int choice = intScanner.nextInt();
            switch (choice){
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
        User user=new User();
        System.out.print("Enter name: ");
        String name =strScanner.nextLine();
        System.out.print("Enter surname: ");
        String surname =strScanner.nextLine();
        System.out.print("Enter email : ");
        String email=strScanner.nextLine();
        if(!checkEmail(email)){
            System.out.print("Enter password: ");
            user.setEmail(email);
            user.setPassword(strScanner.nextLine());
            user.setName(name);
            user.setSurname(surname);
            System.out.print("Enter phoneNumber: ");
            user.setPhoneNumber(strScanner.nextLine());
            System.out.print("Enter fullAddress: ");
            user.setFullAddress(strScanner.nextLine());
            user.setRole(Role.USER);
            users.add(user);
            System.out.println("User registered successfully...✔️");

        }
        else{
            System.out.println("This email is busy");
        }
    }
    private static boolean checkEmail(String a) {
        for (User user : users) {
            if(user.getEmail().equals(a)){
                return true;
            }
        }
        return false;
    }

    private static void showAllUsers() {
        users.forEach(System.out::println);
    }
}

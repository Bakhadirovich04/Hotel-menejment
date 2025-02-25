package org.example.service.AuthPackage;

import org.example.entity.User;
import org.example.entity.enums.Role;
import org.example.service.adminPackage.AdminService;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import static org.example.db.Datasource.*;
import static org.example.db.Datasource.users;

public class AuthService {
    public static void service(){
        while (true) {
            System.out.print("""
                               Main Menu👇
                    0.End the program
                    1.Sign Up
                    2.Sign In
                    """);
            System.out.print("Select Comand: ");
            switch (intScanner.nextInt()) {
                case 0 -> {
                    System.out.println("Goodbye👋👋👋");
                    return;
                }
                case 1 -> {
                    signUp();
                }
                case 2 -> {
                    signIn();
                }
                default -> {
                    System.out.println("No such action");
                }
            }
        }
    }
    private static void signUp() {
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

    private static void signIn() {
        System.out.print("Enter email: ");
        String email=strScanner.nextLine();
        System.out.print("Enter Password: ");
        String pass=strScanner.nextLine();
        boolean chekLogin=true;
        for (User user : users) {
            if(user.getEmail().equals(email)&&user.getPassword().equals(pass)){
                currentUser=user;
                chekLogin=false;
                break;
            }
        }
        if(chekLogin){
            System.out.println("There is no such password or login");
        }
        else{
            if (currentUser.getRole().equals(Role.USER)){
                //UserService.service();
            }
            else if(currentUser.getRole().equals(Role.ADMIN)){
                AdminService.service();
            }
            else if(currentUser.getRole().equals(Role.GUEST)){
                //
            }
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
}

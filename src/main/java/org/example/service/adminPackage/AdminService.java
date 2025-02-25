package org.example.service.adminPackage;

import java.util.Scanner;

import static org.example.service.adminPackage.BookService.bookService;
import static org.example.service.adminPackage.RoomService.roomService;
import static org.example.service.adminPackage.UserService.*;

public class AdminService {
    static Scanner strScanner = new Scanner(System.in);
    static Scanner intScanner = new Scanner(System.in);
    public static void service(){
        while (true){
            System.out.println("""
                
                0: Exit
                1: Room service
                2: Book service
                3: User service
                
                Choose!
                """);
            int choice = intScanner.nextInt();
            switch (choice){
                case 0 -> {
                    System.out.println("see you never");
                    return;
                }
                case 1 -> {
                    roomService();
                }
                case 2 -> {
                    bookService();
                }
                case 3 -> {
                    userService();
                }
                default -> System.out.println("invalid choice");
            }
        }
    }
}

package org.example.service.adminPackage;

import static org.example.service.adminPackage.UserService.intScanner;

public class BookService {
    public static void bookService() {
        while (true){
            System.out.println("""
                    
                    0: Back
                    1: Get all rooms
                    2: Available rooms
                    3: Book a room
                    
                    Choose!
                    """);
            int choice = intScanner.nextInt();
            switch (choice){
                case 0 -> {
                    return;
                }
                case 1 -> {
                    getAllRooms();
                }
                case 2 -> {
                    availableRooms();
                }
                case 3 -> {
                    bookARoom();
                }
                default -> System.out.println("invalid choice");
            }
        }
    }

    private static void bookARoom() {
    }

    private static void availableRooms() {
    }

    private static void getAllRooms() {
    }

}

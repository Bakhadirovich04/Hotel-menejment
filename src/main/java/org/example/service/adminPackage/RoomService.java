package org.example.service.adminPackage;

import static org.example.service.adminPackage.UserService.intScanner;

public class RoomService {
    public static void roomService() {
        while (true) {
            System.out.println("""
                    
                    0: Back
                    1: Add room
                    2: Edit room
                    3: Delete room
                    4: Get all room
                    5: Edit room state
                    
                    Choose!
                    """);
            int choice = intScanner.nextInt();
            switch (choice){
                case 0 -> {
                    return;
                }
                case 1 -> {
                    addRoom();
                }
                case 2 -> {
                    editRoom();
                }
                case 3 -> {
                    deleteRoom();
                }
                case 4 -> {
                    getAllRoom();
                }
                case 5 -> {
                    editRoomState();
                }
                default -> System.out.println("invalid choice");
            }
        }
    }

    private static void editRoomState() {
    }

    private static void getAllRoom() {
    }

    private static void deleteRoom() {
    }

    private static void editRoom() {
    }

    private static void addRoom() {
    }
}

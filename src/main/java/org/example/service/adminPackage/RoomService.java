package org.example.service.adminPackage;

import org.example.entity.Room;
import org.example.entity.enums.RoomState;
import org.example.entity.enums.RoomType;

import java.util.Objects;
import java.util.UUID;

import static org.example.db.Datasource.*;
import static org.example.service.adminPackage.UserService.*;

public class RoomService {
    static Room room = new Room();

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
            switch (choice) {
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
        System.out.println("""
                
                1: Available
                2: Occupied
                3: Cleaning
                
                Choose!
                """);
        int choice = intScanner.nextInt();
        switch (choice) {
            case 1 -> {
                room.setState(RoomState.AVAILABLE);
            }
            case 2 -> {
                room.setState(RoomState.OCCUPIED);
            }
            case 3 -> {
                room.setState(RoomState.CLEAN);
            }
            default -> System.out.println("invalid choice");
        }
    }

    private static void getAllRoom() {
        int i = 1;
        for (Room r : rooms) {
            System.out.println(i++ + ": " + r);
        }
    }

    private static void deleteRoom() {
        System.out.println("enter roomNumber");
        int choice = intScanner.nextInt();
        boolean has = false;
        for (Room r : rooms) {
            if (Objects.equals(r.getRoomNumber(), choice)) {
                rooms.remove(r);
                has = true;
            }
        }
        if (!has) {
            System.out.println("invalid choice");
            return;
        }
        System.out.println("room successfully removed");
    }

    private static void editRoom() {
        System.out.println("enter number of room to change");
        Integer number = intScanner.nextInt();

        for (Room r : rooms) {
            if (Objects.equals(r.getRoomNumber(), number)) {
                System.out.println("this number is not empty, choose another one");
                break;
            }
        }
        room.setRoomNumber(String.valueOf(number));

        System.out.println("enter price of room to change");
        room.setPrice(doubleScanner.nextDouble());

        System.out.println("enter capacity of room to change");
        room.setCapacity(intScanner.nextInt());

        System.out.println("room successfully changed");
    }

    private static void addRoom() {
        room.setId(UUID.randomUUID().toString());
        System.out.println("enter room number");
        String number = strScanner.nextLine();
        for (Room r : rooms) {
            if (Objects.equals(r.getRoomNumber(), number)) {
                System.out.println("this room is not empty, choose another room");
                break;
            }
        }
        room.setRoomNumber(number);

        System.out.println("enter price of room");
        Double price = doubleScanner.nextDouble();
        room.setPrice(price);

        System.out.println("enter capacity of this room");
        Integer capacity = intScanner.nextInt();
        room.setCapacity(capacity);

        System.out.println("enter floor of this room");
        Integer floor = intScanner.nextInt();
        room.setFloor(floor);

        room.setState(RoomState.AVAILABLE);

        System.out.println("enter type of room");
        System.out.println("""
                
                1: Standard
                2: Comfort
                3: Lux
                
                Choose!
                """);
        int choice = intScanner.nextInt();
        switch (choice) {
            case 1 -> {
                room.setRoomType(RoomType.STANDARD);
            }
            case 2 -> {
                room.setRoomType(RoomType.COMFORT);
            }
            case 3 -> {
                room.setRoomType(RoomType.LUX);
            }
            default -> System.out.println("invalid choice");
        }
        rooms.add(room);
        System.out.println("room successfully added");
    }
}
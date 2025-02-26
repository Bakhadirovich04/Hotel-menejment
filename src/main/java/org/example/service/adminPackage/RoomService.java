package org.example.service.adminPackage;

import org.example.entity.Room;
import org.example.entity.enums.RoomState;
import org.example.entity.enums.RoomType;

import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;

import static org.example.db.Datasource.*;
import static org.example.service.adminPackage.AdminService.intScanner;
import static org.example.service.adminPackage.AdminService.strScanner;
import static org.example.service.adminPackage.UserService.*;
import static org.example.service.adminPackage.UserService.doubleScanner;

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
        System.out.println("enter the room number to change state");
        String number = strScanner.nextLine();
        Room roomToEdit = findRoom(number);

        if (roomToEdit == null) {
            System.out.println("This room wasn't built yet");
            return;
        }

        System.out.println("""
                
                1: Available
                2: Occupied
                3: Cleaning
                
                Choose!
                """);

        int choice = intScanner.nextInt();
        intScanner.nextLine();
        switch (choice) {
            case 1 -> room.setState(RoomState.AVAILABLE);
            case 2 -> room.setState(RoomState.OCCUPIED);
            case 3 -> room.setState(RoomState.CLEAN);
            default -> System.out.println("invalid choice");
        }
    }

    private static Room findRoom(String number) {
        for (Room r : rooms) {
            if (Objects.equals(r.getRoomNumber(), number)) {
                return r;
            }
        }
        return null;
    }

    private static void getAllRoom() {
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        int i = 1;
        for (Room r : rooms) {
            System.out.println(i++ + ": " + r);
        }
    }

    private static void deleteRoom() {
        System.out.println("enter room number to delete");
        String choice = strScanner.nextLine();
        Iterator<Room> iterator = rooms.iterator();
        boolean has = false;

        while (iterator.hasNext()) {
            Room r = iterator.next();
            if (Objects.equals(r.getRoomNumber(), choice)) {
                iterator.remove();
                has = true;
                break;
            }
        }

        if (!has) {
            System.out.println("invalid choice, room not found");
        }
        System.out.println("room successfully removed");
    }

    private static void editRoom() {
        System.out.println("Enter the number of the room you want to change:");
        String number = strScanner.nextLine();
        Room roomToEdit = findRoom(number);

        if (roomToEdit == null) {
            System.out.println("This room wasn't built yet");
            return;
        }

        System.out.println("Enter the new price of the room:");
        if (doubleScanner.hasNextDouble()) {
            roomToEdit.setPrice(doubleScanner.nextDouble());
            doubleScanner.nextLine();
            System.out.println("Price of the room successfully changed.");
        } else {
            System.out.println("Invalid price input.");
            doubleScanner.nextLine();
        }
    }


    private static void addRoom() {
        room.setId(UUID.randomUUID().toString());
        System.out.println("enter room number");
        String number = strScanner.nextLine();

        if (findRoom(number) != null) {
            System.out.println("This room is already taken, choose another room.");
            return;
        }
        room.setRoomNumber(number);

        System.out.println("enter price of room");
        Double price = doubleScanner.nextDouble();
        if (price <= 0) {
            System.out.println("price cannot be 0 or less than");
            return;
        }
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
            default -> {
                System.out.println("invalid choice");
                return;
            }
        }
        rooms.add(room);
        System.out.println("room successfully added");
    }
}
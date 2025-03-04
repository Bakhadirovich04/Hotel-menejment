package org.example.service.userPackage;
import lombok.SneakyThrows;
import org.example.entity.Borrow;
import org.example.entity.History;
import org.example.entity.Room;
import org.example.entity.enums.BorrowType;
import org.example.entity.enums.RoomState;
import org.example.entity.enums.RoomType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.example.db.Datasource.*;
public class UserService {
    public static void service(){
        System.out.println("Welcome to User Service 😊!");
        while (true){
            System.out.println("""
                    0 exit
                    1 Get all rooms
                    2 Get available room
                    3 Order room
                    4 Cancel room
                    5 Profile
                    6 History
                    7 Balance
                    """);
            switch (intScanner.nextInt()){
                case 0->{return;}
                case 1->getAllRooms();
                case 2->getAvailableRoom();
                case 3->orderRoom();
                case 4->cancelRoom();
                case 5->profile();
                case 6->history();
                case 7->balance();
                default -> System.out.println("invalid option!");
            }
        }
    }



    private static void getAllRooms() {
        System.out.println("Rooms List: ");
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    private static void getAvailableRoom() {
        System.out.println("Available rooms: ");
        for (Room room : rooms) {
            if(room.getState().equals(RoomState.AVAILABLE)){
                System.out.println(room);
            }
        }
    }

    @SneakyThrows
    private static void orderRoom() {
        System.out.println("Enter room number");
        String roomNumber = strScanner.nextLine();
        boolean roomFound = false;
        for (Room room : rooms) {
            if(room.getState().equals(RoomState.AVAILABLE) && Objects.equals(room.getRoomNumber(), roomNumber)){
                roomFound = true;
                Borrow newBorrow = new Borrow();
                History history = new History();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");

                if(room.getCapacity()<=0){
                    System.out.println("not empty place");
                    return;
                }
                if(room.getPrice()>currentUser.getBalance()){
                    System.out.println("not enough money to order room");
                    return;
                }
                //System.out.println("Enter how many place");

                System.out.println("Enter how many days");
                int day = intScanner.nextInt();

                System.out.println("Enter date from: (dd-MM)");
                Date dateFrom = sdf.parse(strScanner.nextLine());

                System.out.println("Enter date to: (dd-MM)");
                Date dateTo = sdf.parse(strScanner.nextLine());
                long diffInMillies = Math.abs(dateTo.getTime() - dateFrom.getTime());
                long calculatedDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if (calculatedDays != day) {
                    System.out.println("The selected dates do not match the entered number of days.");
                    return;
                }

                for (Borrow borrow : borrows) {
                    if(borrow.getRoom().getRoomNumber().equals(roomNumber)){
                        Date existingDateFrom = borrow.getDateFrom();
                        Date existingDateTo = borrow.getDateTo();
                        if(!(dateTo.before(existingDateFrom) || dateFrom.after(existingDateTo))){
                            System.out.println("The room is already booked during the selected dates.");
                            return;
                        }
                    }
                }

                newBorrow.setId(UUID.randomUUID().toString());
                room.setRoomNumber(roomNumber);
                room.setId(room.getId());
                room.setPrice(room.getPrice());
                newBorrow.setRoom(room);
                newBorrow.setDay(day);
                newBorrow.setDateFrom(dateFrom);
                newBorrow.setDateTo(dateTo);
                newBorrow.setBorrowType(BorrowType.ACTIVE);
                room.setCapacity(room.getCapacity()-1);

                if(room.getCapacity().equals(0)){
                    room.setState(RoomState.OCCUPIED);
                }

                currentUser.setBalance(currentUser.getBalance()-room.getPrice());
                newBorrow.setUser(currentUser);
                history.setBorrow(newBorrow);
                borrows.add(newBorrow);
                histories.add(history);
                System.out.println("Successfully ordered!😊");
                //clean bo'lsa 1 kundan olish qo'shish kk
            }
        }
        if(!roomFound){
            System.out.println("invalid room number!");
        }
    }

    private static void cancelRoom() {
        System.out.println("Enter room number");
        String roomNumber = strScanner.nextLine();
        Borrow removedBorrow = null;
        for (Borrow borrow : borrows) {
            if(borrow.getUser().getId().equals(currentUser.getId())&&
                borrow.getRoom().getRoomNumber().equals(roomNumber)&&
                borrow.getBorrowType().equals(BorrowType.ACTIVE)){

                Room room = borrow.getRoom();
                if(room.getState().equals(RoomState.OCCUPIED)){
                    room.setState(RoomState.AVAILABLE);
                }
                room.setCapacity(room.getCapacity()+1);
                currentUser.setBalance(currentUser.getBalance()+room.getPrice()*0.75);
                borrow.setDateFrom(null);
                borrow.setDateTo(null);
                borrow.setBorrowType(BorrowType.FINISHED);
                removedBorrow = borrow;

            }
        }
        if (removedBorrow != null){
            borrows.remove(removedBorrow);
            System.out.println("Successfully cancelled!");
        }else {
            System.out.println("no active booking found with that room number!");
        }

    }

    private static void profile() {
        System.out.println(currentUser);
    }

    private static void history() {
        System.out.println("Your booking history: ");
        boolean hasHistory = false;

        for (History history : histories) {
            if (history.getBorrow().getUser().getId().equals(currentUser.getId())) {
                System.out.println(history.getBorrow());
                hasHistory = true;
            }
        }
        if(!hasHistory){
            System.out.println("No any booking history found!");
        }
    }

    private static void balance() {
        System.out.println("""
                0 back
                1 show balance
                2 fill balance
                """);
        switch (intScanner.nextInt()){
            case 0 -> {
                return;
            }
            case 1-> System.out.println("Balance : " + currentUser.getBalance());
            case 2-> fillBalance();
        }
    }

    private static void fillBalance() {
        System.out.println("Enter amount");
        double amount = doubleScanner.nextDouble();
        if(amount<0){
            System.out.println("fill appropriate money😒");
            return;
        }
        currentUser.setBalance(currentUser.getBalance()+ amount);
    }
}

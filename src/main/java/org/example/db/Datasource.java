package org.example.db;

import com.github.javafaker.Faker;
import org.example.entity.Borrow;
import org.example.entity.History;
import org.example.entity.Room;
import org.example.entity.User;
import org.example.entity.enums.BorrowType;
import org.example.entity.enums.Role;
import org.example.entity.enums.RoomState;
import org.example.entity.enums.RoomType;

import java.util.*;

public class Datasource {
    public static Scanner strScanner = new Scanner(System.in);
    public static Scanner intScanner = new Scanner(System.in);
    public static User user;

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static List<User> users = new ArrayList<>();
    public static List<Room> rooms = new ArrayList<>();
    public static List<Borrow> borrows = new ArrayList<>();
    public static List<History> histories = new ArrayList<>();

    static {
        // Admin user qo'shish
        users.add(new User(UUID.randomUUID().toString(), "Admin", "admin", "admin@gmail.com", "admin", "987654321", "Uzb, Toshkent", Role.ADMIN, 1000d));

        // 5 ta soxta User yaratish
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setName(faker.name().firstName());
            user.setSurname(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.internet().password(8, 12));
            user.setPhoneNumber(faker.phoneNumber().cellPhone());
            user.setFullAddress(faker.address().fullAddress());
            Role[] allowedRoles = {Role.GUEST, Role.USER};
            user.setRole(allowedRoles[random.nextInt(allowedRoles.length)]);

            user.setBalance(random.nextDouble() * 1000);

            users.add(user);
        }

        // 5 ta soxta Room yaratish
        for (int i = 0; i < 5; i++) {
            Room room = new Room();
            room.setId(UUID.randomUUID().toString());
            room.setRoomNumber("R" + (i + 1) + faker.number().digits(2));
            room.setPrice((double) (random.nextInt() * 500 + 50));
            room.setCapacity(random.nextInt(4) + 1);
            room.setFloor(random.nextInt(10) + 1);
            room.setState(RoomState.values()[random.nextInt(RoomState.values().length)]);
            room.setRoomType(RoomType.values()[random.nextInt(RoomType.values().length)]);

            rooms.add(room);
        }

        // 5 ta soxta Borrow yaratish
        for (int i = 0; i < 5; i++) {
            Borrow borrow = new Borrow();
            borrow.setId(UUID.randomUUID().toString());
            borrow.setUser(users.get(random.nextInt(users.size())));
            borrow.setRoom(rooms.get(random.nextInt(rooms.size())));
            borrow.setDay(random.nextInt(7) + 1);
            borrow.setDateFrom(faker.date().past(30, java.util.concurrent.TimeUnit.DAYS));
            borrow.setDateTo(faker.date().future(30, java.util.concurrent.TimeUnit.DAYS));
            borrow.setBorrowType(BorrowType.values()[random.nextInt(BorrowType.values().length)]);

            borrows.add(borrow);
        }

        // 5 ta soxta History yaratish
        for (int i = 0; i < 5; i++) {
            History history = new History();
            history.setId(UUID.randomUUID().toString());
            history.setBorrow(borrows.get(random.nextInt(borrows.size())));

            histories.add(history);
        }
    }
}
package org.example.service.adminPackage;

import lombok.RequiredArgsConstructor;
import org.example.entity.enums.BorrowType;
import org.example.entity.Borrow;
import org.example.entity.User;
import org.example.entity.Room;
import org.example.entity.enums.Role;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BorrowService {
    private final List<Borrow> borrowList = new ArrayList<>();
    private final Map<String, User> userDatabase = new HashMap<>(); // Хранилище пользователей
    private final Scanner scanner = new Scanner(System.in);

    public void addBorrow(Borrow borrow) {
        System.out.print("Введите ID пользователя: ");
        String userId = scanner.nextLine();

        User user = userDatabase.get(userId);
        if (user == null) {
            System.out.println("Пользователь не найден. Администратор должен создать профиль.");
            user = createUser(userId);
        } else {
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();
            if (!user.getPassword().equals(password)) {
                System.out.println("Неверный пароль. Бронь невозможна.");
                return;
            }
        }

        double cost = calculateBorrowCost(borrow);
        if (user.getBalance() < cost) {
            System.out.println("Бронь невозможна: недостаточно средств у пользователя");
            return;
        }

        // Списываем средства у пользователя
        user.setBalance(user.getBalance() - cost);
        borrow.setUser(user);
        borrowList.add(borrow);
    }

    private User createUser(String userId) {
        System.out.print("Введите имя: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Введите фамилию: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Введите email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Введите номер телефона: ");
        String phoneNumber = scanner.nextLine().trim();

        System.out.print("Введите полный адрес: ");
        String fullAddress = scanner.nextLine().trim();

        System.out.print("Введите роль (например, USER или ADMIN): ");
        Role role;
        try {
            role = Role.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректная роль. Устанавливаю USER по умолчанию.");
            role = Role.USER;
        }

        double balance = 0;
        while (true) {
            System.out.print("Введите начальный баланс: ");
            try {
                balance = Double.parseDouble(scanner.nextLine().trim());
                if (balance < 0) {
                    System.out.println("Баланс не может быть отрицательным. Попробуйте снова.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Введите число.");
            }
        }

        System.out.print("Создайте пароль: ");
        String password = scanner.nextLine().trim();

        User newUser = new User(
                userId, firstName, lastName, email, password, phoneNumber, fullAddress, role, balance
        );

        userDatabase.put(userId, newUser);
        System.out.println("Пользователь успешно создан.");
        return newUser;
    }

    private double calculateBorrowCost(Borrow borrow) {
        return borrow.getDay() * 10.0;
    }

    public Optional<Borrow> getBorrowById(String id) {
        return borrowList.stream()
                .filter(borrow -> borrow.getId().equals(id))
                .findFirst();
    }

    public List<Borrow> getBorrowsByUser(User user) {
        return borrowList.stream()
                .filter(borrow -> borrow.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public List<Borrow> getBorrowsByRoom(Room room) {
        return borrowList.stream()
                .filter(borrow -> borrow.getRoom().equals(room))
                .collect(Collectors.toList());
    }

    public List<Borrow> getBorrowsByType(BorrowType type) {
        return borrowList.stream()
                .filter(borrow -> borrow.getBorrowType() == type)
                .collect(Collectors.toList());
    }

    public void removeBorrow(String id) {
        borrowList.removeIf(borrow -> borrow.getId().equals(id));
    }

    public List<Borrow> getBorrowsBetweenDates(Date from, Date to) {
        return borrowList.stream()
                .filter(borrow -> borrow.getDateFrom() != null && borrow.getDateTo() != null)
                .filter(borrow -> (borrow.getDateFrom().after(from) || borrow.getDateFrom().equals(from)) &&
                        (borrow.getDateTo().before(to) || borrow.getDateTo().equals(to)))
                .collect(Collectors.toList());
    }
}

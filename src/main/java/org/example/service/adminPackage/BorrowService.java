
package org.example.service.adminPackage;

import lombok.RequiredArgsConstructor;
import org.example.entity.enums.BorrowType;
import org.example.entity.Borrow;
import org.example.entity.User;
import org.example.entity.Room;
import org.example.entity.enums.Role;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public class BorrowService {
    private final List<Borrow> borrowList = new ArrayList<>();
    private final Map<String, User> userDatabase = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public void startMenu() {
        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addNewBorrow();
                    break;
                case "2":
                    findBorrowById();
                    break;
                case "3":
                    findBorrowsByUser();
                    break;
                case "4":
                    findBorrowsByRoom();
                    break;
                case "5":
                    findBorrowsByType();
                    break;
                case "6":
                    removeBorrowById();
                    break;
                case "7":
                    findBorrowsByDateRange();
                    break;
                case "0":
                    System.out.println("Выход из системы...");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Система управления бронированиями ===");
        System.out.println("1. Добавить новое бронирование");
        System.out.println("2. Найти бронирование по ID");
        System.out.println("3. Показать бронирования пользователя");
        System.out.println("4. Показать бронирования комнаты");
        System.out.println("5. Показать бронирования по типу");
        System.out.println("6. Удалить бронирование");
        System.out.println("7. Показать бронирования за период");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void addNewBorrow() {
        System.out.print("Введите ID бронирования: ");
        String id = scanner.nextLine().trim();

        System.out.print("Введите количество дней: ");
        int days;
        try {
            days = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Неверное количество дней. Операция отменена.");
            return;
        }

        Borrow borrow = new Borrow();
        borrow.setId(id);
        borrow.setDay(days);
        addBorrow(borrow);
        System.out.println("Бронирование успешно добавлено!");
    }

    private void findBorrowById() {
        System.out.print("Введите ID бронирования: ");
        String id = scanner.nextLine().trim();
        Optional<Borrow> borrow = getBorrowById(id);
        if (borrow.isPresent()) {
            System.out.println("Найденное бронирование: " + borrow.get());
        } else {
            System.out.println("Бронирование не найдено.");
        }
    }

    private void findBorrowsByUser() {
        System.out.print("Введите ID пользователя: ");
        String userId = scanner.nextLine().trim();
        User user = userDatabase.get(userId);
        if (user != null) {
            List<Borrow> borrows = getBorrowsByUser(user);
            printBorrowList(borrows);
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    private void findBorrowsByRoom() {
        System.out.print("Введите ID комнаты: ");
        String roomId = scanner.nextLine().trim();
        Room room = new Room(); // Предполагаем, что Room имеет конструктор по умолчанию
        room.setId(roomId);     // Устанавливаем ID комнаты
        List<Borrow> borrows = getBorrowsByRoom(room);
        printBorrowList(borrows);
    }

    private void findBorrowsByType() {
        System.out.print("Введите тип бронирования (например, DAILY): ");
        try {
            BorrowType type = BorrowType.valueOf(scanner.nextLine().trim().toUpperCase());
            List<Borrow> borrows = getBorrowsByType(type);
            printBorrowList(borrows);
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный тип бронирования.");
        }
    }

    private void removeBorrowById() {
        System.out.print("Введите ID бронирования для удаления: ");
        String id = scanner.nextLine().trim();
        removeBorrow(id);
        System.out.println("Бронирование удалено (если существовало).");
    }

    private void findBorrowsByDateRange() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            System.out.print("Введите дату начала (dd.MM.yyyy): ");
            Date from = sdf.parse(scanner.nextLine().trim());
            System.out.print("Введите дату окончания (dd.MM.yyyy): ");
            Date to = sdf.parse(scanner.nextLine().trim());
            List<Borrow> borrows = getBorrowsBetweenDates(from, to);
            printBorrowList(borrows);
        } catch (Exception e) {
            System.out.println("Ошибка ввода даты. Используйте формат dd.MM.yyyy");
        }
    }

    private void printBorrowList(List<Borrow> borrows) {
        if (borrows.isEmpty()) {
            System.out.println("Бронирования не найдены.");
        } else {
            System.out.println("Найденные бронирования:");
            borrows.forEach(System.out::println);
        }
    }

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
                .filter(borrow -> borrow.getRoom() != null && borrow.getRoom().equals(room))
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
package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);

    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> trackableItems = new ArrayList<>();

    // Коробки для разных типов посылок (вес в граммах)
    private static ParcelBox<StandardParcel> standardBox = new ParcelBox<>(100000); // 100 кг
    private static ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(50000);    // 50 кг
    private static ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(80000); // 80 кг

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        addParcel();
                        break;
                    case 2:
                        sendParcels();
                        break;
                    case 3:
                        calculateCosts();
                        break;
                    case 4:
                        reportStatus();
                        break;
                    case 5:
                        showBoxContents();
                        break;
                    case 6:
                        checkPerishableParcels();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("❌ Неверный выбор. Пожалуйста, выберите пункт из меню.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Ошибка: введите число от 0 до 6");
            }
        }
        System.out.println("До свидания!");
    }

    private static void showMenu() {
        System.out.println("\n=== СЛУЖБА ДОСТАВКИ SHIPIT ===");
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Отчет о местоположении (трекинг)");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("6 — Проверить скоропортящиеся посылки");
        System.out.println("0 — Завершить работу");
        System.out.print("Ваш выбор: ");
    }

    private static void addParcel() {
        System.out.println("\n--- ДОБАВЛЕНИЕ НОВОЙ ПОСЫЛКИ ---");
        System.out.println("Выберите тип посылки:");
        System.out.println("1 — Стандартная");
        System.out.println("2 — Хрупкая");
        System.out.println("3 — Скоропортящаяся");

        int type = getValidatedInt("Тип посылки (1-3): ", 1, 3);

        System.out.println("Введите описание посылки:");
        String description = scanner.nextLine();

        // Ввод веса с валидацией
        System.out.println("⚠️  Внимание: вес указывается в ГРАММАХ (только цифры)");
        int weight = getValidatedInt("Вес посылки (в граммах): ", 1, 1000000);

        System.out.println("Введите адрес доставки:");
        String address = scanner.nextLine();

        // Ввод дня отправки
        int sendDay = getValidatedInt("День отправки (число от 1 до 31): ", 1, 31);

        switch (type) {
            case 1:
                StandardParcel standardParcel = new StandardParcel(description, weight, address, sendDay);
                allParcels.add(standardParcel);
                standardBox.addParcel(standardParcel);
                break;
            case 2:
                FragileParcel fragileParcel = new FragileParcel(description, weight, address, sendDay);
                allParcels.add(fragileParcel);
                trackableItems.add(fragileParcel);
                fragileBox.addParcel(fragileParcel);
                break;
            case 3:
                System.out.println("⚠️  Внимание: укажите срок годности в ДНЯХ");
                int timeToLive = getValidatedInt("Срок годности (в днях): ", 1, 31);
                PerishableParcel perishableParcel = new PerishableParcel(description, weight, address, sendDay, timeToLive);
                allParcels.add(perishableParcel);
                perishableBox.addParcel(perishableParcel);
                break;
        }

        System.out.println("✅ Посылка успешно добавлена!");
    }

    private static void sendParcels() {
        if (allParcels.isEmpty()) {
            System.out.println("❌ Нет посылок для отправки.");
            return;
        }

        System.out.println("\n--- ОТПРАВКА ПОСЫЛОК ---");
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
        System.out.println("✅ Все посылки отправлены!");
    }

    private static void calculateCosts() {
        if (allParcels.isEmpty()) {
            System.out.println("❌ Нет посылок для расчета стоимости.");
            return;
        }

        int totalCost = 0;
        System.out.println("\n--- РАСЧЕТ СТОИМОСТИ ДОСТАВКИ ---");
        for (Parcel parcel : allParcels) {
            int cost = parcel.calculateDeliveryCost();
            System.out.println("Посылка <<" + parcel.getDescription() + ">> (" +
                    parcel.getFormattedWeight() + "): " + cost + " руб.");
            totalCost += cost;
        }
        System.out.println("📊 Общая стоимость доставки всех посылок: " + totalCost + " руб.");
    }

    private static void reportStatus() {
        if (trackableItems.isEmpty()) {
            System.out.println("❌ Нет отслеживаемых посылок.");
            return;
        }

        System.out.println("Введите новое местоположение:");
        String location = scanner.nextLine();

        System.out.println("\n--- ОТЧЕТ О МЕСТОПОЛОЖЕНИИ ---");
        for (Trackable item : trackableItems) {
            item.reportStatus(location);
        }
    }

    private static void showBoxContents() {
        System.out.println("\n--- СОДЕРЖИМОЕ КОРОБОК ---");
        System.out.println("Выберите тип коробки:");
        System.out.println("1 — Стандартные посылки");
        System.out.println("2 — Хрупкие посылки");
        System.out.println("3 — Скоропортящиеся посылки");

        int choice = getValidatedInt("Тип коробки (1-3): ", 1, 3);

        switch (choice) {
            case 1:
                printBoxContents("Стандартные посылки", standardBox);
                break;
            case 2:
                printBoxContents("Хрупкие посылки", fragileBox);
                break;
            case 3:
                printBoxContents("Скоропортящиеся посылки", perishableBox);
                break;
        }
    }

    private static void checkPerishableParcels() {
        System.out.println("\n--- ПРОВЕРКА СКОРОПОРТЯЩИХСЯ ПОСЫЛОК ---");
        int currentDay = getValidatedInt("Введите текущий день месяца (1-31): ", 1, 31);

        boolean foundExpired = false;
        for (Parcel parcel : allParcels) {
            if (parcel instanceof PerishableParcel) {
                PerishableParcel perishable = (PerishableParcel) parcel;
                if (perishable.isExpired(currentDay)) {
                    System.out.println("❌ ПРОСРОЧЕНА: " + perishable.getDescription() +
                            " (отправлена " + perishable.getSendDay() +
                            ", срок годности " + perishable.getTimeToLive() + " дней)");
                    foundExpired = true;
                } else {
                    System.out.println("✅ Годна: " + perishable.getDescription() +
                            " (годна до " + perishable.getExpirationDay() + " числа)");
                }
            }
        }

        if (!foundExpired) {
            System.out.println("✅ Все скоропортящиеся посылки годны к употреблению.");
        }
    }

    // Вспомогательные методы для валидации

    private static int getValidatedInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("❌ Ошибка: введите число от " + min + " до " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Ошибка: введите целое число (только цифры)");
            }
        }
    }

    private static <T extends Parcel> void printBoxContents(String title, ParcelBox<T> box) {
        System.out.println("\n--- " + title.toUpperCase() + " ---");
        List<T> parcels = box.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("Коробка пуста.");
        } else {
            for (T parcel : parcels) {
                System.out.println("• " + parcel.getDescription() +
                        " (вес: " + parcel.getFormattedWeight() +
                        ", отправка: " + parcel.getSendDay() + " число)");
            }
            System.out.println("📦 Заполнение: " + box.getCurrentWeight()/1000.0 + " кг / " + box.getMaxWeight()/1000.0 + " кг");
        }
    }
}


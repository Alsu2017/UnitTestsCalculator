package org.example;
import java.util.Scanner;

public class DeliveryCostCalculator {

    public static double calculateDeliveryCost(double distance, String size, boolean isFragile, String loadLevel) {
        double baseCost = 0;

        // Рассчитываем базовую стоимость в зависимости от расстояния
        if (distance > 30) {
            baseCost += 300;
        } else if (distance > 10) {
            baseCost += 200;
        } else if (distance > 2) {
            baseCost += 100;
        } else {
            baseCost += 50;
        }

        // Проверка на хрупкость
        if (isFragile) {
            if (distance > 30) {
                throw new IllegalArgumentException("Хрупкие грузы нельзя возить на расстояние более 30 км.");
            }
            baseCost += 300;
        }

        // Добавляем стоимость в зависимости от габаритов
        if (size.equalsIgnoreCase("большие")) {
            baseCost += 200;
        } else if (size.equalsIgnoreCase("маленькие")) {
            baseCost += 100;
        }

        // Определяем коэффициент загрузки
        double loadCoefficient;
        switch (loadLevel.toLowerCase()) {
            case "очень высокая":
                loadCoefficient = 1.6;
                break;
            case "высокая":
                loadCoefficient = 1.4;
                break;
            case "повышенная":
                loadCoefficient = 1.2;
                break;
            default:
                loadCoefficient = 1.0;
                break;
        }

        // Рассчитываем итоговую стоимость
        double totalCost = baseCost * loadCoefficient;

        // Проверяем минимальную сумму доставки
        if (totalCost < 400) {
            return 400;
        }

        return totalCost;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите расстояние в км: ");
        double distance = scanner.nextDouble();

        System.out.print("Введите размер груза (большие/маленькие): ");
        String size = scanner.next();

        System.out.print("Является ли груз хрупким? (true/false): ");
        boolean isFragile = scanner.nextBoolean();

        System.out.print("Введите уровень загруженности (очень высокая/высокая/повышенная/нормальная): ");
        String loadLevel = scanner.next();

        try {
            double cost = calculateDeliveryCost(distance, size, isFragile, loadLevel);
            System.out.println("Стоимость доставки: " + cost + " рублей");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

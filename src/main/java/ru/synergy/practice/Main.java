package ru.synergy.practice;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int count = readValue(scanner, "Количество сотрудников: ", Integer::parseInt, value -> value > 0);
            List<Worker> workers = new ArrayList<>(count);

            for (int i = 0; i < count; i++) {
                System.out.printf("%nСотрудник %d%n", i + 1);
                String name = readText(scanner, "Фамилия и инициалы: ");
                String position = readText(scanner, "Должность: ");
                BigDecimal salary = readValue(
                        scanner,
                        "Зарплата, руб.: ",
                        value -> new BigDecimal(value.replace(',', '.')),
                        value -> value.signum() >= 0
                );
                int currentYear = Year.now().getValue();
                int employmentYear = readValue(
                        scanner,
                        "Год поступления на работу: ",
                        Integer::parseInt,
                        value -> value >= 1900 && value <= currentYear
                );
                workers.add(new Worker(name, position, salary, employmentYear));
            }

            int minimumExperience = readValue(
                    scanner,
                    "Минимальный стаж, лет: ",
                    Integer::parseInt,
                    value -> value >= 0
            );
            List<Worker> result = WorkerService.findWithExperienceGreaterThan(
                    workers,
                    minimumExperience,
                    Year.now().getValue()
            );

            if (result.isEmpty()) {
                System.out.println("Сотрудников с указанным стажем не найдено.");
                return;
            }

            System.out.println("Фамилии работников со стажем больше заданного:");
            result.forEach(worker -> System.out.println(worker.getSurnameAndInitials()));
        }
    }

    private static String readText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Значение не может быть пустым.");
        }
    }

    private static <T> T readValue(
            Scanner scanner,
            String prompt,
            Function<String, T> parser,
            java.util.function.Predicate<T> validator
    ) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                T value = parser.apply(input);
                if (validator.test(value)) {
                    return value;
                }
            } catch (RuntimeException ignored) {
                // Пользователь получает единое понятное сообщение и повторяет ввод.
            }
            System.out.println("Некорректное значение. Повторите ввод.");
        }
    }
}

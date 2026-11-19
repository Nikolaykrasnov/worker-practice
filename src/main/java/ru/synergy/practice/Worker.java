package ru.synergy.practice;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Objects;

public class Worker {
    private String surnameAndInitials;
    private String position;
    private BigDecimal salary;
    private int employmentYear;

    public Worker() {
        this("Не указано", "Не указана", BigDecimal.ZERO, Year.now().getValue());
    }

    public Worker(String surnameAndInitials, String position) {
        this(surnameAndInitials, position, BigDecimal.ZERO, Year.now().getValue());
    }

    public Worker(String surnameAndInitials, String position, BigDecimal salary, int employmentYear) {
        setSurnameAndInitials(surnameAndInitials);
        setPosition(position);
        setSalary(salary);
        setEmploymentYear(employmentYear);
    }

    public String getSurnameAndInitials() {
        return surnameAndInitials;
    }

    public void setSurnameAndInitials(String surnameAndInitials) {
        if (surnameAndInitials == null || surnameAndInitials.isBlank()) {
            throw new IllegalArgumentException("Фамилия и инициалы не могут быть пустыми");
        }
        this.surnameAndInitials = surnameAndInitials.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if (position == null || position.isBlank()) {
            throw new IllegalArgumentException("Должность не может быть пустой");
        }
        this.position = position.trim();
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        Objects.requireNonNull(salary, "Зарплата не может быть null");
        if (salary.signum() < 0) {
            throw new IllegalArgumentException("Зарплата не может быть отрицательной");
        }
        this.salary = salary;
    }

    public int getEmploymentYear() {
        return employmentYear;
    }

    public void setEmploymentYear(int employmentYear) {
        int currentYear = Year.now().getValue();
        if (employmentYear < 1900 || employmentYear > currentYear) {
            throw new IllegalArgumentException("Год поступления должен быть от 1900 до " + currentYear);
        }
        this.employmentYear = employmentYear;
    }

    public int getExperienceYears(int currentYear) {
        if (currentYear < employmentYear) {
            throw new IllegalArgumentException("Расчётный год не может быть раньше года поступления");
        }
        return currentYear - employmentYear;
    }

    public String display() {
        return "%s, должность: %s, зарплата: %s руб., год поступления: %d"
                .formatted(surnameAndInitials, position, salary.toPlainString(), employmentYear);
    }

    @Override
    public String toString() {
        return display();
    }
}

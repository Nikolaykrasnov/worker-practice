package ru.synergy.practice;

import java.util.List;

public final class WorkerService {
    private WorkerService() {
    }

    public static List<Worker> findWithExperienceGreaterThan(
            List<Worker> workers,
            int minimumExperience,
            int currentYear
    ) {
        if (minimumExperience < 0) {
            throw new IllegalArgumentException("Стаж не может быть отрицательным");
        }
        return workers.stream()
                .filter(worker -> worker.getExperienceYears(currentYear) > minimumExperience)
                .toList();
    }
}

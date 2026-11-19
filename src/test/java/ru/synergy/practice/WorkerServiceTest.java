package ru.synergy.practice;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WorkerServiceTest {
    @Test
    void returnsOnlyWorkersWhoseExperienceIsStrictlyGreater() {
        List<Worker> workers = List.of(
                new Worker("Иванов И.И.", "Преподаватель", new BigDecimal("80000"), 2015),
                new Worker("Петров П.П.", "Методист", new BigDecimal("65000"), 2020),
                new Worker("Сидоров С.С.", "Разработчик", new BigDecimal("120000"), 2023)
        );

        List<Worker> result = WorkerService.findWithExperienceGreaterThan(workers, 5, 2026);

        assertEquals(List.of("Иванов И.И.", "Петров П.П."),
                result.stream().map(Worker::getSurnameAndInitials).toList());
    }

    @Test
    void returnsEmptyListWhenNoWorkersMatch() {
        Worker worker = new Worker("Сидоров С.С.", "Разработчик", new BigDecimal("120000"), 2023);

        assertEquals(List.of(), WorkerService.findWithExperienceGreaterThan(List.of(worker), 10, 2026));
    }

    @Test
    void rejectsInvalidWorkerData() {
        assertThrows(IllegalArgumentException.class,
                () -> new Worker("", "Методист", BigDecimal.ONE, 2020));
        assertThrows(IllegalArgumentException.class,
                () -> new Worker("Иванов И.И.", "Методист", new BigDecimal("-1"), 2020));
    }
}

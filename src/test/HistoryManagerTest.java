package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilManager.HistoryManager;
import utilManager.Managers;
import utilManager.Status;
import utilManager.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    private HistoryManager historyManager;

    @BeforeEach
    public void beforeEach() {
        historyManager = Managers.getDefaultHistory(); // Предполагаем, что у вас есть реализация HistoryManager
    }

    @Test
    public void testTaskHistoryPreservation() {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        Task task2 = new Task("Task 2", "Description 2", Status.NEW);
        Task task3 = new Task("Task 3", "Description 3", Status.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        List<Task> history = historyManager.getHistory();
        //История должна содержать 3 задачи.
        assertEquals(3, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
        assertEquals(task3, history.get(2));

        // Проверяем, что изменения в задачах не влияют на сохраненные версии
        task1.setTitle("Updated Task 1");
        task1.setDescription("Updated Description 1");

        assertNotEquals("Updated Task 1", history.get(0).getTitle());
        assertNotEquals("Updated Description 1", history.get(0).getDescription());
    }
}
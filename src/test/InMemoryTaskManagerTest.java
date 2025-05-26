package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;
import managers.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;
    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault(); // создание экземпляра менеджера задач до каждого теста
    }

    @Test
    void shouldBePositiveAddTask() {
        Task task = new Task("Test Task", "Description of test task", Status.NEW);
        long taskId = taskManager.addTask(task);

        // Проверяем, что задача добавлена и имеет корректный ID
        assertNotEquals(-1, taskId);
        assertEquals(task, taskManager.getTaskById(taskId));
    }
    @Test
    void shouldBePositiveAddEpic() {
        Epic epic = new Epic("Test Epic", "Description of test epic");
        long epicId = taskManager.addEpic(epic);

        // Проверяем, что эпик добавлен и доступен по ID
        assertNotEquals(-1, epicId);
        assertEquals(epic, taskManager.getEpicById(epicId));
    }

    @Test
    void shouldBePositiveAddSubtask() {
        Epic epic = new Epic("Test Epic", "Description of test epic");
        long epicId = taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Test Subtask", "Description of test subtask", Status.NEW, epicId);
        long subtaskId = taskManager.addSubtask(subtask);

        // Проверяем, что подзадача добавлена и доступна по ID
        assertNotEquals(-1, subtaskId);
        assertEquals(subtask, taskManager.getSubtaskById(subtaskId));

        // Проверяем, что подзадача добавлена в эпик
        assertTrue(epic.getSubtasks().contains(subtask));
    }
    @Test
    public void shouldBeTrueImmutabilityOnAdd() {
        Task task = new Task("Test", "test descr", Status.NEW);
        String name = task.getTitle();
        String desc = task.getDescription();
        Status status = task.getStatus();

        taskManager.addTask(task);

        Assertions.assertEquals(name, taskManager.getTaskById(1).getTitle());
        Assertions.assertEquals(desc, taskManager.getTaskById(1).getDescription());
        Assertions.assertEquals(status, taskManager.getTaskById(1).getStatus());
    }

}
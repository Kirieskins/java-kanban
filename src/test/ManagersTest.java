package test;

import org.junit.jupiter.api.Test;
import utilManager.Managers;
import utilManager.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    public void shouldNotBeNull(){
        TaskManager taskManager = Managers.getDefault();

        // Проверяем, что менеджер задач не равен null
        assertNotNull(taskManager);
    }

}
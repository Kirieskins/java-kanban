package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.*;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    Epic epic;
    @BeforeEach
     public void beforeEach(){
        epic = new Epic("EPIC 1", "Desc of epic1");
    }
    @Test
    public void shouldNotBeEmptyWhenSubtasksAdd(){
        Subtask subtask = new Subtask("Sub1", "Sub1", Status.NEW, epic.getCurrentId());
        epic.addSubtask(subtask);
        assertFalse(epic.getSubtasks().isEmpty());
    }
}
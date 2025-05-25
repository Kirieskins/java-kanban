package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utilManager.Epic;
import utilManager.Status;
import utilManager.Subtask;
import utilManager.Task;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    public void shouldBeEqualsIfIdEquals(){
        Task task1 = new Task("task","task", Status.NEW);
        Task task2 = new Task("task","task", Status.NEW);

        task1.setCurrentId(2);
        task2.setCurrentId(2);
        Assertions.assertEquals(task1, task2);
    }
    @Test
    public void shouldBeEqualsIfIdInSubtasksEquals(){
        Subtask subtask1 = new Subtask("task","task", Status.NEW,0);
        Subtask subtask2 = new Subtask("task","task", Status.NEW,0);

        subtask1.setCurrentId(2);
        subtask2.setCurrentId(2);

        Assertions.assertEquals(subtask1, subtask2);
    }

}
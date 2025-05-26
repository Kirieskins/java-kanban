package managers;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;
import tasks.*;

import java.util.List;

public interface TaskManager{

    // a. Получение списка всех задач.
     List<Task> getAllTasks();
     List<Task> getAllEpics();
     List<Task> getAllSubtasks();

    // b. Удаление всех задач.
     void deleteAllTasks();
     void deleteAllEpics();
     void deleteAllSubtasks();
    // c. Получение по идентификатору.
    Task getTaskById(long id);
    Epic getEpicById(long id);
    Subtask getSubtaskById(long id);
    // d. Создание. Сам объект должен передаваться в качестве параметра.
    long addTask(Task task);

    long addEpic(Epic epic);

    // Добавляем подзадачу в эпик, которому она принадлежит
    long addSubtask(Subtask subtask);
    // e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    boolean updateTask(Task task);
    boolean updateEpic(Epic epic);

    boolean updateSubtask(Subtask subtask);
    // f. Удаление по идентификатору.
    void deleteTaskById(long id);

    void deleteEpicById(long id);
    void deleteSubtaskById(long id);

    //Дополнительные методы:
    //a. Получение списка всех подзадач определённого эпика.

    List<Subtask> getSubtasksByEpicId(long epicId);

    // Обновление статуса эпика в зависимости от статусов подзадач
    default void updateEpicStatus(Epic epic) {
        List<Subtask> currentSubtasks = epic.getSubtasks();
        if (currentSubtasks.isEmpty()) {
            epic.setStatus(Status.NEW);
        } else {
            boolean allDone = true;
            boolean anyInProgress = false;

            for (Subtask subtask : currentSubtasks) {
                if (subtask.getStatus() == Status.NEW) {
                    allDone = false;
                } else if (subtask.getStatus() == Status.IN_PROGRESS) {
                    anyInProgress = true;
                    allDone = false;
                }
            }

            if (allDone) {
                epic.setStatus(Status.DONE);
            } else if (anyInProgress) {
                epic.setStatus(Status.IN_PROGRESS);
            } else {
                epic.setStatus(Status.NEW);
            }
        }
    }
    List<Task> getHistory();
}

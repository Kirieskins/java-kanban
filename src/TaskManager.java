import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private HashMap<Long, Task> tasks; // для хранения задач по id
    private HashMap<Long, Epic> epics; // для хранения эпиков по id
    private HashMap<Long, Subtask> subtasks; //  для хранения сабтасков по id

    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    // a. Получение списка всех задач.
    public List<Task> getAllTasks(){
        return new ArrayList<>(tasks.values());
    }
    public List<Task> getAllEpics(){
        return new ArrayList<>(epics.values());
    }
    public List<Task> getAllSubtasks(){
        return new ArrayList<>(subtasks.values());
    }

    // b. Удаление всех задач.
    public void deleteAllTasks(){
        tasks.clear();
    }
    public void deleteAllEpics(){
        epics.clear();
    }
    public void deleteAllSubtasks(){
        subtasks.clear();
    }
    // c. Получение по идентификатору.
    public Task getTaskById(long id) {
        return tasks.get(id);
    }
    public Epic getEpicById(long id) {
        return epics.get(id);
    }
    public Subtask getSubtaskById(long id) {
        return subtasks.get(id);
    }
    // d. Создание. Сам объект должен передаваться в качестве параметра.
    public void addTask(Task task) {
        tasks.put(task.getCurrentId(), task);
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getCurrentId(), epic);
    }

    // Добавляем подзадачу в эпик, которому она принадлежит
    public void addSubtask(Subtask subtask, Epic epic) {
        subtasks.put(subtask.getCurrentId(), subtask);

        if (epic != null) {
            epic.addSubtask(subtask);
        }
    }
    // e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    public void updateTask(Task task) {
        tasks.replace(task.getCurrentId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.replace(epic.getCurrentId(), epic);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.replace(subtask.getCurrentId(), subtask);
    }
    // f. Удаление по идентификатору.
    public void deleteTaskById(long id) {
        tasks.remove(id);
    }

    public void deleteEpicById(long id) {
        epics.remove(id);
    }
    public void deleteSubtaskById(long id) {
        subtasks.remove(id);
        // Удаление подзадачи также из соответствующего эпика
        for (Epic epic : epics.values()) {
            epic.removeSubtaskById(id);
        }
    }

    //Дополнительные методы:
    //a. Получение списка всех подзадач определённого эпика.

    public List<Subtask> getSubtasksByEpicId(long epicId) {
        Epic epic = getEpicById(epicId);
        return epic != null ? epic.getSubtasks() : new ArrayList<>();
    }

    // Обновление статуса эпика в зависимости от статусов подзадач
    public void updateEpicStatus(Epic epic) {
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
    // печать текущих задач
    public void printTasks() {
        System.out.println("Tasks:");
        for (Task task : tasks.values()) {
            System.out.println(task);
        }
    }

    public void printEpics() {
        System.out.println("Epics:");
        for (Epic epic : epics.values()) {
            System.out.println(epic);
        }
    }

    public void printSubtasks() {
        System.out.println("Subtasks:");
        for (Subtask subtask : subtasks.values()) {
            System.out.println(subtask);
        }
    }


}

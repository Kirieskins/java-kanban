import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private HashMap<Long, Task> tasks; // для хранения задач по id
    private HashMap<Long, Epic> epics; // для хранения эпиков по id
    private HashMap<Long, Subtask> subtasks; //  для хранения сабтасков по id

    private int id = 1; // доделать

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
        subtasks.clear();
    }
    public void deleteAllSubtasks(){
        for(Epic epic: epics.values()){
            epic.clearSubtasks();
            updateEpicStatus(epic);
        }
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
    public long addTask(Task task) {
        task.setCurrentId(id++);
        int collectionSize = tasks.size(); // проверка удалось ли добавить элемент чем сравнение кол-во элементов (до-после)
        tasks.put(task.getCurrentId(), task);
        if (collectionSize < tasks.size()){
            return task.getCurrentId();
        }else return -1;
    }

    public long addEpic(Epic epic) {
        epic.setCurrentId(id++);
        int collectionSize = epics.size(); // проверка удалось ли добавить элемент чем сравнение кол-во элементов (до-после)
        epics.put(epic.getCurrentId(), epic);
        if (collectionSize < epics.size()){
            return epic.getCurrentId();
        }else return -1;
    }

    // Добавляем подзадачу в эпик, которому она принадлежит
    public long addSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getEpicId())){
            subtask.setCurrentId(id++);
            int collectionSize = subtasks.size();
            Epic epic = epics.get(subtask.getEpicId());
            epic.addSubtask(subtask);
            subtasks.put(subtask.getCurrentId(), subtask);

            if (collectionSize < subtasks.size()){
                updateEpicStatus(epic);
                return subtask.getCurrentId();
            }else return -1;

        }else return -1;

    }
    // e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    public boolean updateTask(Task task) {
        // Проверяем, существует ли задача с таким идентификатором
        if (tasks.containsKey(task.getCurrentId())) {
            // Если существует, обновляем задачу
            tasks.replace(task.getCurrentId(), task);
            return true;
        }
        return false;
    }

    public boolean updateEpic(Epic epic) {
        if (epics.containsKey(epic.getCurrentId())){
            epics.get(epic.getCurrentId()).setTitle(epic.getTitle());
            epics.get(epic.getCurrentId()).setDescription(epic.getDescription());
            return true;
        }else return false;

    }

    public boolean updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getCurrentId())) {//проверяем есть ли такая подзадача, не существующую подзадачу не обновляем
            if (subtask.getEpicId() == subtasks.get(subtask.getCurrentId()).getEpicId()) {
                // даляем старую версию подзадачи из эпика и добавляем новую
                epics.get(subtask.getEpicId()).removeSubtaskById(subtask.getCurrentId());
                epics.get(subtask.getEpicId()).addSubtask(subtask);

                // Обновляем подзадачу в хранилище
                subtasks.replace(subtask.getCurrentId(), subtask);

                updateEpicStatus(epics.get(subtask.getEpicId()));
                return true;
            }
        }
        return false;
    }
    // f. Удаление по идентификатору.
    public void deleteTaskById(long id) {
        tasks.remove(id);
    }

    public void deleteEpicById(long id) {
        for (Subtask sub: subtasks.values()){
            if (sub.getEpicId() == id){
                subtasks.remove(sub.getCurrentId());
            }
        }
        epics.remove(id);
    }
    public void deleteSubtaskById(long id) {
       Subtask delSubtask = subtasks.get(id);
       if (delSubtask != null) {
           epics.get(delSubtask.getEpicId()).getSubtasks().remove(delSubtask);
           subtasks.remove(id);

           updateEpicStatus(epics.get(delSubtask.getEpicId()));
       }

    }

    //Дополнительные методы:
    //a. Получение списка всех подзадач определённого эпика.

    public List<Subtask> getSubtasksByEpicId(long epicId) {
        Epic epic = getEpicById(epicId);
        return epic != null ? epic.getSubtasks() : new ArrayList<>();

    }

    // Обновление статуса эпика в зависимости от статусов подзадач
    private void updateEpicStatus(Epic epic) {
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
            for (Subtask subtask: epic.getSubtasks()){
                System.out.println("Подзадача -" + subtask);
            }
            System.out.println();
        }
    }

    public void printSubtasks() {
        System.out.println("Subtasks:");
        for (Subtask subtask : subtasks.values()) {
            System.out.println(subtask);
        }
    }


}

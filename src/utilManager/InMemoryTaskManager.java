package utilManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Long, Task> tasks; // для хранения задач по id
    private HashMap<Long, Epic> epics; // для хранения эпиков по id
    private HashMap<Long, Subtask> subtasks; //  для хранения сабтасков по id

    private HistoryManager historyManager = Managers.getDefaultHistory();

    private int id = 1;

    // Вероятно, с появлением класса Managers, который сам будет возвращать нужный объект нет нужны в public
    // конструкторе ? В таком случае, наверное, в другом пакете должно происходить создание пользователей и объектов ->
    // можно сделать package-private static метод чтобы только класс util.Managers мог его использовать, а пользователь напрямую
    // не мог создавать конкретный экземпляр через (= new) ?
    private InMemoryTaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    /**
     * Доступ(условно) только для util.Managers
     * */

    static InMemoryTaskManager create(){
        return new InMemoryTaskManager();
    }

    // a. Получение списка всех задач.
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    @Override
    public List<Task> getAllEpics() {
        return new ArrayList<>(epics.values());
    }
    @Override
    public List<Task> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }
    @Override
    // b. Удаление всех задач.
    public void deleteAllTasks() {
        tasks.clear();
    }
    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }
    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
            updateEpicStatus(epic);
        }
        subtasks.clear();

    }
    @Override
    // c. Получение по идентификатору.
    public Task getTaskById(long id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }
    @Override
    public Epic getEpicById(long id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }
    @Override
    public Subtask getSubtaskById(long id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }
    @Override
    // d. Создание. Сам объект должен передаваться в качестве параметра.
    public long addTask(Task task) {
        task.setCurrentId(id++);
        int collectionSize = tasks.size(); // проверка удалось ли добавить элемент чем сравнение кол-во элементов (до-после)
        tasks.put(task.getCurrentId(), task);
        if (collectionSize < tasks.size()) {
            return task.getCurrentId();
        } else return -1;
    }
    @Override
    public long addEpic(Epic epic) {
        epic.setCurrentId(id++);
        int collectionSize = epics.size(); // проверка удалось ли добавить элемент чем сравнение кол-во элементов (до-после)
        epics.put(epic.getCurrentId(), epic);
        if (collectionSize < epics.size()) {
            return epic.getCurrentId();
        } else return -1;
    }

    // Добавляем подзадачу в эпик, которому она принадлежит
    @Override
    public long addSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getEpicId())) {
            subtask.setCurrentId(id++);
            int collectionSize = subtasks.size();
            Epic epic = epics.get(subtask.getEpicId());
            epic.addSubtask(subtask);
            subtasks.put(subtask.getCurrentId(), subtask);

            if (collectionSize < subtasks.size()) {
                updateEpicStatus(epic);
                return subtask.getCurrentId();
            } else return -1;

        } else return -1;

    }
    @Override
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
    @Override
    public boolean updateEpic(Epic epic) {
        if (epics.containsKey(epic.getCurrentId())) {
            epics.get(epic.getCurrentId()).setTitle(epic.getTitle());
            epics.get(epic.getCurrentId()).setDescription(epic.getDescription());
            return true;
        } else return false;

    }
    @Override
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
    @Override
    // f. Удаление по идентификатору.
    public void deleteTaskById(long id) {
        tasks.remove(id);
    }
    @Override
    public void deleteEpicById(long id) {
        for (Subtask sub : subtasks.values()) {
            if (sub.getEpicId() == id) {
                subtasks.remove(sub.getCurrentId());
            }
        }
        epics.remove(id);
    }
    @Override
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
    @Override
    public List<Subtask> getSubtasksByEpicId(long epicId) {
        Epic epic = getEpicById(epicId);
        return epic != null ? epic.getSubtasks() : new ArrayList<>();

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
            for (Subtask subtask : epic.getSubtasks()) {
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



    @Override
    public boolean add(Task task) {
        return historyManager.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}

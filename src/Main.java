import managers.Managers;
import managers.TaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;


public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("Epic", "e[ic");

        Task task1 = new Task("First Task", "The first task for test", Status.NEW);
        Task task2 = new Task("Second Task", "The second task for test", Status.NEW);
        Task task3 = new Task("Third Task", "The third task for test", Status.NEW);

        Epic epic1 = new Epic("The first epic", "the first epic for test");

        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);

        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("The first Subtask", "The first subtask", Status.NEW, 4);
        Subtask subtask2 = new Subtask("The second Subtask", "The second subtask", Status.NEW, 4);
        Subtask subtask3 = new Subtask("The third Subtask", "The third subtask", Status.NEW, 4);

        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);

        printAllTasks(manager);

    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.getSubtasksByEpicId(epic.getCurrentId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
        System.out.println("the end");
    }
}

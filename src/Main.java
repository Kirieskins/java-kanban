public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        // Создаем две задачи
        Task task1 = new Task("Clean the house", "Clean the living room and kitchen");
        Task task2 = new Task("Buy groceries", "Buy milk, bread, and eggs");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        // Создаем эпик с двумя подзадачами
        Epic epic1 = new Epic("Prepare for the trip", "Pack the bags and get everything ready");
        taskManager.addEpic(epic1);
        Subtask subtask1 = new Subtask("Pack clothes", "Pack summer clothes");
        Subtask subtask2 = new Subtask("Pack electronics", "Pack charger and gadgets");
        taskManager.addSubtask(subtask1, epic1);
        taskManager.addSubtask(subtask2, epic1);

        // Создаем эпик с одной подзадачей
        Epic epic2 = new Epic("Birthday party", "Plan a birthday party");
        taskManager.addEpic(epic2);
        Subtask subtask3 = new Subtask("Send invitations", "Send invitations to guests");
        taskManager.addSubtask(subtask3, epic2);

        // Распечатываем списки задач, эпиков и подзадач
        taskManager.printTasks();
        taskManager.printEpics();
        taskManager.printSubtasks();

        // Изменяем статусы и распечатываем
        task1.setStatus(Status.IN_PROGRESS);
        subtask1.setStatus(Status.DONE);
        taskManager.updateEpicStatus(epic1);

        System.out.println("\nAfter changing statuses:");
        taskManager.printTasks();
        taskManager.printEpics();
        taskManager.printSubtasks();

        // Удаляем задачу и эпик
        taskManager.deleteTaskById(task2.getCurrentId()); // Удаляем "Buy groceries"
        taskManager.deleteEpicById(epic2.getCurrentId()); // Удаляем "Birthday party"

        // Распечатываем списки после удаления
        System.out.println("\nAfter deletion:");
        taskManager.printTasks();
        taskManager.printEpics();
        taskManager.printSubtasks();
    }
}

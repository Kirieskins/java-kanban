public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task = new Task("Wash Dish", "Wash dish in kitchen", Status.NEW);
        Task task2 = new Task("Clean room", "Clean room with vacuumCleaner", Status.NEW);

        Epic epic = new Epic("Fix up programm", "fixing program");
      //  Epic epic2 = new Epic("Cook dish", "Cook breakfast");

        Subtask subtask = new Subtask("check review", "check review", Status.NEW, 3);
        Subtask subtask2 = new Subtask("rework code", "rework code", Status.NEW, 3);

      //  Subtask subtask3 = new Subtask("Prepare food", "unfreeze meat", Status.NEW);

       taskManager.addTask(task);
        taskManager.addTask(task2);

        taskManager.addEpic(epic);
       // taskManager.addEpic(epic2);

        taskManager.addSubtask(subtask);
        taskManager.addSubtask(subtask2);

        //taskManager.addSubtask(subtask3, epic2);





        taskManager.printTasks();
        System.out.println("----------------------------------------------------");
        taskManager.printEpics();
        System.out.println("----------------------------------------------------");
        taskManager.printSubtasks();
        System.out.println("----------------------------------------------------");

        subtask.setStatus(Status.IN_PROGRESS);
        subtask.setDescription("TEST REVIEW");
        subtask2.setStatus(Status.IN_PROGRESS);

       // subtask3.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subtask);
        taskManager.updateSubtask(subtask2);
      //  taskManager.updateSubtask(subtask3);



        System.out.println("After done some job \n\n");
        taskManager.printEpics();
        System.out.println("----------------------------------------------------");
    }
}

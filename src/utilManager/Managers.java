package utilManager;

public class Managers {
    public static TaskManager getDefault(){
        return InMemoryTaskManager.create();
    }

   public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
/**
 * Немного не понятно getDefault() - считает InMemoryTaskManager дефолтным, а для создания других будут другие
 * методы (~getSpecificTaskManager~) или внутри этого метода должна быть какая то логика - к простому примеру
 * - рандом
 * */

//        Добавление существующих Менеджеров с реализацией
//        List<util.TaskManager> taskManagers = new ArrayList<>(){{
//            add(new util.InMemoryTaskManager());
//        }};
//
//            int taskManager = new Random().nextInt(taskManagers.size());
//            return taskManagers.get(taskManager);
package managers;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    /**
     * Для истории вызова тасков HISTORY_MAX_SIZE - для проверок на количество элементов в листе
     * historyList - для хранения элементов в истории
     * */
    private static final int HISTORY_MAX_SIZE = 10;
    private List<Task> historyList = new ArrayList<>(10);
    /**
     * При вызовах методов getTaskById(), getSubtaskById(), getEpicsById() вызывается метод -> addToHistory()
     */


    @Override
    public boolean add(Task task) {
        if (task == null){
            return false;
        }
        if (historyList.size() >= HISTORY_MAX_SIZE){
            historyList.removeFirst();
        }
        historyList.add(task.cloneTask());
        return true;
    }

    @Override
    public List<Task> getHistory()  {
        return List.copyOf(historyList);
    }
}

package managers;

import tasks.*;

import java.util.List;

public interface HistoryManager {

   boolean add(Task task);

   List<Task> getHistory();

}

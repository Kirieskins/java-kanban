import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{
    private List<Subtask> subtasks;
    public Epic(String title, String description) {
        super(title, description, Status.NEW);
        subtasks = new ArrayList<>();

    }

    public void addSubtask(Subtask subtask){
        subtasks.add(subtask);
    }


    public void removeSubtaskById(long id){
        for(Subtask subtask: subtasks){
            if (subtask.getCurrentId() == id){
                subtasks.remove(subtask);
            }
        }
    }
    public void removeSubtask(Subtask subtask){
        subtasks.remove(subtask);
    }
    public List<Subtask> getSubtasks() {
      return subtasks;
    }

    public void clearSubtasks(){
        subtasks.clear();
    }

}

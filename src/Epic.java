import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{
    private List<Subtask> subtasks;
    public Epic(String title, String description) {
        super(title, description);
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

    public boolean isDone(){
            for (Subtask task: subtasks){
                if (task.getStatus() != Status.DONE){
                    return false;
                }
            }
            setStatus(Status.DONE);
            return true;
    }
}

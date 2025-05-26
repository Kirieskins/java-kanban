package tasks;

public class Subtask extends Task {
    private long epicId;

    public Subtask(String title, String description, Status status, long epicId) {
        super(title, description, status);
        this.epicId = epicId;
    }

    public long getEpicId() {
        return epicId;
    }

}

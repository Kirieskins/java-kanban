import java.util.Objects;

public class Task {
    private String title;
    private String description;
    private static long id = 0;
    private long currentId;
    private Status status;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        id++;
        currentId = id;
        status = Status.NEW;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static long getId() { // чтобы узнать какой порядковый номер будет у следующего таска
        return id;
    }


    public long getCurrentId() {
        return currentId;
    }



    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return currentId == task.currentId && Objects.equals(title, task.title) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, currentId, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", currentId=" + currentId +
                ", status=" + status +
                '}';
    }
}

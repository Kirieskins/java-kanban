package tasks;

import java.util.Objects;

public class Task {
    protected String title;
    protected String description;
    protected long currentId;
    protected Status status;

    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
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


    public long getCurrentId() {
        return currentId;
    }

    public void setCurrentId(long currentId) {
        this.currentId = currentId;
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

    public Task cloneTask() {
        return new Task(this.title, this.description, this.status);
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, description, currentId, status);
    }

    @Override
    public String toString() {
        return "util.Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", currentId=" + currentId +
                ", status=" + status +
                '}';
    }
}

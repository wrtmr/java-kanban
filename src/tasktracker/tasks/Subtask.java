package tasktracker.tasks;

import tasktracker.Infrastructure.TaskStatus;

import java.util.Objects;

public final class Subtask extends Task {
    private Epic parentTask;
    private int epicId;

    public Subtask(String name, String description, TaskStatus status) {
        super(name, description, status);
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public Epic getParentTask() {

        return parentTask;
    }

    public void setParentTask(Epic task) {

        this.parentTask = task;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this != object && getClass() != object.getClass()) return false;
        Subtask otherTask = (Subtask) object;
        return Objects.equals(this.id, otherTask.getId()) &&
                Objects.equals(this.name, otherTask.name) &&
                Objects.equals(this.description, otherTask.description) &&
                Objects.equals(this.parentTask, otherTask.getParentTask());
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (id > 0) {
            hash += id;
        }

        hash *= 31;

        if (name != null) {
            hash += name.hashCode();
        }
        if (description != null) {
            hash += description.hashCode();
        }
        if (parentTask != null) {
            hash += parentTask.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        return super.toString() + "Epic ID:" + epicId + ". ";
    }
}

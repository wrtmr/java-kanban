package TaskTracker.Tasks;

import java.util.Objects;

public final class Subtask extends Task {
    private Epic parentTask;

    public Subtask(String name, String description, TaskStatus status) {
        super(name, description, status);
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
        return Objects.equals(this.id, otherTask.getId())&&
                Objects.equals(this.name, otherTask.name)&&
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
        if(description != null) {
            hash += description.hashCode();
        }
        if(parentTask != null){
            hash += parentTask.hashCode();
        }
        return hash;
    }
}

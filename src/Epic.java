import java.util.HashMap;
import java.util.Objects;

public final class Epic extends Task {
    private final HashMap<Integer, Subtask> subtasks;

    public HashMap<Integer, Subtask> getSubtasks() { return subtasks; }

    public Epic(String name, String description, TaskStatus status,
                HashMap<Integer, Subtask> subtasks) {
        super(name, description, status);
        this.subtasks = subtasks;
    }

    public void clearSubTasks() {
        subtasks.clear();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this != object && getClass() != object.getClass()) return false;
        Epic otherTask = (Epic) object;
        return Objects.equals(this.id, otherTask.getId())&&
                Objects.equals(this.name, otherTask.name)&&
                Objects.equals(this.description, otherTask.description) &&
                Objects.equals(this.subtasks, otherTask.subtasks);
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
        if(subtasks != null){
            hash += subtasks.hashCode();
        }
        return hash;
    }
}

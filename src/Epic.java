import java.util.ArrayList;
import java.util.HashMap;

public final class Epic extends Task {
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public HashMap<Integer, Subtask> getSubtasks() { return subtasks; }

    public Epic(String name, String description, TaskStatus status,
                HashMap<Integer, Subtask> subtasks) {
        super(name, description, status);
        this.subtasks = subtasks;
    }

}

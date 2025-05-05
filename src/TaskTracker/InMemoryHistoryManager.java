package TaskTracker;

import java.util.ArrayList;
import java.util.List;

public final class InMemoryHistoryManager implements HistoryManager{

    private final List<Task> viewHistory = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return viewHistory;
    }

    @Override
    public void add(Task task) {
        Task savedTask = new Task(task.getName(), task.getDescription(), task.getStatus() );
        savedTask.setId(task.getId());
        viewHistory.add(savedTask);
    }
}

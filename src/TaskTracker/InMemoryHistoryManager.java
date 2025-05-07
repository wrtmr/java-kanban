package TaskTracker;

import java.util.ArrayList;
import java.util.List;

public final class InMemoryHistoryManager implements HistoryManager{
    private static final int HISTORY_LENGTH = 10;
    private final List<Task> viewHistory = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return viewHistory;
    }

    @Override
    public void add(Task task) {
        if (viewHistory.size() + 1 > HISTORY_LENGTH) {
            viewHistory.removeFirst();
        }
        Task savedTask = new Task(task.getName(), task.getDescription(), task.getStatus());
        savedTask.setId(task.getId());
        viewHistory.add(savedTask);
    }
}

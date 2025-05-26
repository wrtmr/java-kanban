package tasktracker.managers;
import tasktracker.tasks.*;
import java.util.List;


public interface HistoryManager {
    List<Task> getHistory();
    void remove(int id);
    void add (Task task);
}

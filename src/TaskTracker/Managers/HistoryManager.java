package TaskTracker.Managers;

import TaskTracker.Tasks.*;

import java.util.LinkedList;


public interface HistoryManager {
    LinkedList<Task> getHistory();

    void add (Task task);
}

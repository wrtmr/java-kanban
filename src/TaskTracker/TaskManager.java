package TaskTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    boolean getInitializationStatus();
    ArrayList<Task> getAllTasks();

    ArrayList<Subtask> getAllSubtasks();

    ArrayList<Epic> getAllEpics();

    void createTask(Task task);

    void createSubtask(Subtask task);

    void createEpicTask(Epic task);

    void updateTaskById(Integer id, Task newTask);

    void updateSubtaskById(Integer id, Subtask newSubtask);

    void updateEpicById(Integer id, Epic newEpic);

    HashMap<Integer, Subtask> getSubtasksFromEpic(Integer id);

    void clearAllTasks();

    Task getTaskById(Integer id);

    Subtask getSubtaskById(Integer id);

    Epic getEpicById(Integer id);

    void removeTaskById(Integer id);

    void removeSubtaskById(Integer id);

    void removeEpicById(Integer id);

    List<Task> getHistory();
}

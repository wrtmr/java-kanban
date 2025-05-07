package TaskTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class InMemoryTaskManager implements TaskManager {
    private int idCounter;
    private boolean isInitialized = false;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;
    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager){
        idCounter = 0;
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        this.historyManager = historyManager;
        if (this.historyManager == null) {
            System.out.println("History manager is Null. Initialization stopped.");
        } else {
            isInitialized = true;
        }
    }

    @Override
    public boolean getInitializationStatus(){ return isInitialized;}
    @Override
    public ArrayList<Task> getAllTasks(){ return new ArrayList<>(tasks.values()); }
    @Override
    public ArrayList<Subtask> getAllSubtasks(){ return new ArrayList<>(subtasks.values()); }
    @Override
    public ArrayList<Epic> getAllEpics(){ return new ArrayList<>(epics.values()); }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void createTask(Task task) {
        task.setId(idCounter);
        tasks.put(task.getId(), task);
        idCounter++;
    }

    @Override
    public void createSubtask(Subtask task) {
        task.setId(idCounter);
        subtasks.put(task.getId(), task);
        idCounter++;
    }

    @Override
    public void createEpicTask(Epic task) {
        task.setId(idCounter);
        epics.put(task.getId(), task);
        idCounter++;
    }

    @Override
    public void updateTaskById(Integer id, Task newTask) {
        if (tasks.get(id) == null) {
            System.out.println("Такого ключа нет в таблице с задачами");
            return;
        }
        newTask.setId(id);
        tasks.put(id, newTask);
    }

    @Override
    public void updateSubtaskById(Integer id, Subtask newSubtask) {
        if (subtasks.get(id) == null) {
            System.out.println("Такого ключа нет в таблице с подзадачами");
            return;
        }
        newSubtask.setId(id);
        subtasks.put(id, newSubtask);
        for (Epic epicTask : epics.values()) {
            if (epicTask.getSubtasks().containsKey(newSubtask.getId())) {
                epicTask.getSubtasks().put(newSubtask.getId(), newSubtask);
                updateEpicStatus(epicTask);
            }
        }
    }

    @Override
    public void updateEpicById(Integer id, Epic newEpic) {
        if(epics.get(id) == null){
            System.out.println("Такого ключа нет в таблице с Эпиками");
            return;
        }
        newEpic.setId(id);
        epics.put(newEpic.getId(), newEpic);
        updateEpicStatus(newEpic);
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasksFromEpic(Integer id) {
        if (epics.get(id) != null) {
            return epics.get(id).getSubtasks();
        }
        return null;
    }

    @Override
    public void clearAllTasks(){
        tasks.clear();
        for (Epic epicTask : epics.values()) {
           epicTask.clearSubTasks();
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public Task getTaskById(Integer id) {
        for (Integer key : tasks.keySet()) {
            if (key.equals(id)) {
                Task task = tasks.get(key);
                historyManager.add(task);
                return task;
            }
        }
        return null;
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        for (Integer key : subtasks.keySet()) {
            if (key.equals(id)) {
                Subtask task = subtasks.get(key);
                historyManager.add(task);
                return task;
            }
        }
        return null;
    }

    @Override
    public Epic getEpicById(Integer id) {
        for (Integer key : epics.keySet()) {
            if (key.equals(id)) {
                Epic task  = epics.get(key);
                historyManager.add(task);
                return task;
            }
        }
        return null;
    }

    @Override
    public void removeTaskById(Integer id) {
        tasks.remove(id);
    }

    @Override
    public void removeSubtaskById(Integer id) {
        subtasks.remove(id);
        for (Epic epicTask : epics.values()) {
            epicTask.getSubtasks().remove(id);
            updateEpicStatus(epicTask);
        }
    }

    @Override
    public void removeEpicById(Integer id) {
        for (Integer subTaskId : epics.get(id).getSubtasks().keySet()) {
            subtasks.remove(subTaskId);
        }
        epics.remove(id);
    }



    private void updateEpicStatus(Epic epicTask) {
        if (epicTask.getSubtasks().isEmpty()) {
            epicTask.setStatus(TaskStatus.NEW);
            return;
        }

        int doneCounter = 0;
        int newCounter = 0;

        for (Subtask subtask : epicTask.getSubtasks().values()) {
            if(subtask.getStatus() == TaskStatus.NEW){
                newCounter++;
            }
            if(subtask.getStatus() == TaskStatus.DONE) {
                doneCounter++;
            }
        }

        if (doneCounter == epicTask.getSubtasks().size()) {
            epicTask.setStatus(TaskStatus.DONE);
        } else if (newCounter == epicTask.getSubtasks().size()) {
            epicTask.setStatus(TaskStatus.NEW);
        } else {
            epicTask.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}

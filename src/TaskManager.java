import java.util.ArrayList;
import java.util.HashMap;

public final class TaskManager {
    private int idCounter = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    public ArrayList<Task> getAllTasks(){ return new ArrayList<>(tasks.values()); }
    public ArrayList<Subtask> getAllSubtasks(){ return new ArrayList<>(subtasks.values()); }
    public ArrayList<Epic> getAllEpics(){ return new ArrayList<>(epics.values()); }

    public void createTask(Task task) {
        task.setId(idCounter);
        tasks.put(task.getId(), task);
        idCounter++;
    }

    public void createSubtask(Subtask task) {
        task.setId(idCounter);
        subtasks.put(task.getId(), task);
        idCounter++;
    }

    public void createEpicTask(Epic task) {
        task.setId(idCounter);
        epics.put(task.getId(), task);
        idCounter++;
    }

    public void updateTaskById(Integer id, Task newTask) {
        if (tasks.get(id) == null) {
            System.out.println("Такого ключа нет в таблице с задачами");
            return;
        }
        newTask.setId(id);
        tasks.put(id, newTask);
    }

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

    public void updateEpicById(Integer id, Epic newEpic) {
        if(epics.get(id) == null){
            System.out.println("Такого ключа нет в таблице с Эпиками");
            return;
        }
        newEpic.setId(id);
        epics.put(newEpic.getId(), newEpic);
        updateEpicStatus(newEpic);
    }

    public HashMap<Integer, Subtask> getSubtasksFromEpic(Epic epicTask) {
        if (epics.get(epicTask.getId()) != null) {
            return epics.get(epicTask.getId()).getSubtasks();
        }
        return null;
    }

    public void clearAllTasks(){
        tasks.clear();
        for(Epic epicTask : epics.values()) {
           epicTask.clearSubTasks();
        }
        subtasks.clear();
        epics.clear();
    }

    public Task getTaskById(Integer id) {
        for (Integer key : tasks.keySet()) {
            if (key.equals(id)) return tasks.get(key);
        }
        return null;
    }

    public Subtask getSubtaskById(Integer id) {
        for (Integer key : subtasks.keySet()) {
            if (key.equals(id)) return subtasks.get(key);
        }
        return null;
    }

    public Epic getEpicById(Integer id) {
        for (Integer key : epics.keySet()) {
            if(key.equals(id)) return epics.get(key);
        }
        return null;
    }

    public void removeTaskById(Integer id) {
        tasks.remove(id);
    }

    public void removeSubtaskById(Integer id) {
        subtasks.remove(id);
        for (Epic epicTask : epics.values()) {
            epicTask.getSubtasks().remove(id);
            updateEpicStatus(epicTask);
        }
    }

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

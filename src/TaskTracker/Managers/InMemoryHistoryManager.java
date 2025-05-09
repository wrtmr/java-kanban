package TaskTracker.Managers;

import TaskTracker.Tasks.*;

import java.util.LinkedList;


public final class InMemoryHistoryManager implements HistoryManager{
    private static final int HISTORY_LENGTH = 10;
    private final LinkedList<Task> viewHistory;

    public InMemoryHistoryManager() {
        viewHistory = new LinkedList<>();
    }

    @Override
    public LinkedList<Task> getHistory() {
        return viewHistory;
    }

    @Override
    public void add(Task task) {
        if (viewHistory.size() + 1 > HISTORY_LENGTH) {
            viewHistory.removeFirst();
        }
// В тз там вроде говорилось что надо сохранять как бы те изменения и данные которые были на момент сохранения
// Задание: убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
// Вот я и ориентировался на предыдущую версию задачи. А по скольку задача это ссылочный тип то он
// хранит ссылку на неё (и если что-то изменится, то и в хистори тоже изменится), а чтобы сохранилась копия со старыми данными то надо скопировать/склонировать задачу
// TaskTracker.Tasks.Task savedTask = new TaskTracker.Tasks.Task(task.getName(), task.getDescription(), task.getStatus());
// savedTask.setId(task.getId());
      viewHistory.add(task);
    }
}

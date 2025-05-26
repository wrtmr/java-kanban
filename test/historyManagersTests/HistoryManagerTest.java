package historyManagersTests;
import tasktracker.managers.HistoryManager;
import tasktracker.managers.Managers;
import tasktracker.tasks.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HistoryManagerTest {

    private static HistoryManager historyManager;

    @BeforeAll
    public static void beforeAll() {
        Managers managers = new Managers();
        historyManager = managers.getDefaultHistory();
    }

    @Test
    public void taskStatusAndDataSavedCorrectInHistory(){
        Task task = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.NEW);
        task.setId(1);
        historyManager.add(task);

        Task savedTask = historyManager.getHistory().getFirst();
        task.setStatus(TaskStatus.IN_PROGRESS);

        Assertions.assertEquals(task, savedTask, "Задачи не равны.");
    }

    @Test
    public void theOrderOfPublishedTasksIsRight(){
        Task task = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.NEW);
        task.setId(1);
        Task task2 = new Task("Прикрутить полку", "Взять шурупы, взять дрель, взять + " +
                "полку, просверлить отверстия", TaskStatus.NEW);
        task2.setId(2);
        Task task3 = new Task("Какая-то новая задача", "Взять шурупы, взять дрель, взять + " +
                "полку, просверлить отверстия", TaskStatus.NEW);
        task3.setId(3);

        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);

        Task savedTask = historyManager.getHistory().getFirst();

        Assertions.assertEquals(task3, savedTask, "Задачи не равны.");
    }

    @Test
    public void theRemovedTasksAreNotInTheHistory(){
        Task task = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.NEW);
        task.setId(1);
        Task task2 = new Task("Прикрутить полку", "Взять шурупы, взять дрель, взять + " +
                "полку, просверлить отверстия", TaskStatus.NEW);
        task2.setId(2);
        Task task3 = new Task("Какая-то новая задача", "Что-то, как-то, взять", TaskStatus.NEW);

        task3.setId(3);

        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(task3.getId());

        Task savedTask = historyManager.getHistory().getFirst();

        Assertions.assertEquals(task2, savedTask, "Задачи не равны.");
    }
}

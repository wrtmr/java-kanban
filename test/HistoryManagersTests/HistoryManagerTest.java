package HistoryManagersTests;
import TaskTracker.Managers.HistoryManager;
import TaskTracker.Managers.Managers;
import TaskTracker.Tasks.*;
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

}

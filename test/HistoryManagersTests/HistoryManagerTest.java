package HistoryManagersTests;
import TaskTracker.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HistoryManagerTest {

    private static HistoryManager historyManager;
    private static Managers managers;

    @BeforeAll
    public static void beforeAll() {
        managers = new Managers();
        historyManager = managers.getDefaultHistory();
    }

    @Test
    public void taskStatusAndDataSavedCorrectInHistory(){
        Task task = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.NEW);
        task.setId(1);
        historyManager.add(task);
        task.setStatus(TaskStatus.IN_PROGRESS);

        Assertions.assertEquals(TaskStatus.NEW, historyManager.getHistory().getFirst().getStatus(), "Статус в истории изменился. " +
                "Статус задачи в истории не должен изменяться.");
    }

}

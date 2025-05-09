package ManagersTests;

import TaskTracker.Managers.HistoryManager;
import TaskTracker.Managers.InMemoryHistoryManager;
import TaskTracker.Managers.Managers;
import TaskTracker.Managers.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ManagersTest {

    private static Managers managers;

    @BeforeAll
    public static void beforeAll() {
        managers = new Managers();
    }

    @Test
    public void managersReturnInitializedAndReadyToWorkManagers() {
        HistoryManager historyManager = new InMemoryHistoryManager();

        TaskManager taskManager = managers.getDefault(historyManager);
        Assertions.assertTrue(taskManager.getInitializationStatus(), "TaskManager is not initialized and not ready for work");
    }
}

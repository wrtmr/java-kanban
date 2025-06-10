package tasktracker.managers;

import java.io.File;

public final class Managers {

    public TaskManager getDefault(HistoryManager historyManager) {
        return new InMemoryTaskManager(historyManager);
    }

    public TaskManager getFileBacked(HistoryManager historyManager, File file) {
        return new FileBackedTaskManager(historyManager, file);
    }

    public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}

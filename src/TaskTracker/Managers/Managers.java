package TaskTracker.Managers;

public final class Managers {

    public TaskManager getDefault(HistoryManager historyManager) {
        return new InMemoryTaskManager(historyManager);
    }

    public HistoryManager getDefaultHistory() { return new InMemoryHistoryManager(); }
}

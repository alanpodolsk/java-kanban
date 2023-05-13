package manager;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault() {
        TaskManager taskManager = new HttpTaskManager();
        return taskManager;
    }

    public static HistoryManager getDefaultHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        return historyManager;
    }
}

package util;

public class Managers {

    static TaskManager getDefault(){
        TaskManager taskManager = new InMemoryTaskManager();
        return taskManager;
    }

    static HistoryManager getDefaultHistory(){
        HistoryManager historyManager = new InMemoryHistoryManager();
        return historyManager;
    }
}

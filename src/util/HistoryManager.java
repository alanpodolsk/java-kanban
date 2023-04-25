package util;

import elements.Task;
import java.util.List;


public interface HistoryManager {


    void add(Task task);
    void remove (int id);
    List<Task> getHistory();
}

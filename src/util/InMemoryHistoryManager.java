package util;

import elements.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    List<Task> history = new ArrayList<>();

    @Override
    public void add (Task task){
        history.add(task);
        if(history.size()>10){
            history.remove(0);
        }
    }
    @Override
    public List<Task> getHistory(){
        return history;
    }
}

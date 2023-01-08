package util;

import elements.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    public static final byte MAX_SIZE = 10;

    List<Task> history = new ArrayList<>();

    @Override
    public List<Task> getHistory(){
        return history;
    }  // метод возвращает последние 10 просмотренных записей

    @Override
    public void add (Task task){
        history.add(task);
        if(history.size()>MAX_SIZE){
            history.remove(0);
        }
    }

}

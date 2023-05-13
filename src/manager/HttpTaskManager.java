package manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Epic;
import model.SubTask;
import model.Task;
import server.KVServerClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class HttpTaskManager extends FileBackedTaskManager{
    KVServerClient client;
    final String KEY_TASK = "tasks";
    final String KEY_EPIC = "epics";
    final String KEY_SUBTASK = "subtasks";
    final String KEY_HISTORY = "history";
    Gson gson;

    public HttpTaskManager(){
        this.client = new KVServerClient("http://localhost:8078");
        gson = new Gson();
    }
    @Override
    protected void save(){
        client.put(KEY_TASK, gson.toJson(getTasks()));
        client.put(KEY_EPIC, gson.toJson(getEpics()));
        client.put(KEY_SUBTASK, gson.toJson(getSubTasks()));
        client.put(KEY_HISTORY, gson.toJson(historyToString()));
    }

    public static HttpTaskManager loadFromServer(){
        HttpTaskManager manager = new HttpTaskManager();
        Type taskType = new TypeToken<ArrayList<Task>>(){}.getType();
        Type subTaskType = new TypeToken<ArrayList<SubTask>>(){}.getType();
        Type epicType = new TypeToken<ArrayList<Epic>>(){}.getType();
        List<Task> loadedTasks = manager.gson.fromJson(manager.client.load(manager.KEY_TASK),taskType);
        List<Epic> loadedEpics = manager.gson.fromJson(manager.client.load(manager.KEY_EPIC),epicType);
        List<SubTask> loadedSubTasks = manager.gson.fromJson(manager.client.load(manager.KEY_SUBTASK),subTaskType);
        String history = manager.gson.fromJson(manager.client.load(manager.KEY_HISTORY),String.class).replace("[", "").replace("]", "").replace(" ", "");
        for (Task task: loadedTasks){
            manager.createTask(task);
        }
        for (Epic epic: loadedEpics){
            manager.createEpic(epic);
        }
        for (SubTask subTask: loadedSubTasks){
            manager.createSubTask(subTask);
        }
        manager.historyFromString(history);
        return manager;
    }



}

package manager;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;
import java.util.TreeSet;

public interface TaskManager {
    int getNewId();
    //Работа с elements.Task

    void createTask(Task task);

    void updateTask(Task task);

    void deleteTask(int Id);

    void removeAllTasks();

    List<Task> getTasks();

    void createEpic(Epic epic);

    void updateEpic(Epic epic);

    void deleteEpic(int epicId);

    void removeAllEpics();

    List<Epic> getEpics();

    void updateEpicStatus(Epic epic);

    void createSubTask(SubTask subTask);

    void updateSubTask(SubTask subTask);

    void deleteSubTask(int subTaskId);

    void removeAllSubTasks();

    List<SubTask> getSubTasks();

    Task getTask(int id);

    SubTask getSubTask(int id);

    Epic getEpic(int id);

    List<Task> getHistory();

    TreeSet<Task> getPrioritizedTasks();
}
package util;

import elements.Epic;
import elements.SubTask;
import elements.Task;

import java.util.List;

public interface TaskManager {


    //Работа с elements.Task
    int getNewTaskId();

    void createTask(Task task);

    void updateTask(Task task);

    void deleteTask(int taskId);

    void removeAllTasks();

    void printAllTasks();

    void createEpic(Epic epic);

    void updateEpic(Epic epic);

    void deleteEpic(int epicId);

    void removeAllEpics();

    void printAllEpics();

    void updateEpicStatus(Epic epic);

    void createSubTask(SubTask subTask);

    void addSubTaskToEpic(SubTask subTask);

    void updateSubTask(SubTask subTask);

    void deleteSubTask(int subTaskId);

    void removeSubTaskFromEpic(SubTask subTask);

    int getNewSubTaskId();

    void removeAllSubTasks();

    void printAllSubTasks();

    Task getTask(int id);

    SubTask getSubTask(int id);

    Epic getEpic(int id);

    List<Task> getHistory();
}
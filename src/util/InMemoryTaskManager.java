package util;

import elements.Epic;
import elements.SubTask;
import elements.Task;

import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager{


    HashMap<Integer, Task> taskMap = new HashMap<>();
    HashMap<Integer, Epic> epicMap = new HashMap<>();
    HashMap<Integer, SubTask> subTaskMap = new HashMap<>();
    HistoryManager historyManager = Managers.getDefaultHistory();


    //Работа с elements.Task
    @Override
    public int getNewTaskId() {
        int maxId = 0;
        for (Integer key : taskMap.keySet()) {
            if (key > maxId) {
                maxId = key;
            }
        }
        return maxId + 1;
    }

    @Override
    public void createTask(Task task) {
        if (task == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        int taskId = getNewTaskId();
        task.setTaskID(taskId);
        taskMap.put(taskId, task);
    }

    @Override
    public void updateTask(Task task) {
        if (task == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        int taskId = task.getTaskID();
        if (!taskMap.containsKey(taskId)) {
            System.out.println("Невозможно обновить запись " + task + " - данный ID отсутствует в базе");
            return;
        }
        taskMap.put(taskId, task);
    }

    @Override
    public void deleteTask(int taskId) {
        if (!taskMap.containsKey(taskId)) {
            System.out.println("Невозможно удалить запись №" + taskId + " - данный ID отсутствует в базе");
            return;
        }
        taskMap.remove(taskId);
    }

    @Override
    public void removeAllTasks() {
        taskMap.clear();
    }
    @Override
    public void printAllTasks(){
        System.out.println(taskMap.toString());
    }


    //Работа с elements.Epic

    @Override
    public void createEpic(Epic epic){
        if (epic == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        int epicId = getNewEpicId();
        epic.setTaskID(epicId);
        epicMap.put(epicId, epic);
    }

    @Override
    public void updateEpic(Epic epic){
        if (epic == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        int epicId = epic.getTaskID();
        if (!epicMap.containsKey(epicId)) {
            System.out.println("Невозможно обновить запись " + epic + " - данный ID отсутствует в базе");
            return;
        }
            epicMap.put(epicId, epic);
    }

    @Override
    public void deleteEpic(int epicId){
        if (!epicMap.containsKey(epicId)) {
            System.out.println("Невозможно удалить запись №" + epicId + " - данный ID отсутствует в базе");
            return;
        }
        Epic epic = epicMap.get(epicId);
        List<Integer> subTaskList = epic.getSubTasksList();
        for (Integer key : subTaskList) {
            deleteSubTask(key);
            }
        epicMap.remove(epicId);
    }

    @Override
    public void removeAllEpics() {
        Integer[] epicIdList = epicMap.keySet().toArray(new Integer[0]);

        for (Integer epicId : epicIdList){
            deleteEpic(epicId);
        }
    }

    @Override
    public void printAllEpics() {
        System.out.println(epicMap.toString());
    }
    public int getNewEpicId() {
        int maxId = 0;
        for (Integer key : epicMap.keySet()) {
            if (key > maxId) {
                maxId = key;
            }
        }
        return maxId + 1;
    }

    @Override
    public void updateEpicStatus(Epic epic){
        List<Integer> subTasksList = epic.getSubTasksList();
        if (subTasksList.size() == 0){
            epic.setStatus(Statuses.NEW);
            return;
        }
        int newSubs = 0;
        int progressSubs = 0;
        int doneSubs = 0;
        Statuses status;

        for (Integer key : subTaskMap.keySet()){
            if(!subTasksList.contains(key)){
                continue;
            }
            SubTask actualSubTask = subTaskMap.get(key);
            Statuses subTaskStatus = actualSubTask.getStatus();
            switch (subTaskStatus){
                case NEW:
                    newSubs++;
                    break;
                case IN_PROGRESS:
                    progressSubs++;
                    break;
                case DONE:
                    doneSubs++;
                    break;
                default:
            }
        }

        if (newSubs == subTasksList.size()){
            status = Statuses.NEW;
        } else if (doneSubs == subTasksList.size()){
            status = Statuses.DONE;
        } else {
            status = Statuses.IN_PROGRESS;
        }
        epic.setStatus(status);
    }

    //Работа с SubTasks

    @Override
    public void createSubTask(SubTask subTask) {
        if (subTask == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        int subTaskId = getNewSubTaskId();
        int epicId = subTask.getEpicId();
        subTask.setTaskID(subTaskId);
        if (epicMap.containsKey(epicId)) {
            subTaskMap.put(subTaskId, subTask);
            addSubTaskToEpic(subTask);
            Epic epic = epicMap.get(epicId);
            updateEpicStatus(epic);
            updateEpic(epic);
        } else {
            System.out.println("Невозможно записать в систему задачу " + subTask + " - указан некорректный ID эпика");
        }


    }
    @Override
    public void addSubTaskToEpic(SubTask subTask){
        int epicId = subTask.getEpicId();
        int subTaskId = subTask.getTaskID();
        Epic epic = epicMap.get(epicId);
        if(!epic.isSubTaskExists(subTaskId)){
            epic.addNewSubTask(subTaskId);
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTask == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        int taskId = subTask.getTaskID();
        if (!subTaskMap.containsKey(taskId)) {
            System.out.println("Невозможно обновить запись " + subTask + " - данный ID отсутствует в базе");
            return;
        }

        subTaskMap.put(taskId, subTask);
        int epicId = subTask.getEpicId();
        Epic epic = epicMap.get(epicId);
        updateEpicStatus(epic);
        updateEpic(epic);

    }

    @Override
    public void deleteSubTask(int subTaskId) {
        if (!subTaskMap.containsKey(subTaskId)) {
            System.out.println("Невозможно удалить запись №" + subTaskId + " - данный ID отсутствует в базе");
            return;
        }
        SubTask subTask = subTaskMap.get(subTaskId);
        removeSubTaskFromEpic(subTask);
        subTaskMap.remove(subTaskId);
    }

    @Override
    public void removeSubTaskFromEpic(SubTask subTask){
        int epicId = subTask.getEpicId();
        int subTaskId = subTask.getTaskID();
        Epic epic = epicMap.get(epicId);
        if(epic.isSubTaskExists(subTaskId)){
            epic.removeSubTask(subTaskId);
            updateEpicStatus(epic);
            updateEpic(epic);
        }
    }
    @Override
    public int getNewSubTaskId() {
        int maxId = 0;
        for (Integer key : subTaskMap.keySet()) {
            if (key > maxId) {
                maxId = key;
            }
        }
        return maxId + 1;
    }

    @Override
    public void removeAllSubTasks() {
        Integer[] subTaskIdList = subTaskMap.keySet().toArray(new Integer[0]);

        for (Integer subTaskId : subTaskIdList){
            deleteSubTask(subTaskId);
        }
    }

    @Override
    public void printAllSubTasks(){
        System.out.println(subTaskMap.toString());
    }

    @Override
    public Task getTask(int id){
        Task task = taskMap.get(id);
        if (task != null){
            historyManager.add(task);
        }
        return task;
    }
    @Override
    public SubTask getSubTask(int id) {
        SubTask subTask = subTaskMap.get(id);
        if (subTask != null){
            historyManager.add((Task)subTask);
        }
        return subTask;
    }
    @Override
    public Epic getEpic(int id) {
        Epic epic = epicMap.get(id);
        if (epic != null){
            historyManager.add((Task)epic);
        }
        return epic;
    }

    @Override
    public List <Task> getHistory(){
        return historyManager.getHistory();
    }



}


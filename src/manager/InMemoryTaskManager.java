package manager;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class InMemoryTaskManager implements TaskManager{


    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    protected final TreeSet <Task> prioritizedTasks = new TreeSet <Task>(new TaskTimeComparator());
    static int idManager = 0;
    protected final HistoryManager historyManager = Managers.getDefaultHistory();


    @Override
    public int getNewId() {
        idManager +=1;
        return idManager;
    }

    //Работа с elements.Task

    @Override
    public void createTask(Task task) {
        if (task == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        tasks.put(task.getId(), task);
        prioritizedTasks.add(task);
    }

    @Override
    public void updateTask(Task task) {
        if (task == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        int taskId = task.getId();
        if (!tasks.containsKey(taskId)) {
            System.out.println("Невозможно обновить запись " + task + " - данный ID отсутствует в базе");
            return;
        }
        tasks.put(taskId, task);
        prioritizedTasks.add(task);
    }

    @Override
    public void deleteTask(int Id) {
        if (!tasks.containsKey(Id)) {
            System.out.println("Невозможно удалить запись №" + Id + " - данный ID отсутствует в базе");
            return;
        }
        Task task = tasks.get(Id);
        tasks.remove(Id);
        prioritizedTasks.remove(task);
        historyManager.remove(Id);
    }

    @Override
    public void removeAllTasks() {
        Integer[] taskIdList = tasks.keySet().toArray(new Integer[0]);
        for (Integer taskId : taskIdList){
            deleteTask(taskId);
        }
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    //Работа с elements.Epic

    @Override
    public void createEpic(Epic epic){
        if (epic == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        epic.setStartTime(getEpicStartTime(epic));
        epic.setEndTime(getEpicEndTime(epic));
        epic.setDuration();
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateEpic(Epic epic){
        if (epic == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        int epicId = epic.getId();
        if (!epics.containsKey(epicId)) {
            System.out.println("Невозможно обновить запись " + epic + " - данный ID отсутствует в базе");
            return;
        }
        epic.setStartTime(getEpicStartTime(epic));
        epic.setEndTime(getEpicEndTime(epic));
        epic.setDuration();
        epics.put(epicId, epic);
    }

    @Override
    public void deleteEpic(int id){
        if (!epics.containsKey(id)) {
            System.out.println("Невозможно удалить запись №" + id + " - данный ID отсутствует в базе");
            return;
        }
        Epic epic = epics.get(id);
        List<Integer> subTaskList = epic.getSubTasksList();
        for (Integer key : subTaskList) {
            deleteSubTask(key);
            }
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeAllEpics() {
        for (Integer epic: epics.keySet()){
            deleteEpic(epic);
        }
    }

    @Override
    public List<Epic> getEpics() {
        return  new ArrayList<>(epics.values());
        }

    private void updateEpicStatus(Epic epic){
        List<Integer> subTasksList = epic.getSubTasksList();
        if (subTasksList.size() == 0){
            epic.setStatus(Status.NEW);
            return;
        }
        int newSubs = 0;
        int progressSubs = 0;
        int doneSubs = 0;
        Status status;

        for (Integer key : subTasks.keySet()){
            if(!subTasksList.contains(key)){
                continue;
            }
            SubTask actualSubTask = subTasks.get(key);
            Status subTaskStatus = actualSubTask.getStatus();
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
            status = Status.NEW;
        } else if (doneSubs == subTasksList.size()){
            status = Status.DONE;
        } else {
            status = Status.IN_PROGRESS;
        }
        epic.setStatus(status);
    }

    public LocalDateTime getEpicStartTime(Epic epic){
        LocalDateTime startTime = null;
        for (Integer subTaskId: epic.getSubTasksList()){
            if (startTime == null){
               startTime = subTasks.get(subTaskId).getStartTime();
            } else if (subTasks.get(subTaskId).getStartTime().isBefore(startTime)){
                startTime = subTasks.get(subTaskId).getStartTime();
            }
        }
        if (startTime == null){
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.of("Europe/Moscow"));
        } else {
            return startTime;
        }
    }

    public LocalDateTime getEpicEndTime(Epic epic){
        LocalDateTime endTime = null;
        for (Integer subTaskId: epic.getSubTasksList()){
            if (endTime == null){
                endTime = subTasks.get(subTaskId).getEndTime();
            } else if (subTasks.get(subTaskId).getEndTime().isAfter(endTime)){
                endTime = subTasks.get(subTaskId).getEndTime();
            }
        }
        if (endTime == null){
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.of("Europe/Moscow"));
        } else {
            return endTime;
        }
    }



    //Работа с SubTasks

    @Override
    public void createSubTask(SubTask subTask) {
        if (subTask == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        int epicId = subTask.getEpicId();
        if (epics.containsKey(epicId)) {
            subTasks.put(subTask.getId(), subTask);
            prioritizedTasks.add(subTask);
            addSubTaskToEpic(subTask);
            Epic epic = epics.get(epicId);
            updateEpicStatus(epic);
            updateEpic(epic);
        } else {
            System.out.println("Невозможно записать в систему задачу " + subTask + " - указан некорректный ID эпика");
        }


    }

    private void addSubTaskToEpic(SubTask subTask){
        int epicId = subTask.getEpicId();
        int subTaskId = subTask.getId();
        Epic epic = epics.get(epicId);
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
        int taskId = subTask.getId();
        if (!subTasks.containsKey(taskId)) {
            System.out.println("Невозможно обновить запись " + subTask + " - данный ID отсутствует в базе");
            return;
        }

        subTasks.put(taskId, subTask);
        prioritizedTasks.add(subTask);
        int epicId = subTask.getEpicId();
        Epic epic = epics.get(epicId);
        updateEpicStatus(epic);
        updateEpic(epic);

    }

    @Override
    public void deleteSubTask(int id) {
        if (!subTasks.containsKey(id)) {
            System.out.println("Невозможно удалить запись №" + id + " - данный ID отсутствует в базе");
            return;
        }
        SubTask subTask = subTasks.get(id);
        removeSubTaskFromEpic(subTask);
        prioritizedTasks.remove(subTask);
        subTasks.remove(id);
        historyManager.remove(id);

    }


    private void removeSubTaskFromEpic(SubTask subTask){
        int epicId = subTask.getEpicId();
        int subTaskId = subTask.getId();
        Epic epic = epics.get(epicId);
        if(epic.isSubTaskExists(subTaskId)){
            epic.removeSubTask(subTaskId);
            updateEpicStatus(epic);
            updateEpic(epic);
        }
    }


    @Override
    public void removeAllSubTasks() {
   Integer[] subTaskIdList = subTasks.keySet().toArray(new Integer[0]);
        for (Integer subTaskId : subTaskIdList){
            deleteSubTask(subTaskId);
        }
    }

    @Override
    public List<SubTask> getSubTasks() {
        return  new ArrayList<>(subTasks.values());
    }

    @Override
    public Task getTask(int id){
        Task task = tasks.get(id);
        if (task != null){
            historyManager.add(task);
        }
        return task;
    }
    @Override
    public SubTask getSubTask(int id) {
        SubTask subTask = subTasks.get(id);
        if (subTask != null){
            historyManager.add((Task)subTask);
        }
        return subTask;
    }
    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null){
            historyManager.add((Task)epic);
        }
        return epic;
    }

    @Override
    public List <Task> getHistory(){
        return historyManager.getHistory();
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }


}


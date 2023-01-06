import java.util.HashMap;
import java.util.List;

public class Manager {

    HashMap<Integer, Task> taskMap = new HashMap<>();
    HashMap<Integer, Epic> epicMap = new HashMap<>();
    HashMap<Integer, SubTask> subTaskMap = new HashMap<>();
    String[] validStatuses = {"NEW", "IN_PROGRESS", "DONE"}; //в комментариях рекомендовано вынести в Enum, но поскольку сам Enum - это тема следующего спринта, я исправлю этот момент в ТЗ 4


    //Работа с Task
    public int getNewTaskId() {
        int maxId = 0;
        for (Integer key : taskMap.keySet()) {
            if (key > maxId) {
                maxId = key;
            }
        }
        return maxId + 1;
    }

    public void createTask(Task task) {
        if (task == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        int taskId = getNewTaskId();
        task.setTaskID(taskId);
        if (statusValidation(task.getStatus())) {
            taskMap.put(taskId, task);
        } else {
            System.out.println("Невозможно записать в систему задачу " + task + " - статус не прошел валидацию");
        }
    }

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

        if (statusValidation(task.getStatus())) {
            taskMap.put(taskId, task);
        } else {
            System.out.println("Невозможно записать в систему задачу " + task + " - статус не прошел валидацию");
        }
    }

    public void deleteTask(int taskId) {
        if (!taskMap.containsKey(taskId)) {
            System.out.println("Невозможно удалить запись №" + taskId + " - данный ID отсутствует в базе");
            return;
        }
        taskMap.remove(taskId);
    }

    public void removeAllTasks() {
        taskMap.clear();
    }
    public void printAllTasks(){
        System.out.println(taskMap.toString());
    }
    //Работа с Epic

    public void createEpic(Epic epic){
        if (epic == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        int epicId = getNewEpicId();
        epic.setTaskID(epicId);
        epicMap.put(epicId, epic);
    }

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

    public void removeAllEpics() {
        Object[] epicIdList = epicMap.keySet().toArray();

        for (Object epicId : epicIdList){
            deleteEpic((Integer)epicId);
        }
    }

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

    public void updateEpicStatus(Epic epic){
        List<Integer> subTasksList = epic.getSubTasksList();
        if (subTasksList.size() == 0){
            epic.setStatus("NEW");
            return;
        }
        int newSubs = 0;
        int progressSubs = 0;
        int doneSubs = 0;
        String status;

        for (Integer key : subTaskMap.keySet()){
            if(!subTasksList.contains(key)){
                continue;
            }
            SubTask actualSubTask = subTaskMap.get(key);
            switch (actualSubTask.getStatus()){
                case "NEW":
                    newSubs++;
                    break;
                case "IN_PROGRESS":
                    progressSubs++;
                    break;
                case "DONE":
                    doneSubs++;
                    break;
                default:
            }
        }

        if (newSubs == subTasksList.size()){
            status = "NEW";
        } else if (doneSubs == subTasksList.size()){
            status = "DONE";
        } else {
            status = "IN_PROGRESS";
        }
        epic.setStatus(status);
    }

    //Работа с SubTasks

    public void createSubTask(SubTask subTask) {
        if (subTask == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        int subTaskId = getNewSubTaskId();
        int epicId = subTask.getEpicId();
        subTask.setTaskID(subTaskId);
        if (statusValidation(subTask.getStatus()) && epicMap.containsKey(epicId)) {
            subTaskMap.put(subTaskId, subTask);
            addSubTaskToEpic(subTask);
            Epic epic = epicMap.get(epicId);
            updateEpicStatus(epic);
            updateEpic(epic);
        } else {
            System.out.println("Невозможно записать в систему задачу " + subTask + " - статус не прошел валидацию либо указан некорректный ID эпика");
        }


    }
    public void addSubTaskToEpic(SubTask subTask){
        int epicId = subTask.getEpicId();
        int subTaskId = subTask.taskID;
        Epic epic = epicMap.get(epicId);
        if(!epic.isSubTaskExists(subTaskId)){
            epic.addNewSubTask(subTaskId);
        }
    }

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

        if (statusValidation(subTask.getStatus())) {
            subTaskMap.put(taskId, subTask);
            int epicId = subTask.getEpicId();
            Epic epic = epicMap.get(epicId);
            updateEpicStatus(epic);
            updateEpic(epic);
        } else {
            System.out.println("Невозможно записать в систему задачу " + subTask + " - статус не прошел валидацию");
        }
    }

    public void deleteSubTask(int subTaskId) {
        if (!subTaskMap.containsKey(subTaskId)) {
            System.out.println("Невозможно удалить запись №" + subTaskId + " - данный ID отсутствует в базе");
            return;
        }
        SubTask subTask = subTaskMap.get(subTaskId);
        removeSubTaskFromEpic(subTask);
        subTaskMap.remove(subTaskId);
    }

    public void removeSubTaskFromEpic(SubTask subTask){
        int epicId = subTask.getEpicId();
        int subTaskId = subTask.taskID;
        Epic epic = epicMap.get(epicId);
        if(epic.isSubTaskExists(subTaskId)){
            epic.removeSubTask(subTaskId);
            updateEpicStatus(epic);
            updateEpic(epic);
        }
    }
    public int getNewSubTaskId() {
        int maxId = 0;
        for (Integer key : subTaskMap.keySet()) {
            if (key > maxId) {
                maxId = key;
            }
        }
        return maxId + 1;
    }

    public void removeAllSubTasks() {
        Object[] subTaskIdList = subTaskMap.keySet().toArray();

        for (Object subTaskId : subTaskIdList){
            deleteSubTask((Integer)subTaskId);
        }
    }

    public void printAllSubTasks(){
        System.out.println(subTaskMap.toString());
    }

// General
    public boolean statusValidation(String status) {
        boolean checkResult = false;
        for (String validStatus : validStatuses) {
            if (status.equals(validStatus)) {
                checkResult = true;
                break;
            }
        }
        return checkResult;
    }
}


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Manager {

    HashMap<Integer, Task> taskMap = new HashMap<>();
    HashMap<Integer, Epic> epicMap = new HashMap<>();
    HashMap<Integer, SubTask> subTaskMap = new HashMap<>();
    String[] validStatuses = {"NEW", "IN_PROGRESS", "DONE"};


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
            System.out.println("Невозможно записать в систему задачу " + task.toString() + " - статус не прошел валидацию");
        }
    }

    public void updateTask(Task task) {
        if (task == null) {
            System.out.println("Передан пустой объект. Запись в базу невозможна");
            return;
        }
        int taskId = task.getTaskID();
        if (taskMap.containsKey(taskId) == false) {
            System.out.println("Невозможно обновить запись " + task.toString() + " - данный ID отсутствует в базе");
            return;
        }

        if (statusValidation(task.getStatus())) {
            taskMap.put(taskId, task);
        } else {
            System.out.println("Невозможно записать в систему задачу " + task.toString() + " - статус не прошел валидацию");
        }
    }

    public void deleteTask(int taskId) {
        if (taskMap.containsKey(taskId) == false) {
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
        if (taskMap.containsKey(epicId) == false) {
            System.out.println("Невозможно обновить запись " + epic.toString() + " - данный ID отсутствует в базе");
            return;
        }
            epicMap.put(epicId, epic);
    }

    public void deleteEpic(int epicId){
        if (epicMap.containsKey(epicId) == false) {
            System.out.println("Невозможно удалить запись №" + epicId + " - данный ID отсутствует в базе");
            return;
        }
        taskMap.remove(epicId);
    }

    public void removeAllEpics() {
        epicMap.clear();
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
        ArrayList<SubTask> subTasksList = epic.getSubTasksList();
        if (subTasksList.size() == 0){
            epic.setStatus("NEW");
            return;
        }
        int newSubs = 0;
        int progressSubs = 0;
        int doneSubs = 0;
        String status;
        for (SubTask subTask : subTasksList) {
            switch (subTask.getStatus()){
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
            System.out.println("Невозможно записать в систему задачу " + subTask.toString() + " - статус не прошел валидацию либо указан некорректный ID эпика");
        }


    }
    public void addSubTaskToEpic(SubTask subTask){
        int epicId = subTask.getEpicId();
        Epic epic = epicMap.get(epicId);
        if(epic.isSubTaskExists(subTask) == false){
            epic.addNewSubTask(subTask);
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
        subTaskMap.clear();
    }

// General
    public boolean statusValidation(String status) {
        boolean checkResult = false;
        for (String validStatus : validStatuses) {
            if (status.equals(validStatus)) {
                checkResult = true;
            }
        }
        return checkResult;
    }
}


package manager;

import model.*;
import java.nio.file.*;
import java.io.*;
import java.util.Collections;
import java.util.List;


public class FileBackedTaskManager extends InMemoryTaskManager{

    private void save() throws ManagerSaveException {
        try {
            Writer fileWriter = new FileWriter ("fileManager.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("id,type,name,description,epic");
            bufferedWriter.newLine();
            for(Epic epic: epics.values()){
                bufferedWriter.write(epic.toFileString());
                bufferedWriter.newLine();
            }

            for(Task task: tasks.values()){
                bufferedWriter.write(task.toFileString());
                bufferedWriter.newLine();
            }

            for(SubTask subTask: subTasks.values()){
                bufferedWriter.write(subTask.toFileString());
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            bufferedWriter.write(historyToString(historyManager));
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException exception){
            throw new ManagerSaveException();
        }

    }

    private String historyToString(HistoryManager manager){
        return manager.getTasksId();
    }
    private void historyFromString(String value){
        if (value.isBlank()){
            return;
        }
        String[] history = value.split(",");
        for (String unit: history) {
            ;
            if (getEpic(Integer.parseInt(unit)) != null) {
                historyManager.add(getEpic(Integer.parseInt(unit)));
            } else if (getTask(Integer.parseInt(unit)) != null) {
                historyManager.add(getTask(Integer.parseInt(unit)));
            } else if (getSubTask(Integer.parseInt(unit)) != null) {
                historyManager.add(getSubTask(Integer.parseInt(unit)));
            } else {
                System.out.println("Запись с id = " + unit + " невозможно добавить в историю - не найдена в базе");
            }
        }
    }
    public static FileBackedTaskManager loadFromFile(String path){
        FileBackedTaskManager manager = new FileBackedTaskManager();
            List<String> reportData = manager.readFileContents(path);
            for (int i = 1; i< reportData.size()-2;i++) {
                String line = reportData.get(i);
                Task task = manager.getTaskFromString(line);
                switch (task.getType()){
                    case TASK:
                        manager.createTask(task);
                        break;
                    case EPIC:
                        manager.createEpic((Epic)task);
                        break;
                    case SUBTASK:
                        manager.createSubTask((SubTask)task);
                        break;
                }
                if (task.getId() > manager.idManager){
                    manager.idManager = task.getId();
                }
            }
            manager.historyFromString(reportData.get(reportData.size()-1).replace("[","").replace("]","").replace(" ",""));
            return manager;
    }

    List<String> readFileContents (String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }

    private Task getTaskFromString(String value){
        String[] data = value.split(",");
        Task task = null;
        switch (TaskType.valueOf(data[1])) {
            case TASK:
                task = new Task(Integer.parseInt(data[0]), data[2], data[3], Status.valueOf(data[4]));
                break;
            case EPIC:
                task = new Epic(Integer.parseInt(data[0]), data[2], data[3]);
                break;
            case SUBTASK:
                task = new SubTask(Integer.parseInt(data[0]), data[2], data[3], Status.valueOf(data[4]), Integer.parseInt(data[5]));
                break;
            default:
                break;
        }
        return task;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void updateTask(Task task){
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteTask(int Id){
        super.deleteTask(Id);
        save();
    }

    @Override
    public void removeAllTasks(){
        super.removeAllTasks();
        save();
    }

    @Override
    public void createEpic(Epic epic){
    super.createEpic(epic);
    save();
    }

    @Override
    public void updateEpic(Epic epic){
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteEpic(int id){
        super.deleteEpic(id);
        save();
    }

    @Override
    public void removeAllEpics(){
        super.removeAllEpics();
        save();
    }

    @Override
    public void createSubTask(SubTask subTask){
        super.createSubTask(subTask);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask){
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void deleteSubTask(int id){
        super.deleteSubTask(id);
        save();
    }

    @Override
    public void removeAllSubTasks(){
        super.removeAllSubTasks();
        save();
    }
    @Override
    public Task getTask(int id){
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public SubTask getSubTask(int id){
        SubTask subTask = super.getSubTask(id);
        save();
        return subTask;
    }

    @Override
    public Epic getEpic(int id){
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }


}

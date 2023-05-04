package manager;

import model.Epic;
import model.SubTask;
import model.Task;

public class FileBackedTaskManager extends InMemoryTaskManager{

    private void save(){

    }

    private void read(){

    }

    private Task fromString(String value){
        return null;
    }

    @Override
    public void createTask(Task task){
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


}

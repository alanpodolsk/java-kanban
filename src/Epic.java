import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<SubTask> subTasks;
    public Epic(String name, String description) {
        this.taskID = 0;
        this.name = name;
        this.description = description;
        this.subTasks = new ArrayList<>();
    }

    public void addNewSubTask(SubTask subTask){
        this.subTasks.add(subTask);
    }

    public void removeSubTask(SubTask subTask){
        this.subTasks.remove(subTask);
    }

    public ArrayList<SubTask> getSubTasksList(){
        return this.subTasks;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public boolean isSubTaskExists(SubTask subTask){
        return this.subTasks.contains(subTask);
    }
}

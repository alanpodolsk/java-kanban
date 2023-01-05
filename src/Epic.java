import java.util.ArrayList;
import java.util.Arrays;

public class Epic extends Task{
    private ArrayList<Integer> subTasksId;
    public Epic(String name, String description) {
        this.taskID = 0;
        this.name = name;
        this.description = description;
        this.status = "NEW";
        this.subTasksId = new ArrayList<>();
    }

    public void addNewSubTask(Integer subTaskId){
        this.subTasksId.add(subTaskId);
    }

    public void removeSubTask(Integer subTaskId){
        this.subTasksId.remove(subTaskId);
    }

    public ArrayList<Integer> getSubTasksList(){
        return this.subTasksId;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public boolean isSubTaskExists(int subTaskId){
        return this.subTasksId.contains(subTaskId);
    }
    @Override
    public String toString() {
        return "Task(ID=" + this.taskID + ", name=" + this.name + ", description.length=" + description.length() + ", status=" + this.status+", subTasksId="+ Arrays.toString(subTasksId.toArray());
    }
}

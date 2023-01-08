package elements;

import util.Statuses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Epic extends Task {
    private ArrayList<Integer> subTasksId;
    public Epic(String name, String description) {
        this.taskID = 0;
        this.name = name;
        this.description = description;
        this.status = Statuses.NEW;
        this.subTasksId = new ArrayList<>();
    }

    public void addNewSubTask(Integer subTaskId){
        this.subTasksId.add(subTaskId);
    }

    public void removeSubTask(Integer subTaskId){
        this.subTasksId.remove(subTaskId);
    }

    public List<Integer> getSubTasksList(){
        return this.subTasksId;
    }

    public boolean isSubTaskExists(int subTaskId){
        return this.subTasksId.contains(subTaskId);
    }
    @Override
    public String toString() {
        return "elements.Epic(ID=" + this.taskID + ", name=" + this.name + ", description.length=" + description.length() + ", status=" + this.status+", subTasksId="+ Arrays.toString(subTasksId.toArray());
    }
}

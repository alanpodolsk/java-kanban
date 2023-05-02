package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private List<Integer> subTasksIds;
    public Epic(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.subTasksIds = new ArrayList<>();
    }

    public void addNewSubTask(Integer subTaskId){
        this.subTasksIds.add(subTaskId);
    }

    public void removeSubTask(Integer subTaskId){
        this.subTasksIds.remove(subTaskId);
    }

    public List<Integer> getSubTasksList(){
        return this.subTasksIds;
    }

    public boolean isSubTaskExists(int subTaskId){
        return this.subTasksIds.contains(subTaskId);
    }
    @Override
    public String toString() {
        return "elements.Epic(ID=" + this.id + ", name=" + this.name + ", description.length=" + description.length() + ", status=" + this.status+", subTasksId="+ Arrays.toString(subTasksIds.toArray());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasksIds, epic.subTasksIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasksIds);
    }
}

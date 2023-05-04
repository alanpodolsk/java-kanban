package model;

import java.util.Objects;

public class SubTask extends Task {

    private int epicId;

    public SubTask(int id, String name, String description, Status status, int epicId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.epicId = epicId;
    }

    public int getEpicId(){
        return epicId;
    }

    public void setEpicId (int epicId){
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "subTask(ID=" + this.id + ", name=" + this.name + ", description.length=" + description.length() + ", status=" + this.status + ", epicId="+this.epicId;
    }
    @Override
    public String toFileString(){
        return super.toFileString()+","+this.epicId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicId == subTask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }
}


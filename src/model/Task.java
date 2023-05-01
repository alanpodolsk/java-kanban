package model;

import java.util.Objects;

public class Task {

    protected int id;
    protected String name;
    protected String description;
    protected Status status;

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && name.equals(task.name) && description.equals(task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status);
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Status getStatus(){
        return this.status;
    }
    public void setStatus(Status status){
        this.status = status;
    }
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        if (name != null){
            this.name = name;
        }
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        if (description != null){
            this.description = description;
        }
    }

    @Override
    public String toString(){
        return "elements.Task(ID="+this.id +", name="+this.name+", description.length="+description.length()+", status="+this.status;
    }
}


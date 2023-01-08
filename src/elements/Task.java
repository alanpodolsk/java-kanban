package elements;

import util.Statuses;

public class Task {

    protected int taskID;
    protected String name;
    protected String description;
    protected Statuses status;

    public Task(String name, String description, Statuses status) {
        this.taskID = 0;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task() {
    }

    public int getTaskID(){
        return this.taskID;
    }

    public void setTaskID(int taskID){
        this.taskID = taskID;
    }

    public Statuses getStatus(){
        return this.status;
    }
    public void setStatus(Statuses status){
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
        return "elements.Task(ID="+this.taskID+", name="+this.name+", description.length="+description.length()+", status="+this.status;
    }
}


public class SubTask extends Task {

    private int epicId;

    public SubTask(String name, String description, String status, int epicId) {
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
        return "subTask(ID=" + this.taskID + ", name=" + this.name + ", description.length=" + description.length() + ", status=" + this.status + ", epicId="+this.epicId;
    }
}


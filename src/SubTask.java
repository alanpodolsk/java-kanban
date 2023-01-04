public class SubTask extends Task {

    private int epicId;

    public SubTask(String name, String description, String status, int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId(){
        return epicId;
    }

}


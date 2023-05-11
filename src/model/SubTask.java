package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static model.TaskType.SUBTASK;

public class SubTask extends Task {

    private Integer epicId;

    public SubTask(int id, String name, String description, Status status, long duration, LocalDateTime startTime, int epicId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.epicId = epicId;
        this.type = SUBTASK;
        this.duration = Duration.ofMinutes(duration);
        this.startTime = startTime;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "subTask(ID=" + this.id + ", name=" + this.name + ", description.length=" + description.length() + ", status=" + this.status + ", startTime=" + this.startTime + ", epicId=" + this.epicId;
    }

    @Override
    public String toFileString(DateTimeFormatter formatter) {
        return super.toFileString(formatter) + "," + this.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicId == subTask.epicId;
    }
}


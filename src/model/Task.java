package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static model.TaskType.TASK;

public class Task {

    protected Integer id;
    protected String name;
    protected String description;
    protected Status status;
    protected TaskType type;
    protected Duration duration;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected Integer epicId;
    protected List<Integer> subTasksIds;

    public Task(Integer id, String name, String description, Status status, long duration, LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = TASK;
        this.duration = Duration.ofMinutes(duration);
        this.startTime = startTime;
    }

    public Task() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public TaskType getType() {
        return this.type;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    ;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    ;

    public Duration getDuration() {
        return this.duration;
    }


    public String toFileString(DateTimeFormatter formatter) {
        return this.id + "," + this.type + "," + this.name + "," + this.description + "," + this.status + "," + this.duration.toMinutes() + "," +
                this.startTime.format(formatter);
    }

    public void setDuration(long duration) {
        this.duration = Duration.ofMinutes(duration);
    }

    @Override
    public String toString() {
        return "elements(Type= "+this.type+"ID=" + this.id + ", name=" + this.name + ", description.length=" + description.length() + ", status=" + this.status + ", startTime=" + this.startTime+
                "endTime="+this.endTime+"epicId="+this.epicId+"SubTaskIds="+this.subTasksIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && name.equals(task.name) && description.equals(task.description) && status == task.status && type == task.type && Objects.equals(duration, task.duration) && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status);
    }
}


package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static model.TaskType.EPIC;

public class Epic extends Task {
    private List<Integer> subTasksIds;
    private LocalDateTime endTime;

    public Epic(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.subTasksIds = new ArrayList<>();
        this.type = EPIC;
    }

    public void addNewSubTask(Integer subTaskId) {
        this.subTasksIds.add(subTaskId);
    }

    public void removeSubTask(Integer subTaskId) {
        this.subTasksIds.remove(subTaskId);
    }

    public List<Integer> getSubTasksList() {
        return this.subTasksIds;
    }

    public boolean isSubTaskExists(int subTaskId) {
        return this.subTasksIds.contains(subTaskId);
    }

    public void setStartTime(LocalDateTime time) {
        this.startTime = time;
    }

    public void setEndTime(LocalDateTime time) {
        this.endTime = time;
    }

    public void setDuration() {
        if (this.startTime != null && this.endTime != null) {
            this.duration = Duration.between(this.startTime, this.endTime);
        } else {
            this.duration = Duration.ofMinutes(0);
        }
    }

    @Override
    public String toString() {
        return "elements.Epic(ID=" + this.id + ", name=" + this.name + ", description.length=" + description.length() + ", status=" + this.status + ", subTasksId=" + Arrays.toString(subTasksIds.toArray());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasksIds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasksIds, epic.subTasksIds) && Objects.equals(endTime, epic.endTime);
    }
}


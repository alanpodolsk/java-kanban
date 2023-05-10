import manager.FileBackedTaskManager;
import manager.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.SubTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EpicStatusTest {
    InMemoryTaskManager manager;
    Epic epic;

    @BeforeEach
    public void createManager() {
        manager = new InMemoryTaskManager();
        epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
    }


    @Test
    @DisplayName("Epic should set status NEW without subtasks")
    public void shouldSetEpicStatusNewWithoutSubs() {
        //Arrange
        final int id = epic.getId();
        //Act
        manager.createEpic(epic);
        //Assert
        assertNotNull(epic,"Эпик не должен быть null");
        assertEquals(Status.NEW, manager.getEpic(id).getStatus(), "Установлен некорректный статус");
    }

    @Test
    @DisplayName("Epic should set status NEW with 2 New subtasks")
    public void shouldSetEpicStatusNewBy2NewSubs() {
        //Arrange
        final int id = epic.getId();
        manager.createEpic(epic);
        //Act
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), id));
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить кухню", "Включить пылесос и пропылесосить кухню", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 11, 30, 0, 0), id));
        //Assert
        assertNotNull(epic,"Эпик не должен быть null");
        assertEquals(Status.NEW, manager.getEpic(id).getStatus(), "Установлен некорректный статус");
    }

    @Test
    @DisplayName("Epic should set status DONE with 2 Done subtasks")
    public void shouldSetEpicStatusDoneBy2DoneSubs() {
        //Arrange
        final int id = epic.getId();
        manager.createEpic(epic);
        //Act
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.DONE, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), id));
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить кухню", "Включить пылесос и пропылесосить кухню", Status.DONE, 25, LocalDateTime.of(2023, 2, 5, 11, 30, 0, 0), id));
        //Assert
        assertNotNull(epic,"Эпик не должен быть null");
        assertEquals(Status.DONE, manager.getEpic(id).getStatus(), "Установлен некорректный статус");
    }

    @Test
    @DisplayName("Epic should set status IN PROGRESS with 1 New & 1 Done subtasks")
    public void shouldSetEpicStatusInProgressByNewAndDoneSubs() {
        //Arrange
        final int id = epic.getId();
        manager.createEpic(epic);
        //Act
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), id));
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить кухню", "Включить пылесос и пропылесосить кухню", Status.DONE, 25, LocalDateTime.of(2023, 2, 5, 11, 30, 0, 0), id));
        //Assert
        assertNotNull(epic,"Эпик не должен быть null");
        assertEquals(Status.IN_PROGRESS, manager.getEpic(id).getStatus(), "Установлен некорректный статус");
    }

    @Test
    @DisplayName("Epic should set status IN PROGRESS with 1 New & 1 In_Progress subtasks")
    public void shouldSetEpicStatusInProgressByNewAndInProgressSubs() {
        //Arrange
        final int id = epic.getId();
        manager.createEpic(epic);
        //Act
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), id));
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить кухню", "Включить пылесос и пропылесосить кухню", Status.IN_PROGRESS, 25, LocalDateTime.of(2023, 2, 5, 11, 30, 0, 0), id));
        //Assert
        assertNotNull(epic,"Эпик не должен быть null");
        assertEquals(Status.IN_PROGRESS, manager.getEpic(id).getStatus(), "Установлен некорректный статус");
    }

    @Test
    @DisplayName("Epic should set status IN PROGRESS with 2 In_Progress subtasks")
    public void shouldSetEpicStatusInProgressBy2AndInProgressSubs() {
        //Arrange
        final int id = epic.getId();
        manager.createEpic(epic);
        //Act
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.IN_PROGRESS, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), id));
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить кухню", "Включить пылесос и пропылесосить кухню", Status.IN_PROGRESS, 25, LocalDateTime.of(2023, 2, 5, 11, 30, 0, 0), id));
        //Assert
        assertNotNull(epic,"Эпик не должен быть null");
        assertEquals(Status.IN_PROGRESS, manager.getEpic(id).getStatus(), "Установлен некорректный статус");
    }
}

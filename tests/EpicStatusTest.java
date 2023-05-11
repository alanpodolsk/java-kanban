import manager.FileBackedTaskManager;
import manager.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.SubTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicStatusTest {
    InMemoryTaskManager manager;

    @BeforeEach
    public void createManager() {
        manager = new InMemoryTaskManager();
    }


    @Test
    public void ShouldSetEpicStatusNewWithoutSubs() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        final int id = epic.getId();
        manager.createEpic(epic);
        assertEquals(Status.NEW, manager.getEpic(id).getStatus(), "Установлен некорректный статус");
    }

    @Test
    public void ShouldSetEpicStatusNewBy2NewSubs() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        final int id = epic.getId();
        manager.createEpic(epic);
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), id));
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить кухню", "Включить пылесос и пропылесосить кухню", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 11, 30, 0, 0), id));

        assertEquals(Status.NEW, manager.getEpic(id).getStatus(), "Установлен некорректный статус");
    }

    @Test
    public void ShouldSetEpicStatusDoneBy2DoneSubs() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        final int id = epic.getId();
        manager.createEpic(epic);
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.DONE, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), id));
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить кухню", "Включить пылесос и пропылесосить кухню", Status.DONE, 25, LocalDateTime.of(2023, 2, 5, 11, 30, 0, 0), id));
        assertEquals(Status.DONE, manager.getEpic(id).getStatus(), "Установлен некорректный статус");
    }

    @Test
    public void ShouldSetEpicStatusInProgressByNewAndDoneSubs() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        final int id = epic.getId();
        manager.createEpic(epic);
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), id));
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить кухню", "Включить пылесос и пропылесосить кухню", Status.DONE, 25, LocalDateTime.of(2023, 2, 5, 11, 30, 0, 0), id));
        assertEquals(Status.IN_PROGRESS, manager.getEpic(id).getStatus(), "Установлен некорректный статус");
    }

    @Test
    public void ShouldSetEpicStatusInProgressByNewAndInProgressSubs() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        final int id = epic.getId();
        manager.createEpic(epic);
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), id));
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить кухню", "Включить пылесос и пропылесосить кухню", Status.IN_PROGRESS, 25, LocalDateTime.of(2023, 2, 5, 11, 30, 0, 0), id));
        assertEquals(Status.IN_PROGRESS, manager.getEpic(id).getStatus(), "Установлен некорректный статус");
    }

    @Test
    public void ShouldSetEpicStatusInProgressBy2AndInProgressSubs() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        final int id = epic.getId();
        manager.createEpic(epic);
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.IN_PROGRESS, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), id));
        manager.createSubTask(new SubTask(manager.getNewId(), "Пропылесосить кухню", "Включить пылесос и пропылесосить кухню", Status.IN_PROGRESS, 25, LocalDateTime.of(2023, 2, 5, 11, 30, 0, 0), id));
        assertEquals(Status.IN_PROGRESS, manager.getEpic(id).getStatus(), "Установлен некорректный статус");
    }
}

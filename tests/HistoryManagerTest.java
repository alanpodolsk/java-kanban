import manager.FileBackedTaskManager;
import manager.HistoryManager;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryManagerTest {
    FileBackedTaskManager taskManager;
    HistoryManager historyManager;

    @BeforeEach
    public void createManagers() {
        taskManager = new FileBackedTaskManager();
        historyManager = taskManager.getHistoryManagerFromTaskManager();
    }

    @Test
    @DisplayName("Should add 1 task to history")
    public void shouldAddTaskToHistory() {
        //Arrange
        Epic epic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        //Act
        historyManager.add(epic1);
        //Assert
        assertArrayEquals(new Task[]{epic1}, historyManager.getHistory().toArray(), "Задача не добавлена в историю");
    }

    @Test
    @DisplayName("Should rewrite the task in history after the second add()")
    public void shouldNotDuplicateTaskInHistory() {
        //Arrange
        Epic epic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        //Act
        historyManager.add(epic1);
        historyManager.add(epic1);
        //Assert
        assertArrayEquals(new Task[]{epic1}, historyManager.getHistory().toArray(), "Задача в истории задублировалась");
    }

    @Test
    @DisplayName("Should return empty history without add() callings")
    public void shouldReturnEmptyHistory() {
        //Assert
        assertArrayEquals(new Task[]{}, historyManager.getHistory().toArray(), "Вернут непустой список");
    }

    @Test
    @DisplayName("Should remove 1st element from history")
    public void shouldRemove1stEpicFromHistory() {
        //Arrange
        Epic epic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask1 = new SubTask(2, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        Task task1 = new Task(3, "Помыть второго ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 10, 0, 0, 0));
        //Act
        historyManager.add(epic1);
        historyManager.add(subTask1);
        historyManager.add(task1);
        assertArrayEquals(new Task[]{task1, subTask1, epic1}, historyManager.getHistory().toArray(), "Задача не добавлена в историю");
        historyManager.remove(1);
        //Assert
        assertArrayEquals(new Task[]{task1, subTask1}, historyManager.getHistory().toArray(), "Задача не удалена из истории");
    }

    @Test
    @DisplayName("Should remove 2nd element from history")
    public void shouldRemove2ndSubTaskFromHistory() {
        //Arrange
        Epic epic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask1 = new SubTask(2, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        Task task1 = new Task(3, "Помыть второго ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 10, 0, 0, 0));
        //Act
        historyManager.add(epic1);
        historyManager.add(subTask1);
        historyManager.add(task1);
        assertArrayEquals(new Task[]{task1, subTask1, epic1}, historyManager.getHistory().toArray(), "Задача не добавлена в историю");
        historyManager.remove(2);
        //Assert
        assertArrayEquals(new Task[]{task1, epic1}, historyManager.getHistory().toArray(), "Задача не удалена из истории");
    }

    @Test
    @DisplayName("Should remove 3rd element from history")
    public void shouldRemove3rdTaskFromHistory() {
        //Arrange
        Epic epic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask1 = new SubTask(2, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        Task task1 = new Task(3, "Помыть второго ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 10, 0, 0, 0));
        //Act
        historyManager.add(epic1);
        historyManager.add(subTask1);
        historyManager.add(task1);
        assertArrayEquals(new Task[]{task1, subTask1, epic1}, historyManager.getHistory().toArray(), "Задача не добавлена в историю");
        historyManager.remove(3);
        //Assert
        assertArrayEquals(new Task[]{subTask1, epic1}, historyManager.getHistory().toArray(), "Задача не удалена из истории");
    }

    @Test
    @DisplayName("Should return array of 3 task ids from history")
    public void shouldReturnHistoryString() {
        //Arrange
        Epic epic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        Epic epic2 = new Epic(2, "Убраться в комнате", "Пропылесосить комнату");
        Epic epic3 = new Epic(3, "Убраться в комнате", "Пропылесосить комнату");
        //Act
        historyManager.add(epic1);
        historyManager.add(epic2);
        historyManager.add(epic3);
        //Assert
        assertEquals("[3, 2, 1]", historyManager.getTasksId(), "Выгружен некорректный список истории");
    }

    @Test
    @DisplayName("Should return empty array from history without add() callings")
    public void shouldReturnEmptyHistoryString() {
        //Assert
        assertEquals("[]", historyManager.getTasksId(), "Выгружен некорректный список истории");
    }


}

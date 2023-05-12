import manager.FileBackedTaskManager;
import manager.HttpTaskManager;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.KVServer;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class HttpTaskManagerTest extends TaskManagerTest{
    KVServer server;
    Epic testEpic1;
    Task testTask1;
    SubTask testSubTask1;
    @BeforeEach
    public void createManager() throws IOException {
        server = new KVServer();
        server.start();
        manager = new HttpTaskManager();
        testEpic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        testTask1 = new Task(3, "Купить продукты", "Сходить в магазин", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 14, 30, 0, 0));
        testSubTask1 = new SubTask(2, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.DONE, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);

    }
    @Test
    @DisplayName("Должен сохранить и выгрузить в новом менеджере перечень эпиков")
    public void shouldSaveAndReturnEpics(){
        manager.createEpic(testEpic1);
        HttpTaskManager httpManager = HttpTaskManager.loadFromServer();
        assertArrayEquals(manager.getEpics().toArray(),httpManager.getEpics().toArray(),"Состояния менеджеров не совпадают");
    }
    @Test
    @DisplayName("Должен сохранить и выгрузить в новом менеджере перечень задач")
    public void shouldSaveAndReturnTasks(){
        manager.createTask(testTask1);
        HttpTaskManager httpManager = HttpTaskManager.loadFromServer();
        assertArrayEquals(manager.getTasks().toArray(),httpManager.getTasks().toArray(),"Состояния менеджеров не совпадают");
    }
    @Test
    @DisplayName("Должен сохранить и выгрузить в новом менеджере перечень задач")
    public void shouldSaveAndReturnSubTasks(){
        manager.createEpic(testEpic1);
        manager.createSubTask(testSubTask1);
        HttpTaskManager httpManager = HttpTaskManager.loadFromServer();
        assertArrayEquals(manager.getSubTasks().toArray(),httpManager.getSubTasks().toArray(),"Состояния менеджеров не совпадают");
    }

    @Test
    @DisplayName("Должен сохранить и выгрузить в новом менеджере историю просмотра задач")
    public void shouldSaveHistory(){
        manager.createTask(testTask1);
        manager.getTask(1);
        HttpTaskManager httpManager = HttpTaskManager.loadFromServer();
        assertArrayEquals(manager.getHistory().toArray(),httpManager.getHistory().toArray(),"Состояния менеджеров не совпадают");
    }
    @AfterEach
    public void stopServer() throws IOException {
        server.stopServer();
    }

}

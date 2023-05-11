import manager.FileBackedTaskManager;
import manager.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest extends TaskManagerTest {
    final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @BeforeEach
    public void createManager() {
        manager = new FileBackedTaskManager();
    }

    @Test
    public void ShouldReturn1EpicFromFile() {
        FileBackedTaskManager fbManager = FileBackedTaskManager.loadFromFile("testFileManager.csv");
        Epic testEpic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        fbManager.updateEpic(testEpic1);
        assertArrayEquals(new Epic[]{testEpic1}, fbManager.getEpics().toArray(), "Метод вернул некорректный перечень эпиков");
    }

    @Test
    public void ShouldReturn1TaskFromFile() {
        FileBackedTaskManager fbManager = FileBackedTaskManager.loadFromFile("testFileManager.csv");
        Task testTask1 = new Task(3, "Купить продукты", "Сходить в магазин", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 14, 30, 0, 0));
        assertArrayEquals(new Task[]{testTask1}, fbManager.getTasks().toArray(), "Метод вернул некорректный перечень тасок");
    }

    @Test
    public void ShouldReturn1SubTaskFromFile() {
        FileBackedTaskManager fbManager = FileBackedTaskManager.loadFromFile("testFileManager.csv");
        SubTask testSubTask1 = new SubTask(2, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.DONE, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        assertArrayEquals(new SubTask[]{testSubTask1}, fbManager.getSubTasks().toArray(), "Метод вернул некорректный перечень субтасок");
    }

    @Test
    public void ShouldRecordDataInCSV() {
        Epic testEpic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        Task testTask1 = new Task(3, "Купить продукты", "Сходить в магазин", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 14, 30, 0, 0));
        SubTask testSubTask1 = new SubTask(2, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.DONE, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        manager.createEpic(testEpic1);
        manager.createTask(testTask1);
        manager.createSubTask(testSubTask1);
        testEpic1 = manager.getEpic(1);
        List<String> fileLines = readFileContents("fileManager.csv");
        assertEquals(testEpic1.toFileString(DATE_TIME_FORMAT), fileLines.get(1), "Содержимое эпика сохранено некорректно");
        assertEquals(testTask1.toFileString(DATE_TIME_FORMAT), fileLines.get(2), "Содержимое таска сохранено некорректно");
        assertEquals(testSubTask1.toFileString(DATE_TIME_FORMAT), fileLines.get(3), "Содержимое субтаска сохранено некорректно");
    }

    private List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }

}

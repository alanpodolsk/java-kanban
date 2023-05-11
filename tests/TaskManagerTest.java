import manager.HistoryManager;
import manager.TaskManager;
import manager.TaskManagerException;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    public T manager;

    @Test
    public void ShouldCreateTask() {
        Task task = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        final int id = task.getId();
        manager.createTask(task);
        assertNotNull(manager.getTask(id), "Задача не сохранена в HashMap");
        assertEquals(task, manager.getTask(id), "Задачи не совпадают");
    }

    @Test
    public void ShouldCreateSubTask() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask = new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        manager.createEpic(epic);
        final int id = subTask.getId();
        manager.createSubTask(subTask);
        assertNotNull(manager.getSubTask(id), "Задача не сохранена в HashMap");
        assertEquals(subTask, manager.getSubTask(id), "Задачи не совпадают");
    }

    @Test
    public void ShouldCreateEpic() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        final int id = epic.getId();
        manager.createEpic(epic);
        assertNotNull(manager.getEpic(id), "Задача не сохранена в HashMap");
        assertEquals(epic, manager.getEpic(id), "Задачи не совпадают");
    }

    @Test
    public void ShouldDeleteTask() {
        Task task = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        final int id = task.getId();
        manager.createTask(task);
        assertNotNull(manager.getTask(id), "Задача не сохранена в HashMap");
        manager.deleteTask(id);
        assertNull(manager.getTask(id), "Задача не удалена из HashMap");
    }

    @Test
    public void ShouldDeleteSubTask() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask = new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        manager.createEpic(epic);
        final int id = subTask.getId();
        manager.createSubTask(subTask);
        assertNotNull(manager.getSubTask(id), "Задача не сохранена в HashMap");
        manager.deleteSubTask(id);
        assertNull(manager.getSubTask(id), "Задача не удалена из HashMap");
    }

    @Test
    public void ShouldDeleteEpic() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        final int id = epic.getId();
        manager.createEpic(epic);
        assertNotNull(manager.getEpic(id), "Задача не сохранена в HashMap");
        manager.deleteEpic(id);
        assertNull(manager.getEpic(id), "Задача не удалена из HashMap");
    }

    @Test
    public void ShouldUpdateTask() {
        Task task = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        final int id = task.getId();
        manager.createTask(task);
        assertNotNull(manager.getTask(id), "Задача не сохранена в HashMap");
        task.setStatus(Status.DONE);
        manager.updateTask(task);
        assertEquals(task, manager.getTask(id), "Задачи не совпадают");
    }

    @Test
    public void ShouldUpdateSubTask() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask = new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        manager.createEpic(epic);
        final int id = subTask.getId();
        manager.createSubTask(subTask);
        assertNotNull(manager.getSubTask(id), "Задача не сохранена в HashMap");
        subTask.setStatus(Status.DONE);
        manager.updateSubTask(subTask);
        assertEquals(subTask, manager.getSubTask(id), "Задачи не совпадают");
    }

    @Test
    public void ShouldUpdateEpic() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        final int id = epic.getId();
        manager.createEpic(epic);
        assertNotNull(manager.getEpic(id), "Задача не сохранена в HashMap");
        epic.setDescription("Убраться во всей квартире");
        manager.updateEpic(epic);
        assertEquals(epic, manager.getEpic(id), "Задачи не совпадают");
    }

    @Test
    public void ShouldRemoveAllTasks() {
        Task task1 = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        Task task2 = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 14, 0, 0, 0));
        manager.createTask(task1);
        manager.createTask(task2);
        assertEquals(2, manager.getTasks().size(), "Задачи не сохранены в HashMap");
        manager.removeAllTasks();
        assertEquals(0, manager.getTasks().size(), "Задачи не удалены из HashMap");
    }

    @Test
    public void ShouldRemoveSubTasks() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask1 = new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        SubTask subTask2 = new SubTask(manager.getNewId(), "Пропылесосить вторую комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 11, 48, 0, 0), 1);
        manager.createEpic(epic);
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask2);
        assertEquals(2, manager.getSubTasks().size(), "Задачи не сохранены в HashMap");
        manager.removeAllSubTasks();
        assertEquals(0, manager.getSubTasks().size(), "Задачи не удалены из HashMap");
    }

    @Test
    public void ShouldRemoveAllEpics() {
        Epic epic1 = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        Epic epic2 = new Epic(manager.getNewId(), "Переехать в Ботсвану", "Переехать в Ботсвану");
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        assertEquals(2, manager.getEpics().size(), "Задачи не сохранены в HashMap");
        manager.removeAllEpics();
        assertEquals(0, manager.getEpics().size(), "Задачи не удалены из HashMap");
    }

    @Test
    public void ShouldGet2Tasks() {
        Task task1 = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        Task task2 = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 14, 0, 0, 0));
        manager.createTask(task1);
        manager.createTask(task2);
        assertArrayEquals(new Task[]{task1, task2}, manager.getTasks().toArray(), "Метод не вернул задачи");
    }

    @Test
    public void ShouldGet0Tasks() {
        assertArrayEquals(new Task[]{}, manager.getTasks().toArray(), "Метод вернул задачи");
    }

    @Test
    public void ShouldGet2SubTasks() {
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask1 = new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        SubTask subTask2 = new SubTask(manager.getNewId(), "Пропылесосить вторую комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 11, 48, 0, 0), 1);
        manager.createEpic(epic);
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask2);
        assertArrayEquals(new SubTask[]{subTask1, subTask2}, manager.getSubTasks().toArray(), "Задачи не сохранены в HashMap");
    }

    @Test
    public void ShouldGet0SubTasks() {
        assertArrayEquals(new SubTask[]{}, manager.getSubTasks().toArray(), "Метод вернул задачи");
    }

    @Test
    public void ShouldGet2Epics() {
        Epic epic1 = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        Epic epic2 = new Epic(manager.getNewId(), "Переехать в Ботсвану", "Переехать в Ботсвану");
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        assertArrayEquals(new Epic[]{epic1, epic2}, manager.getEpics().toArray(), "Метод не вернул задачи");
    }

    @Test
    public void ShouldGet0Epics() {
        assertArrayEquals(new Epic[]{}, manager.getEpics().toArray(), "Метод вернул задачи");
    }

    @Test
    public void ShouldGet0PrioritizedTasks() {
        assertArrayEquals(new Task[]{}, manager.getPrioritizedTasks().toArray(), "Метод вернул задачи");
    }

    @Test
    public void ShouldGet2PrioritizedTasksInOrder() {
        Epic testEpic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        Task testTask1 = new Task(3, "Купить продукты", "Сходить в магазин", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 14, 30, 0, 0));
        SubTask testSubTask1 = new SubTask(2, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.DONE, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        manager.createEpic(testEpic1);
        manager.createTask(testTask1);
        manager.createSubTask(testSubTask1);
        manager.updateTask(testTask1);
        manager.updateSubTask(testSubTask1);
        assertArrayEquals(new Task[]{testSubTask1, testTask1}, manager.getPrioritizedTasks().toArray(), "Метод не вернул задачи");
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenCreatedTaskIsNull() {
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.createTask(null)
        );
        Assertions.assertEquals("Передан пустой объект. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenCreatedEpicIsNull() {
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.createEpic(null)
        );
        Assertions.assertEquals("Передан пустой объект. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenCreatedSubTaskIsNull() {
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.createSubTask(null)
        );
        Assertions.assertEquals("Передан пустой объект. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenUpdatedTaskIsNull() {
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateTask(null)
        );
        Assertions.assertEquals("Передан пустой объект. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenUpdatedEpicIsNull() {
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateEpic(null)
        );
        Assertions.assertEquals("Передан пустой объект. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenUpdatedSubTaskIsNull() {
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateSubTask(null)
        );
        Assertions.assertEquals("Передан пустой объект. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenUpdatedTaskNotFound() {
        Task task1 = new Task(1, "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateTask(task1)
        );
        Assertions.assertEquals("Невозможно обновить запись " + task1 + " - данный ID отсутствует в базе", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenUpdatedEpicNotFound() {
        Epic epic1 = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateEpic(epic1)
        );
        Assertions.assertEquals("Невозможно обновить запись " + epic1 + " - данный ID отсутствует в базе", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenUpdatedSubTaskNotFound() {
        SubTask subTask1 = new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateSubTask(subTask1)
        );
        Assertions.assertEquals("Невозможно обновить запись " + subTask1 + " - данный ID отсутствует в базе", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenDeletedTaskNotFound() {
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.deleteTask(1)
        );
        Assertions.assertEquals("Невозможно удалить запись №" + 1 + " - данный ID отсутствует в базе", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenDeletedEpicNotFound() {
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.deleteEpic(1)
        );
        Assertions.assertEquals("Невозможно удалить запись №" + 1 + " - данный ID отсутствует в базе", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenDeletedSubTaskNotFound() {
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.deleteSubTask(1)
        );
        Assertions.assertEquals("Невозможно удалить запись №" + 1 + " - данный ID отсутствует в базе", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenCreatedTaskHasIntersection() {
        Task task1 = new Task(1, "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        Task task2 = new Task(2, "Помыть второго ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        manager.createTask(task1);
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.createTask(task2)
        );
        Assertions.assertEquals("Обнаружено пересечение времени. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenUpdatedTaskHasIntersection() {
        Task task1 = new Task(1, "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        Task task2 = new Task(2, "Помыть второго ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 10, 0, 0, 0));
        manager.createTask(task1);
        manager.createTask(task2);
        task2.setDuration(240);
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateTask(task2)
        );
        Assertions.assertEquals("Обнаружено пересечение времени. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenCreatedSubTaskHasIntersection() {
        Epic epic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask1 = new SubTask(2, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        SubTask subTask2 = new SubTask(3, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        manager.createEpic(epic1);
        manager.createSubTask(subTask1);
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.createSubTask(subTask2)
        );
        Assertions.assertEquals("Обнаружено пересечение времени. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    public void ShouldThrownTaskManagerExceptionWhenUpdatedSubTaskHasIntersection() {
        Epic epic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask1 = new SubTask(2, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        SubTask subTask2 = new SubTask(3, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 9, 48, 0, 0), 1);
        manager.createEpic(epic1);
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask1);
        subTask2.setDuration(200);
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateSubTask(subTask2)
        );
        Assertions.assertEquals("Обнаружено пересечение времени. Запись в базу невозможна", ex.getMessage());
    }


}

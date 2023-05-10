import manager.TaskManager;
import manager.TaskManagerException;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    public T manager;

    @Test
    @DisplayName("Manager should add task in map")
    public void shouldCreateTask() {
        //Arrange
        Task task = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        final int id = task.getId();
        //Act
        manager.createTask(task);
        //Assert
        assertNotNull(manager.getTask(id), "Задача не сохранена в HashMap");
        assertEquals(task, manager.getTask(id), "Задачи не совпадают");
    }

    @Test
    @DisplayName("Manager should add subtask in map")
    public void shouldCreateSubTask() {
        //Arrange
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask = new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        //Act
        manager.createEpic(epic);
        final int id = subTask.getId();
        manager.createSubTask(subTask);
        //Assert
        assertNotNull(manager.getSubTask(id), "Задача не сохранена в HashMap");
        assertEquals(subTask, manager.getSubTask(id), "Задачи не совпадают");
    }

    @Test
    @DisplayName("Manager should add epic in map")
    public void shouldCreateEpic() {
        //Arrange
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        final int id = epic.getId();
        //Act
        manager.createEpic(epic);
        //Assert
        assertNotNull(manager.getEpic(id), "Задача не сохранена в HashMap");
        assertEquals(epic, manager.getEpic(id), "Задачи не совпадают");
    }

    @Test
    @DisplayName("Manager should delete task by id from map")
    public void shouldDeleteTask() {
        //Arrange
        Task task = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        final int id = task.getId();
        manager.createTask(task);
        assertNotNull(manager.getTask(id), "Задача не сохранена в HashMap");
        //Act
        manager.deleteTask(id);
        //Assert
        assertNull(manager.getTask(id), "Задача не удалена из HashMap");
    }

    @Test
    @DisplayName("Manager should delete subtask by id from map")
    public void shouldDeleteSubTask() {
        //Arrange
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask = new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        manager.createEpic(epic);
        final int id = subTask.getId();
        manager.createSubTask(subTask);
        assertNotNull(manager.getSubTask(id), "Задача не сохранена в HashMap");
        //Act
        manager.deleteSubTask(id);
        //Assert
        assertNull(manager.getSubTask(id), "Задача не удалена из HashMap");
    }

    @Test
    @DisplayName("Manager should delete epic by id from map")
    public void shouldDeleteEpic() {
        //Arrange
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        final int id = epic.getId();
        manager.createEpic(epic);
        assertNotNull(manager.getEpic(id), "Задача не сохранена в HashMap");
        //Act
        manager.deleteEpic(id);
        //Assert
        assertNull(manager.getEpic(id), "Задача не удалена из HashMap");
    }

    @Test
    @DisplayName("Manager should update task in map")
    public void shouldUpdateTask() {
        //Arrange
        Task task = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        final int id = task.getId();
        manager.createTask(task);
        assertNotNull(manager.getTask(id), "Задача не сохранена в HashMap");
        //Act
        task.setStatus(Status.DONE);
        manager.updateTask(task);
        //Assert
        assertEquals(task, manager.getTask(id), "Задачи не совпадают");
    }

    @Test
    @DisplayName("Manager should update subtask in map")
    public void shouldUpdateSubTask() {
        //Arrange
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask = new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        manager.createEpic(epic);
        final int id = subTask.getId();
        manager.createSubTask(subTask);
        assertNotNull(manager.getSubTask(id), "Задача не сохранена в HashMap");
        //Act
        subTask.setStatus(Status.DONE);
        manager.updateSubTask(subTask);
        //Assert
        assertEquals(subTask, manager.getSubTask(id), "Задачи не совпадают");
    }

    @Test
    @DisplayName("Manager should update epic in map")
    public void shouldUpdateEpic() {
        //Arrange
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        final int id = epic.getId();
        manager.createEpic(epic);
        assertNotNull(manager.getEpic(id), "Задача не сохранена в HashMap");
        //Act
        epic.setDescription("Убраться во всей квартире");
        manager.updateEpic(epic);
        //Assert
        assertEquals(epic, manager.getEpic(id), "Задачи не совпадают");
    }

    @Test
    @DisplayName("Manager should remove all tasks from map")
    public void shouldRemoveAllTasks() {
        //Arrange
        Task task1 = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        Task task2 = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 14, 0, 0, 0));
        manager.createTask(task1);
        manager.createTask(task2);
        assertEquals(2, manager.getTasks().size(), "Задачи не сохранены в HashMap");
        //Act
        manager.removeAllTasks();
        //Assert
        assertEquals(0, manager.getTasks().size(), "Задачи не удалены из HashMap");
    }

    @Test
    @DisplayName("Manager should remove all subtasks from map")
    public void shouldRemoveSubTasks() {
        //Arrange
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask1 = new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        SubTask subTask2 = new SubTask(manager.getNewId(), "Пропылесосить вторую комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 11, 48, 0, 0), 1);
        manager.createEpic(epic);
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask2);
        assertEquals(2, manager.getSubTasks().size(), "Задачи не сохранены в HashMap");
        //Act
        manager.removeAllSubTasks();
        //Assert
        assertEquals(0, manager.getSubTasks().size(), "Задачи не удалены из HashMap");
    }

    @Test
    @DisplayName("Manager should remove all epics and linked subtasks from map")
    public void shouldRemoveAllEpics() {
        //Arrange
        Epic epic1 = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        Epic epic2 = new Epic(manager.getNewId(), "Переехать в Ботсвану", "Переехать в Ботсвану");
        SubTask subTask1 = new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        manager.createSubTask(subTask1);
        assertEquals(2, manager.getEpics().size(), "Задачи не сохранены в HashMap");
        //Act
        manager.removeAllEpics();
        //Assert
        assertEquals(0, manager.getEpics().size(), "Эпики не удалены из HashMap");
        assertEquals(0, manager.getSubTasks().size(), "Связанные субтаски не удалены из HashMap");
    }

    @Test
    @DisplayName("Manager should return list of 2 Tasks")
    public void shouldGet2Tasks() {
        //Arrange
        Task task1 = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        Task task2 = new Task(manager.getNewId(), "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 14, 0, 0, 0));
        //Act
        manager.createTask(task1);
        manager.createTask(task2);
        //Assert
        assertArrayEquals(new Task[]{task1, task2}, manager.getTasks().toArray(), "Метод не вернул задачи");
    }

    @Test
    @DisplayName("Manager should return list of 0 Tasks")
    public void shouldGet0Tasks() {
        //Assert
        assertArrayEquals(new Task[]{}, manager.getTasks().toArray(), "Метод вернул задачи");
    }

    @Test
    @DisplayName("Manager should return list of 2 SubTasks")
    public void shouldGet2SubTasks() {
        //Arrange
        Epic epic = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask1 = new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        SubTask subTask2 = new SubTask(manager.getNewId(), "Пропылесосить вторую комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 11, 48, 0, 0), 1);
        //Act
        manager.createEpic(epic);
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask2);
        //Assert
        assertArrayEquals(new SubTask[]{subTask1, subTask2}, manager.getSubTasks().toArray(), "Задачи не сохранены в HashMap");
    }

    @Test
    @DisplayName("Manager should return list of 0 SubTasks")
    public void shouldGet0SubTasks() {
        //Assert
        assertArrayEquals(new SubTask[]{}, manager.getSubTasks().toArray(), "Метод вернул задачи");
    }

    @Test
    @DisplayName("Manager should return list of 2 Epics")
    public void shouldGet2Epics() {
        //Arrange
        Epic epic1 = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        Epic epic2 = new Epic(manager.getNewId(), "Переехать в Ботсвану", "Переехать в Ботсвану");
        //Act
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        //Assert
        assertArrayEquals(new Epic[]{epic1, epic2}, manager.getEpics().toArray(), "Метод не вернул задачи");
    }

    @Test
    @DisplayName("Manager should return list of 0 Epics")
    public void shouldGet0Epics() {
        //Assert
        assertArrayEquals(new Epic[]{}, manager.getEpics().toArray(), "Метод вернул задачи");
    }

    @Test
    @DisplayName("Manager should return list of 0 prioritized tasks")
    public void shouldGet0PrioritizedTasks() {
        //Assert
        assertArrayEquals(new Task[]{}, manager.getPrioritizedTasks().toArray(), "Метод вернул задачи");
    }

    @Test
    @DisplayName("Manager should return list of 2 prioritized tasks ordered by StartTime")
    public void shouldGet2PrioritizedTasksInOrder() {
        //Arrange
        Epic testEpic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        Task testTask1 = new Task(3, "Купить продукты", "Сходить в магазин", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 14, 30, 0, 0));
        SubTask testSubTask1 = new SubTask(2, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.DONE, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        //Act
        manager.createEpic(testEpic1);
        manager.createTask(testTask1);
        manager.createSubTask(testSubTask1);
        manager.updateTask(testTask1);
        manager.updateSubTask(testSubTask1);
        //Assert
        assertArrayEquals(new Task[]{testSubTask1, testTask1}, manager.getPrioritizedTasks().toArray(), "Метод не вернул задачи");
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when created task is Null")
    public void shouldThrownTaskManagerExceptionWhenCreatedTaskIsNull() {
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.createTask(null)
        );
        //Assert
        Assertions.assertEquals("Передан пустой объект. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when created epic is Null")
    public void shouldThrownTaskManagerExceptionWhenCreatedEpicIsNull() {
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.createEpic(null)
        );
        //Assert
        Assertions.assertEquals("Передан пустой объект. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when created subtask is Null")
    public void shouldThrownTaskManagerExceptionWhenCreatedSubTaskIsNull() {
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.createSubTask(null)
        );
        //Assert
        Assertions.assertEquals("Передан пустой объект. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when updated task is Null")
    public void shouldThrownTaskManagerExceptionWhenUpdatedTaskIsNull() {
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateTask(null)
        );
        //Assert
        Assertions.assertEquals("Передан пустой объект. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when updated epic is Null")
    public void shouldThrownTaskManagerExceptionWhenUpdatedEpicIsNull() {
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateEpic(null)
        );
        //Assert
        Assertions.assertEquals("Передан пустой объект. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when updated subtask is Null")
    public void shouldThrownTaskManagerExceptionWhenUpdatedSubTaskIsNull() {
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateSubTask(null)
        );
        //Assert
        Assertions.assertEquals("Передан пустой объект. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when updated task not found")
    public void shouldThrownTaskManagerExceptionWhenUpdatedTaskNotFound() {
        //Arrange
        Task task1 = new Task(1, "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateTask(task1)
        );
        //Assert
        Assertions.assertEquals("Невозможно обновить запись " + task1 + " - данный ID отсутствует в базе", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when updated epic not found")
    public void shouldThrownTaskManagerExceptionWhenUpdatedEpicNotFound() {
        Epic epic1 = new Epic(manager.getNewId(), "Убраться в комнате", "Пропылесосить комнату");
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateEpic(epic1)
        );
        Assertions.assertEquals("Невозможно обновить запись " + epic1 + " - данный ID отсутствует в базе", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when updated subtask not found")
    public void shouldThrownTaskManagerExceptionWhenUpdatedSubTaskNotFound() {
        //Arrange
        SubTask subTask1 = new SubTask(manager.getNewId(), "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateSubTask(subTask1)
        );
        //Assert
        Assertions.assertEquals("Невозможно обновить запись " + subTask1 + " - данный ID отсутствует в базе", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when deleted task not found")
    public void shouldThrownTaskManagerExceptionWhenDeletedTaskNotFound() {
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.deleteTask(1)
        );
        //Assert
        Assertions.assertEquals("Невозможно удалить запись №" + 1 + " - данный ID отсутствует в базе", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when deleted epic not found")
    public void shouldThrownTaskManagerExceptionWhenDeletedEpicNotFound() {
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.deleteEpic(1)
        );
        //Assert
        Assertions.assertEquals("Невозможно удалить запись №" + 1 + " - данный ID отсутствует в базе", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when deleted subtask not found")
    public void shouldThrownTaskManagerExceptionWhenDeletedSubTaskNotFound() {
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.deleteSubTask(1)
        );
        //Assert
        Assertions.assertEquals("Невозможно удалить запись №" + 1 + " - данный ID отсутствует в базе", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when created task has time intersection")
    public void shouldThrownTaskManagerExceptionWhenCreatedTaskHasIntersection() {
        //Arrange
        Task task1 = new Task(1, "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        Task task2 = new Task(2, "Помыть второго ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        manager.createTask(task1);
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.createTask(task2)
        );
        //Assert
        Assertions.assertEquals("Обнаружено пересечение времени. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when updated task has time intersection")
    public void shouldThrownTaskManagerExceptionWhenUpdatedTaskHasIntersection() {
        //Arrange
        Task task1 = new Task(1, "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 12, 0, 0, 0));
        Task task2 = new Task(2, "Помыть второго ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 10, 0, 0, 0));
        manager.createTask(task1);
        manager.createTask(task2);
        task2.setDuration(240);
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateTask(task2)
        );
        //Assert
        Assertions.assertEquals("Обнаружено пересечение времени. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when created subtask has time intersection")
    public void shouldThrownTaskManagerExceptionWhenCreatedSubTaskHasIntersection() {
        //Arrange
        Epic epic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask1 = new SubTask(2, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        SubTask subTask2 = new SubTask(3, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        manager.createEpic(epic1);
        manager.createSubTask(subTask1);
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.createSubTask(subTask2)
        );
        //Assert
        Assertions.assertEquals("Обнаружено пересечение времени. Запись в базу невозможна", ex.getMessage());
    }

    @Test
    @DisplayName("Manager should thrown TaskManagerException when updated subtask has time intersection")
    public void shouldThrownTaskManagerExceptionWhenUpdatedSubTaskHasIntersection() {
        //Arrange
        Epic epic1 = new Epic(1, "Убраться в комнате", "Пропылесосить комнату");
        SubTask subTask1 = new SubTask(2, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 10, 48, 0, 0), 1);
        SubTask subTask2 = new SubTask(3, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 2, 5, 9, 48, 0, 0), 1);
        manager.createEpic(epic1);
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask1);
        subTask2.setDuration(200);
        //Act
        TaskManagerException ex = Assertions.assertThrows(
                TaskManagerException.class,
                () -> manager.updateSubTask(subTask2)
        );
        //Assert
        Assertions.assertEquals("Обнаружено пересечение времени. Запись в базу невозможна", ex.getMessage());
    }


}

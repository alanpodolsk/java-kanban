import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import manager.FileBackedTaskManager;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTest {
    FileBackedTaskManager manager;
    HttpTaskServer server;
    HttpClient client;
    Gson gson = new Gson();

    @BeforeEach
    public void initManagerAndRunServer() {
        manager = FileBackedTaskManager.loadFromFile("testFileManagerForHTTP.txt");
        server = new HttpTaskServer(manager);
        try {
            server.initServer();
            server.startServer();
        } catch (IOException e) {
            System.out.println("Инициализация HttpTaskServer закончилась ошибкой");
        }
        client = HttpClient.newHttpClient();
    }


    @Test
    @DisplayName("Запрос должен вернуть 2 таски")
    public void shouldReturnAllTasks() {
        //Arrange
        List <Task> tasks = new ArrayList<>();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            tasks = gson.fromJson(response.body(), List.class);
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals(manager.getTasks().size(),tasks.size(),"Результат запроса не совпадает с состоянием менеджера");
    }

    @Test
    @DisplayName("Запрос должен вернуть 3 субтаски")
    public void shouldReturnAllSubTasks() {
        //Arrange
        List <SubTask> tasks = new ArrayList<>();
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        Type listType = new TypeToken<ArrayList<SubTask>>(){}.getType();
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            tasks = gson.fromJson(response.body(), listType);
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
            return;
        }
        //Assert
        assertEquals(manager.getSubTasks(),tasks,"Результат запроса не совпадает с состоянием менеджера");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен вернуть 2 эпика")
    public void shouldReturnAllEpics() {
        //Arrange
        List <Epic> tasks = new ArrayList<>();
        Type listType = new TypeToken<ArrayList<Epic>>(){}.getType();
        int code = 0;
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            tasks = gson.fromJson(response.body(), listType);
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса: " + e.getMessage());
        }
        //Assert
        assertArrayEquals(tasks.toArray(),manager.getEpics().toArray(),"Результат запроса не совпадает с состоянием менеджера");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен удалить эпики")
    public void shouldDeleteAllEpics() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        assertEquals(2,manager.getEpics().size(),"Некорректное формирование менеджера из тест-файла");
        int code = 0;
        String responseBody;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса: " + e.getMessage());
            return;
        }
        //Assert
        assertEquals(0,manager.getEpics().size(),"Результат запроса не совпадает с состоянием менеджера");
        assertEquals("Удалены все эпики и связанные подзадачи из памяти менеджера",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен удалить задачи")
    public void shouldDeleteAllTasks() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        assertEquals(2,manager.getTasks().size(),"Некорректное формирование менеджера из тест-файла");
        int code = 0;
        String responseBody = null;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals(0,manager.getTasks().size(),"Результат запроса не совпадает с состоянием менеджера");
        assertEquals("Удалены все задачи из памяти менеджера",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }
    @Test
    @DisplayName("Запрос должен удалить подзадачи")
    public void shouldDeleteAllSubTasks() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        assertEquals(3,manager.getSubTasks().size(),"Некорректное формирование менеджера из тест-файла");
        int code = 0;
        String responseBody = null;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса: " + e.getMessage());
        }
        //Assert
        assertEquals(0,manager.getSubTasks().size(),"Результат запроса не совпадает с состоянием менеджера");
        assertEquals("Удалены все подзадачи из памяти менеджера",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен вернуть задачу с id=2")
    public void shouldReturnTaskId2() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/task/?id=2");
        Task returnedTask = null;
        Task expectedTask = manager.getTask(2);
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            returnedTask = gson.fromJson(response.body(),Task.class);
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals(expectedTask,returnedTask,"Возвращенная задача не соответствует аналогичной в менеджере");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен вернуть эпик с id=4")
    public void shouldReturnEpicId4() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/epic/?id=4");
        Epic returnedTask;
        Epic expectedTask = manager.getEpic(4);
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            returnedTask = gson.fromJson(response.body(),Epic.class);
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
            return;
        }
        //Assert
        assertEquals(expectedTask,returnedTask,"Возвращенная задача не соответствует аналогичной в менеджере");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }
    @Test
    @DisplayName("Запрос должен вернуть субтаск с id=6")
    public void shouldReturnSubTaskId6() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/subtask/?id=6");
        SubTask returnedTask;
        SubTask expectedTask = manager.getSubTask(6);
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            returnedTask = gson.fromJson(response.body(),SubTask.class);
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
            return;
        }
        //Assert
        assertEquals(expectedTask,returnedTask,"Возвращенная задача не соответствует аналогичной в менеджере");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }
    @Test
    @DisplayName("Запрос должен вернуть список субтасков с эпика id=4")
    public void shouldReturnSubTasksFromEpicId4() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/subtask/epic/?id=4");
        List<SubTask> returnedTask;
        List<SubTask> expectedSubTasks = new ArrayList<>();
        Type listType = new TypeToken<ArrayList<SubTask>>(){}.getType();
        for (Integer subTaskId: manager.getEpic(4).getSubTasksList()){
            expectedSubTasks.add(manager.getSubTask(subTaskId));
        }HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            returnedTask = gson.fromJson(response.body(),listType);
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
            return;
        }
        //Assert
        assertEquals(expectedSubTasks,returnedTask,"Возвращенный список подзадач не соответствует аналогичному в менеджере");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен вернуть список задач по приоритету")
    public void shouldReturnPrioritizedTasks() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/");
        TreeSet<Task> returnedTask;
        List<Task> expectedTasks = new ArrayList<>();
        Type listType = new TypeToken<TreeSet<Task>>(){}.getType();
      //  expectedTasks = manager.getPrioritizedTasks();
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            returnedTask = gson.fromJson(response.body(),listType);
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
            return;
        }
        //Assert
        assertArrayEquals(expectedTasks.toArray(),returnedTask.toArray(),"Возвращенный список подзадач не соответствует аналогичному в менеджере");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен вернуть историю задач")
    public void shouldReturnHistory() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/history/");
        List<Task> returnedTask;
        List<Task> expectedTasks = new ArrayList<>();
        Type listType = new TypeToken<ArrayList<Task>>(){}.getType();
        expectedTasks = manager.getHistory();
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            returnedTask = gson.fromJson(response.body(),listType);
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
            return;
        }
        //Assert
        assertEquals(expectedTasks.toString(),returnedTask.toString(),"Возвращенный список подзадач не соответствует аналогичному в менеджере");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен удалить задачу N2")
    public void shouldDeleteTaskId2() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/task/?id=2");
        assertNotNull(manager.getTask(2),"Предлагаемой к удалению задачи нет в менеджере");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertNull(manager.getTask(2),"Результат запроса не совпадает с состоянием менеджера");
        assertEquals("Задача с id = 2 удалена либо не найдена в менеджере",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }
    @Test
    @DisplayName("Запрос должен удалить эпик N4")
    public void shouldDeleteEpicId4() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/epic/?id=4");
        assertNotNull(manager.getEpic(4),"Предлагаемой к удалению задачи нет в менеджере");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertNull(manager.getEpic(4),"Результат запроса не совпадает с состоянием менеджера");
        assertEquals("Задача с id = 4 удалена либо не найдена в менеджере",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен удалить субтаск N6")
    public void shouldDeleteSubTaskId6() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/subtask/?id=6");
        assertNotNull(manager.getSubTask(6),"Предлагаемой к удалению задачи нет в менеджере");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertNull(manager.getSubTask(6),"Результат запроса не совпадает с состоянием менеджера");
        assertEquals("Задача с id = 6 удалена либо не найдена в менеджере",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }
    @Test
    @DisplayName("Запрос должен создать задачу N8")
    public void shouldAddTask8() {
        //Arrange
        Task task = new Task(8, "Помыть ежа", "Помыть ежа и не уколоться", Status.NEW, 60, LocalDateTime.of(2023, 3, 3, 20, 0, 0, 0));
        URI url = URI.create("http://localhost:8080/tasks/task/");
        assertNull(manager.getTask(8),"Предлагаемая к добавлению задача уже находится в менеджере");
        String json = gson.toJson(task);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertNotNull(manager.getTask(8),"Задача не добавлена в менеджер или добавлена некорректно");
        assertEquals("Задача добавлена",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(201, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен создать эпик N8")
    public void shouldAddEpic8() {
        //Arrange
        Epic epic = new Epic(8, "Убраться в комнате", "Пропылесосить комнату");
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        assertNull(manager.getEpic(8),"Предлагаемая к добавлению задача уже находится в менеджере");
        String json = gson.toJson(epic);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertNotNull(manager.getEpic(8),"Задача не добавлена в менеджер");
        assertEquals("Задача добавлена",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(201, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен создать подзадачу N8")
    public void shouldAddSubTask8() {
        //Arrange
        SubTask subTask = new SubTask(8, "Пропылесосить комнату", "Включить пылесос и пропылесосить комнату", Status.NEW, 25, LocalDateTime.of(2023, 10, 5, 9, 48, 0, 0), 3);
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        assertNull(manager.getSubTask(8),"Предлагаемая к добавлению задача уже находится в менеджере");
        String json = gson.toJson(subTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertNotNull(manager.getSubTask(8),"Задача не добавлена в менеджер");
        assertEquals("Задача добавлена",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(201, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен сообщить о некорректном эндпоинте")
    public void shouldReturnIncorrectEndpointError() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/supertasks/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals("Такого эндпоинта не существует",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(404, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос на добавление задачи должен сообщить о некорректном JSON")
    public void shouldReturnIncorrectJsonErrorWherePostTask() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/task/");
        String json = "Бангладеш172";
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals("Получен некорректный JSON",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(404, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос на добавление эпика должен сообщить о некорректном JSON")
    public void shouldReturnIncorrectJsonErrorWherePostEpic() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        String json = "Бангладеш172";
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals("Получен некорректный JSON",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(404, code, "Код ответа не соответствует ожидаемому");
    }
    @Test
    @DisplayName("Запрос на добавление подзадачи должен сообщить о некорректном JSON")
    public void shouldReturnIncorrectJsonErrorWherePostSubTask() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        String json = "Бангладеш172";
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals("Получен некорректный JSON",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(404, code, "Код ответа не соответствует ожидаемому");
    }
    @Test
    @DisplayName("Запрос на удаление должен сообщить о некорректном идентификаторе")
    public void shouldReturnIncorrectIdWhereDeleteTask() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/task/?id=abc");
        assertNotNull(manager.getTask(2),"Предлагаемой к удалению задачи нет в менеджере");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals("Некорректный идентификатор задачи",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(400, code, "Код ответа не соответствует ожидаемому");
    }
    @Test
    @DisplayName("Запрос на удаление эпика должен сообщить о некорректном идентификаторе")
    public void shouldReturnIncorrectIdWhereDeleteEpic() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/epic/?id=abc");
        assertNotNull(manager.getTask(2),"Предлагаемой к удалению задачи нет в менеджере");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals("Некорректный идентификатор задачи",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(400, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос на удаление подзадачи должен сообщить о некорректном идентификаторе")
    public void shouldReturnIncorrectIdWhereDeleteSubTask() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/subtask/?id=abc");
        assertNotNull(manager.getTask(2),"Предлагаемой к удалению задачи нет в менеджере");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals("Некорректный идентификатор задачи",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(400, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос на получение должен сообщить о некорректном идентификаторе")
    public void shouldReturnIncorrectIdWhereGetTask() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/task/?id=abc");
        assertNotNull(manager.getTask(2),"Предлагаемой к удалению задачи нет в менеджере");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals("Некорректный идентификатор задачи",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(400, code, "Код ответа не соответствует ожидаемому");
    }
    @Test
    @DisplayName("Запрос на получение эпика должен сообщить о некорректном идентификаторе")
    public void shouldReturnIncorrectIdWhereGetEpic() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/epic/?id=abc");
        assertNotNull(manager.getTask(2),"Предлагаемой к удалению задачи нет в менеджере");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals("Некорректный идентификатор задачи",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(400, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос на получение подзадачи должен сообщить о некорректном идентификаторе")
    public void shouldReturnIncorrectIdWhereGetSubTask() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/subtask/?id=abc");
        assertNotNull(manager.getTask(2),"Предлагаемой к удалению задачи нет в менеджере");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        String responseBody = null;
        int code = 0;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals("Некорректный идентификатор задачи",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(400, code, "Код ответа не соответствует ожидаемому");
    }

    @AfterEach
    public void stopManager() {
        server.stopServer();
    }
}
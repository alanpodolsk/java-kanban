import com.google.gson.Gson;
import manager.FileBackedTaskManager;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void ShouldReturnAllTasks() {
        //Arrange
        List <Task> tasks = new ArrayList<>();
        URI url = URI.create("http://localhost:8080/tasks/task");
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
        assertEquals(tasks.size(),manager.getTasks().size(),"Результат запроса не совпадает с состоянием менеджера");
    }

    @Test
    @DisplayName("Запрос должен вернуть 3 субтаски")
    public void ShouldReturnAllSubTasks() {
        //Arrange
        List <Task> tasks = new ArrayList<>();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
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
        assertEquals(tasks.size(),manager.getSubTasks().size(),"Результат запроса не совпадает с состоянием менеджера");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен вернуть 2 эпика")
    public void ShouldReturnAllEpics() {
        //Arrange
        List <Task> tasks = new ArrayList<>();
        int code = 0;
        URI url = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            tasks = gson.fromJson(response.body(), List.class);
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals(tasks.size(),manager.getEpics().size(),"Результат запроса не совпадает с состоянием менеджера");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен удалить эпики")
    public void ShouldDeleteAllEpics() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/epic");
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
            System.out.println("Ошибка отправки запроса");
            return;
        }
        //Assert
        assertEquals(0,manager.getEpics().size(),"Результат запроса не совпадает с состоянием менеджера");
        assertEquals("Удалены все эпики и связанные подзадачи из памяти менеджера",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Запрос должен удалить задачи")
    public void ShouldDeleteAllTasks() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        assertEquals(2,manager.getTasks().size(),"Некорректное формирование менеджера из тест-файла");
        int code = 0;
        String responseBody;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
            return;
        }
        //Assert
        assertEquals(0,manager.getTasks().size(),"Результат запроса не совпадает с состоянием менеджера");
        assertEquals("Удалены все задачи из памяти менеджера",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }
    @Test
    @DisplayName("Запрос должен удалить подзадачи")
    public void ShouldDeleteAllSubTasks() {
        //Arrange
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        assertEquals(3,manager.getSubTasks().size(),"Некорректное формирование менеджера из тест-файла");
        int code = 0;
        String responseBody;
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            code = response.statusCode();
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
            return;
        }
        //Assert
        assertEquals(0,manager.getSubTasks().size(),"Результат запроса не совпадает с состоянием менеджера");
        assertEquals("Удалены все подзадачи из памяти менеджера",responseBody,"Ответ сервера не соответствует ожидаемому");
        assertEquals(200, code, "Код ответа не соответствует ожидаемому");
    }

    @AfterEach
    public void stopManager() {
        server.stopServer();
    }
}
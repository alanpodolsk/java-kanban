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
        //Act
        try{
            final HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            tasks = gson.fromJson(response.body(), List.class);
        } catch (IOException | InterruptedException e){
            System.out.println("Ошибка отправки запроса");
        }
        //Assert
        assertEquals(tasks.size(),manager.getTasks().size(),"Результат запроса не совпадает с состоянием менеджера");
    }






    @AfterEach
    public void stopManager() {
        server.stopServer();
    }
}
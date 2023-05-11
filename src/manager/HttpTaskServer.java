package manager;

import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler(new FileBackedTaskManager()));
        httpServer.start(); // запускаем сервер

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }
}






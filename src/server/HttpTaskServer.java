package server;

import com.sun.net.httpserver.HttpServer;
import manager.FileBackedTaskManager;
import manager.TaskManager;

import java.io.*;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    final static int PORT = 8080;
    HttpServer httpServer;
    TaskManager manager;
    public HttpTaskServer (TaskManager manager) {this.manager = manager;}
    public void initServer() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler(manager));
    }
    public void startServer(){
            httpServer.start(); // запускаем сервер
            System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        };
    public void stopServer(){
        httpServer.stop(0);
    }
}






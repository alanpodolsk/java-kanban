package server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.FileBackedTaskManager;
import manager.TaskManager;
import manager.TaskManagerException;
import model.Epic;
import model.SubTask;
import model.Task;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class TasksHandler implements HttpHandler {
    TaskManager manager;
    String response;
    Gson gson = new Gson();

    TasksHandler(TaskManager manager){
        this.manager = manager;
    }

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null){query = "";}
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod(),query);
        switch(endpoint){
            case GET_ALL_TASKS:
                handleGetAllTasks(exchange);
                break;
            case GET_ALL_EPICS:
                handleGetAllEpics(exchange);
                break;
            case GET_ALL_SUBTASKS:
                handleGetAllSubTasks(exchange);
                break;
            case GET_TASK_BY_ID:
                handleGetTaskById(exchange);
                break;
            case GET_EPIC_BY_ID:
                handleGetEpicById(exchange);
                break;
            case GET_SUBTASK_BY_ID:
                handleGetSubTaskById(exchange);
                break;
            case GET_EPIC_SUBTASKS:
                handleGetEpicSubTasksById(exchange);
            case GET_HISTORY:
                handleGetHistory(exchange);
                break;
            case GET_PRIORITIZED_TASKS:
                handleGetPrioritizedTasks(exchange);
                break;
            case POST_EPIC:
                handlePostEpic(exchange);
            case POST_TASK:
                handlePostTask(exchange);
            case POST_SUBTASK:
                handlePostSubTask(exchange);
            case DELETE_EPIC_BY_ID:
                handleDeleteEpicById(exchange);
                break;
            case DELETE_TASK_BY_ID:
                handleDeleteTaskById(exchange);
                break;
            case DELETE_SUBTASK_BY_ID:
                handleDeleteSubTaskById(exchange);
                break;
            case DELETE_ALL_EPICS:
                handleDeleteAllEpics(exchange);
                break;
            case DELETE_ALL_TASKS:
                handleDeleteAllTasks(exchange);
            case DELETE_ALL_SUBTASKS:
                handleDeleteAllSubTasks(exchange);
            case UNKNOWN:
                writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }

    }
    private Endpoint getEndpoint(String requestPath, String requestMethod, String requestQuery) {
        if (requestMethod.equals("GET") && requestPath.equals("/tasks/task/") && requestQuery.isEmpty()) {
            return Endpoint.GET_ALL_TASKS;
        } else if (requestMethod.equals("GET") && requestPath.equals("/tasks/epic/") && requestQuery.isEmpty()) {
            return Endpoint.GET_ALL_EPICS;
        } else if (requestMethod.equals("GET") && requestPath.equals("/tasks/subtask/") && requestQuery.isEmpty()) {
            return Endpoint.GET_ALL_SUBTASKS;
        } else if (requestMethod.equals("GET") && requestPath.equals("/tasks/task/") && requestQuery.startsWith("id="))  {
            return Endpoint.GET_TASK_BY_ID;
        } else if (requestMethod.equals("GET") && requestPath.equals("/tasks/subtask/") && requestQuery.startsWith("id=")) {
            return Endpoint.GET_SUBTASK_BY_ID;
        } else if (requestMethod.equals("GET") && requestPath.equals("/tasks/epic/") && requestQuery.startsWith("id=")) {
            return Endpoint.GET_EPIC_BY_ID;
        } else if (requestMethod.equals("GET") && requestPath.equals("/tasks/subtask/epic/") && requestQuery.startsWith("id=")) {
            return Endpoint.GET_EPIC_SUBTASKS;
        } else if (requestMethod.equals("GET") && requestPath.equals("/tasks/") && requestQuery.isEmpty()) {
            return Endpoint.GET_PRIORITIZED_TASKS;
        } else if (requestMethod.equals("GET") && requestPath.equals("/tasks/history/") && requestQuery.isEmpty()) {
            return Endpoint.GET_HISTORY;
        } else if (requestMethod.equals("POST") && requestPath.equals("/tasks/task/") && requestQuery.isEmpty()) {
            return Endpoint.POST_TASK;
        } else if (requestMethod.equals("POST") && requestPath.equals("/tasks/subtask/") && requestQuery.isEmpty()) {
            return Endpoint.POST_SUBTASK;
        } else if (requestMethod.equals("POST") && requestPath.equals("/tasks/epic/") && requestQuery.isEmpty()) {
            return Endpoint.POST_EPIC;
        } else if (requestMethod.equals("DELETE") && requestPath.equals("/tasks/subtask/") && requestQuery.isEmpty()) {
            return Endpoint.DELETE_ALL_SUBTASKS;
        } else if (requestMethod.equals("DELETE") && requestPath.equals("/tasks/epic/") && requestQuery.isEmpty()) {
            return Endpoint.DELETE_ALL_EPICS;
        } else if (requestMethod.equals("DELETE") && requestPath.equals("/tasks/task/") && requestQuery.isEmpty()) {
            return Endpoint.DELETE_ALL_TASKS;
        } else if (requestMethod.equals("DELETE") && requestPath.equals("/tasks/task/") && requestQuery.startsWith("id=")) {
            return Endpoint.DELETE_TASK_BY_ID;
        } else if (requestMethod.equals("DELETE") && requestPath.equals("/tasks/subtask/") && requestQuery.startsWith("id=")) {
            return Endpoint.DELETE_SUBTASK_BY_ID;
        }   else if (requestMethod.equals("DELETE") && requestPath.equals("/tasks/epic/") && requestQuery.startsWith("id=")) {
                return Endpoint.DELETE_EPIC_BY_ID;
        } else {
            return Endpoint.UNKNOWN;
        }
    }
    private void handleGetAllTasks(HttpExchange exchange) throws IOException {
        response = gson.toJson(manager.getTasks());
        writeResponse(exchange,response,200);
    }
    private void handleGetAllEpics(HttpExchange exchange) throws IOException {
        response = gson.toJson(manager.getEpics());
        writeResponse(exchange,response,200);
    }
    private void handleGetAllSubTasks(HttpExchange exchange) throws IOException {
        response = gson.toJson(manager.getSubTasks());
        writeResponse(exchange,response,200);
    }
    private void handleDeleteAllTasks(HttpExchange exchange) throws IOException {
        manager.removeAllTasks();
        response = "Удалены все задачи из памяти менеджера";
        writeResponse(exchange,response,200);
    }
    private void handleDeleteAllEpics(HttpExchange exchange) throws IOException {
        manager.removeAllEpics();
        response = "Удалены все эпики и связанные подзадачи из памяти менеджера";
        writeResponse(exchange,response,200);
    }
    private void handleDeleteAllSubTasks(HttpExchange exchange) throws IOException {
        manager.removeAllSubTasks();
        response = "Удалены все подзадачи из памяти менеджера";
        writeResponse(exchange,response,200);
    }
    private void handleGetPrioritizedTasks(HttpExchange exchange) throws IOException {
        response = gson.toJson(manager.getPrioritizedTasks().toArray());
        writeResponse(exchange,response,200);
    }
    private void handleGetHistory(HttpExchange exchange) throws IOException {
        response = gson.toJson(manager.getHistory());
        writeResponse(exchange,response,200);
    }
    private void handleGetTaskById(HttpExchange exchange) throws IOException {
        Integer id = getIdFromRequest(exchange.getRequestURI().getQuery());
        if (id != null){
            Task task = manager.getTask(id);
            if (task != null){
                response = gson.toJson(manager.getTask(id));
                writeResponse(exchange,response,200);
            } else {
                response = "Задача с id = "+id+" не обнаружена";
                writeResponse(exchange,response,404);
            }
        }   else {
            response = "Некорректный идентификатор задачи";
            writeResponse(exchange,response,400);
        }
    }
    private void handleGetEpicById(HttpExchange exchange) throws IOException {
        Integer id = getIdFromRequest(exchange.getRequestURI().getQuery());
        if (id != null){
            Epic epic = manager.getEpic(id);
            if (epic != null){
                response = gson.toJson(manager.getEpic(id));
                writeResponse(exchange,response,200);
            } else {
                response = "Задача с id = "+id+" не обнаружена";
                writeResponse(exchange,response,404);
            }
        }   else {
            response = "Некорректный идентификатор задачи";
            writeResponse(exchange,response,400);
        }
    }
    private void handleGetSubTaskById(HttpExchange exchange) throws IOException {
        Integer id = getIdFromRequest(exchange.getRequestURI().getQuery());
        if (id != null){
            SubTask subTask = manager.getSubTask(id);
            if (subTask != null){
                response = gson.toJson(manager.getSubTask(id));
                writeResponse(exchange,response,200);
            } else {
                response = "Задача с id = "+id+" не обнаружена";
                writeResponse(exchange,response,404);
            }
        }   else {
            response = "Некорректный идентификатор задачи";
            writeResponse(exchange,response,400);
        }
    }
    private void handleGetEpicSubTasksById(HttpExchange exchange) throws IOException {
        Integer id = getIdFromRequest(exchange.getRequestURI().getQuery());
        if (id != null){
            Epic epic = manager.getEpic(id);
            if (epic != null){
                List<SubTask> subTasks = new ArrayList<>();
                for (Integer subTaskId: manager.getEpic(id).getSubTasksList()){
                    subTasks.add(manager.getSubTask(subTaskId));
                }
                response = gson.toJson(subTasks);
                writeResponse(exchange,response,200);
            } else {
                response = "Эпик с id = "+id+" не обнаружен";
                writeResponse(exchange,response,404);
            }
        }   else {
            response = "Некорректный идентификатор задачи";
            writeResponse(exchange,response,400);
        }
    }
    private void handleDeleteTaskById(HttpExchange exchange) throws IOException {
        Integer id = getIdFromRequest(exchange.getRequestURI().getQuery());
        if (id != null){
            manager.deleteTask(id);
            response = "Задача с id = "+id+" удалена либо не найдена в менеджере";
            writeResponse(exchange,response,200);
        }   else {
            response = "Некорректный идентификатор задачи";
            writeResponse(exchange,response,400);
        }
    }
    private void handleDeleteEpicById(HttpExchange exchange) throws IOException {
        Integer id = getIdFromRequest(exchange.getRequestURI().getQuery());
        if (id != null){
            manager.deleteEpic(id);
            response = "Задача с id = "+id+" удалена либо не найдена в менеджере";
            writeResponse(exchange,response,200);
        }   else {
            response = "Некорректный идентификатор задачи";
            writeResponse(exchange,response,400);
        }
    }
    private void handleDeleteSubTaskById (HttpExchange exchange) throws IOException {
        Integer id = getIdFromRequest(exchange.getRequestURI().getQuery());
        if (id != null){
            manager.deleteSubTask(id);
            response = "Задача с id = "+id+" удалена либо не найдена в менеджере";
            writeResponse(exchange,response,200);
        }   else {
            response = "Некорректный идентификатор задачи";
            writeResponse(exchange,response,400);
        }
    }

    private void handlePostTask(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        try {
            Task task = gson.fromJson(body, Task.class);
            if (task.getName() == null || task.getDescription() == null ||
                    task.getStatus() == null || task.getStartTime() == null || task.getDuration() == null) {
                writeResponse(exchange, "Поля задачи не могут быть пустыми", 400);
                return;
            }
            try {
                manager.createTask(task);
                writeResponse(exchange, "Задача добавлена", 201);
            } catch (TaskManagerException e) {
                response = "Во время записи задачи в память менеджера произошла ошибка: " + e.getMessage();
                writeResponse(exchange, response, 500);
            }
        } catch (JsonSyntaxException e) {
            writeResponse(exchange, "Получен некорректный JSON", 404);
        }
    }
    private void handlePostEpic(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        try {
            Epic epic = gson.fromJson(body, Epic.class);
            if (epic.getName() == null || epic.getDescription() == null) {
                writeResponse(exchange, "Поля задачи не могут быть пустыми", 400);
                return;
            }
            epic.setId(manager.getNewId());
            try {
                manager.createEpic(epic);
                writeResponse(exchange, "Задача добавлена", 201);
            } catch (TaskManagerException e) {
                response = "Во время записи задачи в память менеджера произошла ошибка: " + e.getMessage();
                writeResponse(exchange, response, 500);
            }
        } catch (JsonSyntaxException e) {
            writeResponse(exchange, "Получен некорректный JSON", 404);
        }
    }

    private void handlePostSubTask(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        try {
            SubTask subTask = gson.fromJson(body, SubTask.class);
            if (subTask.getName() == null || subTask.getDescription() == null ||
                    subTask.getStatus() == null || subTask.getStartTime() == null || subTask.getDuration() == null ||
                    subTask.getEpicId() == null) {
                writeResponse(exchange, "Поля задачи не могут быть пустыми", 400);
                return;
            }
            subTask.setId(manager.getNewId());
            try {
                manager.createSubTask(subTask);
                writeResponse(exchange, "Задача добавлена", 201);
            } catch (TaskManagerException e) {
                response = "Во время записи задачи в память менеджера произошла ошибка: " + e.getMessage();
                writeResponse(exchange, response, 500);
            }
        } catch (JsonSyntaxException e) {
            writeResponse(exchange, "Получен некорректный JSON", 404);
        }
    }
    private void writeResponse(HttpExchange exchange,
                               String responseString,
                               int responseCode) throws IOException {
        if (responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }
    private Integer getIdFromRequest(String requestPath){
        try{
            return Integer.parseInt(requestPath.replace("id=",""));
        } catch (Exception e){
           return null;
        }
    }


}
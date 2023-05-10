package manager;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.io.IOException;


class TasksHandler implements HttpHandler {
    FileBackedTaskManager manager;
    String response;
    Gson gson = new Gson();

    TasksHandler(FileBackedTaskManager manager){
        this.manager = manager;
    }

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());
        String body = exchange.getRequestBody().toString();
        String response;
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
            case GET_EPIC_BY_ID:
            case GET_SUBTASK_BY_ID:
            case GET_EPIC_SUBTASKS:
            case GET_HISTORY:
            case GET_PRIORITIZED_TASKS:
            case POST_EPIC:
            case POST_TASK:
            case POST_SUBTASK:
            case DELETE_EPIC_BY_ID:
            case DELETE_TASK_BY_ID:
            case DELETE_SUBTASK_BY_ID:
            case DELETE_ALL_EPICS:
            case DELETE_ALL_TASKS:
            case DELETE_ALL_SUBTASKS:
            case UNKNOWN:
        }

    }
    private Endpoint getEndpoint(String requestPath, String requestMethod) {
        if (requestMethod.equals("GET") && requestPath.equals("/tasks/task")) {
            return Endpoint.GET_ALL_TASKS;
        } else if (requestMethod.equals("GET") && requestPath.equals("/tasks/epic")) {
            return Endpoint.GET_ALL_EPICS;
        } else if (requestMethod.equals("GET") && requestPath.equals("/tasks/subtask")) {
            return Endpoint.GET_ALL_SUBTASKS;
        } else if (requestMethod.equals("GET") && requestPath.equals("/tasks/epic")) {
            return Endpoint.GET_ALL_EPICS;
        } else if (requestMethod.equals("GET") && requestPath.startsWith("/tasks/task?")) {
            return Endpoint.GET_TASK_BY_ID;
        } else if (requestMethod.equals("GET") && requestPath.startsWith("/tasks/task?")) {
            return Endpoint.GET_TASK_BY_ID;
        } else if (requestMethod.equals("GET") && requestPath.startsWith("/tasks/subtask?")) {
            return Endpoint.GET_SUBTASK_BY_ID;
        } else if (requestMethod.equals("GET") && requestPath.startsWith("/tasks/epic?")) {
            return Endpoint.GET_EPIC_BY_ID;
        } else if (requestMethod.equals("GET") && requestPath.startsWith("/tasks/task?")) {
            return Endpoint.GET_TASK_BY_ID;
        } else if (requestMethod.equals("GET") && requestPath.startsWith("/tasks/subtask/epic?")) {
            return Endpoint.GET_EPIC_SUBTASKS;
        } else if (requestMethod.equals("GET") && requestPath.startsWith("/tasks/")) {
            return Endpoint.GET_PRIORITIZED_TASKS;
        } else if (requestMethod.equals("GET") && requestPath.startsWith("/tasks/history")) {
            return Endpoint.GET_HISTORY;
        } else if (requestMethod.equals("POST") && requestPath.startsWith("/tasks/task")) {
            return Endpoint.POST_TASK;
        } else if (requestMethod.equals("POST") && requestPath.startsWith("/tasks/subtask")) {
            return Endpoint.POST_SUBTASK;
        } else if (requestMethod.equals("POST") && requestPath.startsWith("/tasks/epic")) {
            return Endpoint.POST_EPIC;
        } else if (requestMethod.equals("DELETE") && requestPath.startsWith("/tasks/subtask")) {
            return Endpoint.DELETE_ALL_SUBTASKS;
        } else if (requestMethod.equals("DELETE") && requestPath.startsWith("/tasks/epic")) {
            return Endpoint.DELETE_ALL_EPICS;
        } else if (requestMethod.equals("DELETE") && requestPath.startsWith("/tasks/task")) {
            return Endpoint.DELETE_ALL_TASKS;
        } else if (requestMethod.equals("DELETE") && requestPath.startsWith("/tasks/task?")) {
            return Endpoint.DELETE_TASK_BY_ID;
        } else if (requestMethod.equals("DELETE") && requestPath.startsWith("/tasks/task?")) {
            return Endpoint.DELETE_TASK_BY_ID;
        } else if (requestMethod.equals("DELETE") && requestPath.startsWith("/tasks/subtask?")) {
            return Endpoint.DELETE_SUBTASK_BY_ID;
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


}
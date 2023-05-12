package server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVServerClient {
    HttpClient client = HttpClient.newHttpClient();

    public String getToken() {
        String token = null;
        URI url = URI.create("http://localhost:8078/register");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    token = response.body();
                }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка отправки запроса");
        }
        return token;
    }

    public void put(String key, String data, String token){
        URI url = URI.create("http://localhost:8078/save/"+key+"/?API_TOKEN="+token);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(data);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка отправки запроса");
        }
    }
    public String load(String key, String token){
        String respBody = null;
        URI url = URI.create("http://localhost:8078/load/"+key+"/?API_TOKEN="+token);
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            respBody = response.body();
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка отправки запроса");
        }
        return respBody;
    }
}
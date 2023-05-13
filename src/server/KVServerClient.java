package server;

import manager.KVServerClientException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVServerClient {
    HttpClient client;
    String URL;
    String token;

    public KVServerClient(String URL){
        this.client = HttpClient.newHttpClient();
        this.URL = URL;
        this.token = getToken();
    }

    private String getToken() {
        String token = null;
        URI url = URI.create(URL+"/register");
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

    public void put(String key, String data){
        URI url = URI.create(URL+"/save/"+key+"/?API_TOKEN="+token);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(data);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200){
                throw new KVServerClientException ("Вернулся код обработки = "+response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка отправки запроса");
        }
    }
    public String load(String key){
        String respBody = null;
        URI url = URI.create(URL+"/load/"+key+"/?API_TOKEN="+token);
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                respBody = response.body();
            } else {
                throw new KVServerClientException ("Вернулся код обработки = "+response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка отправки запроса");
        }
        return respBody;
    }
}
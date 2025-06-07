package com.kiri.hobby_tracker.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleService {

    @Value("${google.ai.api.key}")
    private String apiKey;
    private String model = "/gemini-1.5-flash";

    public String GoogleGenerativeAIAPIReq(String query) {
        String url = "https://generativelanguage.googleapis.com/v1/models" + model + ":generateContent?key=" + apiKey;
        System.out.println("Google AI API URL: " + url);
        String prompt = query;
        String jsonRequestBody = """
        {
            "contents": [{
                "parts": [{
                    "text": "%s"
                }]
            }]
        }
        """.formatted(escapeJson(prompt));

        System.out.println(prompt);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("RESPONSE FROM GOOGLE AI API: " + response.body());
        } catch (IOException e) {
            System.out.println("Error in sending request to Google AI API: " + e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Request to Google AI API was interrupted: " + e);
            e.printStackTrace();
        }

        System.out.println(
                "Response status: " + response.statusCode());
        System.out.println(
                "Response body: " + response.body());
        return response.body();
    }

    public static String escapeJson(String str) {
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

}

package com.uoj.equipment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${app.mail.from}")
    private String fromEmail;

    @Value("${app.mail.from-name:ERMS}")
    private String fromName;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void sendPlainTextEmail(String to, String subject, String body) {
        // DIAGNOSTIC — print key prefix and sender so we can verify config is loaded
        String keyPreview = (brevoApiKey == null || brevoApiKey.isBlank())
                ? "NULL/EMPTY ← PROBLEM!"
                : brevoApiKey.substring(0, Math.min(12, brevoApiKey.length())) + "...";
        System.out.println("[EmailService] BREVO KEY prefix: " + keyPreview);
        System.out.println("[EmailService] FROM: " + fromEmail);
        System.out.println("[EmailService] TO:   " + to);

        try {
            String htmlBody = "<pre style=\"font-family:Arial,sans-serif;font-size:14px;\">"
                    + escapeHtml(body) + "</pre>";

            String json = "{"
                    + "\"sender\":{\"name\":\"" + escapeJson(fromName) + "\",\"email\":\"" + escapeJson(fromEmail) + "\"},"
                    + "\"to\":[{\"email\":\"" + escapeJson(to) + "\"}],"
                    + "\"subject\":\"" + escapeJson(subject) + "\","
                    + "\"htmlContent\":\"" + escapeJson(htmlBody) + "\","
                    + "\"textContent\":\"" + escapeJson(body) + "\""
                    + "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("Content-Type", "application/json")
                    .header("api-key", brevoApiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            System.out.println("[EmailService] Brevo response status: " + response.statusCode());
            System.out.println("[EmailService] Brevo response body:   " + response.body());

            if (response.statusCode() != 201) {
                throw new RuntimeException(
                        "Brevo API error " + response.statusCode() + ": " + response.body());
            }

            System.out.println("[EmailService] ✓ Email sent successfully to " + to);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email via Brevo API: " + e.getMessage(), e);
        }
    }

    private String escapeJson(String text) {
        if (text == null) return "";
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String escapeHtml(String text) {
        if (text == null) return "";
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
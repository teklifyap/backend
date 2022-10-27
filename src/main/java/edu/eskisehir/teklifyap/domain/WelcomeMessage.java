package edu.eskisehir.teklifyap.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WelcomeMessage {

    private String timestamp;
    private String message;
    private String path;

    public WelcomeMessage() {
        this.timestamp = LocalDateTime.now().toString();
        this.message = "Welcome to Teklifyap API";
        this.path = "/";
    }
}

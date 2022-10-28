package edu.eskisehir.teklifyap.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessMessage {

    private String timestamp;
    private String message;
    private String path;

    public SuccessMessage(String msg, String path) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = msg;
        this.path = path;
    }

}

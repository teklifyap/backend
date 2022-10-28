package edu.eskisehir.teklifyap.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class SuccessDataMessage<T> {

    private String timestamp;
    private T data;
    private String path;

    public SuccessDataMessage(T data, String path) {
        this.timestamp = LocalDateTime.now().toString();
        this.data = data;
        this.path = path;
    }

}

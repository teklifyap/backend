package edu.eskisehir.teklifyap.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDetails {

    private Long timestamp = System.currentTimeMillis();
    private Integer status;
    private String error;
    private String path;

    public ExceptionDetails(Integer status, String error, String path) {
        this.status = status;
        this.error = error;
        this.path = path;
    }
}

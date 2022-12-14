package edu.eskisehir.teklifyap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorksiteDto {

    private Long id;
    private String name;
    private String userName;
    private LocalDateTime date;
    private String offerName;
    private String address;
    private double locationX;
    private double locationY;

}

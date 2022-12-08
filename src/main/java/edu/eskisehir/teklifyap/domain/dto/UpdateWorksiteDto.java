package edu.eskisehir.teklifyap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWorksiteDto {

    private String name;
    private String address;
    private Double locationX;
    private Double locationY;
}

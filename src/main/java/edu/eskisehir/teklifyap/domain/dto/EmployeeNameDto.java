package edu.eskisehir.teklifyap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeNameDto {

    private Long id;
    private String name;
    private String surname;

}

package edu.eskisehir.teklifyap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRowDataDto {

    private int no;
    private String name;
    private String surname;
    private double salary;


}

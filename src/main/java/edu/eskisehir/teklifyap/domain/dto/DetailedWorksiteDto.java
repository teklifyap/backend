package edu.eskisehir.teklifyap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedWorksiteDto {
    private Long id;
    private String name;
    private String userName;
    private LocalDateTime date;
    private String offerName;
    private String address;
    private double locationX;
    private double locationY;
    private List<EmployeeDto> employees;
}

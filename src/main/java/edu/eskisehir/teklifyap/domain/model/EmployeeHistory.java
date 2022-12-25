package edu.eskisehir.teklifyap.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_history")
@Entity
public class EmployeeHistory {


    @Id
    @Column(name = "eid", nullable = false)
    private Long eid;

    private double oldSalary;

    private LocalDate startDate;

    private LocalDate endDate;

}

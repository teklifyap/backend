package edu.eskisehir.teklifyap.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    private LocalDate salaryStartDate;
    @EqualsAndHashCode.Include
    private String name;

    @EqualsAndHashCode.Include
    private String surname;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //@ManyToMany
    //@JoinTable(
    //        name = "employee_worksite",
    //        joinColumns = @JoinColumn(name = "employee_id"),
    //        inverseJoinColumns = @JoinColumn(name = "worksite_id"))
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<WorksiteEmployee> worksiteEmployees = new ArrayList<>();

    private boolean deleted = false;
    private double salary;
}


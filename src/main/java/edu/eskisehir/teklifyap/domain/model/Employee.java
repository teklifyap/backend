package edu.eskisehir.teklifyap.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
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


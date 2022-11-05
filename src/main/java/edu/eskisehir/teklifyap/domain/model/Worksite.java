package edu.eskisehir.teklifyap.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "worksite")
@Entity
@Builder
public class Worksite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String address;
    private double locationX;
    private double locationY;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToMany
    @JoinTable(
            name = "employee_worksite",
            joinColumns = @JoinColumn(name = "worksite_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees;
}

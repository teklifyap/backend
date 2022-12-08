package edu.eskisehir.teklifyap.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "worksite")
@Entity
public class Worksite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String address;
    private LocalDateTime date;
    private double locationX;
    private double locationY;
    private String userName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Worksite(Long id, String name, String address, LocalDateTime date, double locationX, double locationY, String userName, Offer offer) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.date = date;
        this.locationX = locationX;
        this.locationY = locationY;
        this.userName = userName;
        this.offer = offer;
    }

    //@ManyToMany(cascade = CascadeType.ALL)
    //@JoinTable(
    //        name = "employee_worksite",
    //        joinColumns = @JoinColumn(name = "worksite_id"),
    //        inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @OneToMany(mappedBy = "worksite", cascade = CascadeType.ALL)
    private List<WorksiteEmployee> worksiteEmployees=new ArrayList<>();

    public Worksite(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}

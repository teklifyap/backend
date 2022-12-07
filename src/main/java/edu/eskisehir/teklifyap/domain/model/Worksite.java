package edu.eskisehir.teklifyap.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
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
    private String offerName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

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

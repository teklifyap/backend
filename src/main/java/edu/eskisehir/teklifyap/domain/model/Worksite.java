package edu.eskisehir.teklifyap.domain.model;

import lombok.*;

import javax.persistence.*;

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
}

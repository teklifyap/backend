package edu.eskisehir.teklifyap.domain.model;

import edu.eskisehir.teklifyap.domain.enums.Unit;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item")
@Entity
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String value;
    private Unit unit;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

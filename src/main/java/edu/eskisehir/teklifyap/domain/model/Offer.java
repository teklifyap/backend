package edu.eskisehir.teklifyap.domain.model;


import lombok.*;

import javax.persistence.*;
import java.io.File;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offers")
@Entity
@Builder
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private File document;
    private Date date;
    private boolean status;
    private String receiverName;
    private String userName;
    private double profitRate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "worksite_id")
    private Worksite worksite;

    @ManyToMany
    @JoinTable(
            name = "offer_item",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;
}

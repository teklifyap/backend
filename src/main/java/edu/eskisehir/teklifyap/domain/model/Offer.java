package edu.eskisehir.teklifyap.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offers")
@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    private LocalDateTime date;
    private boolean status;
    private String receiverName;
    private String userName;
    private double profitRate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "worksite_id")
    private Worksite worksite;


    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OfferItem> offerItems = new ArrayList<>();

    public Offer(Long id, boolean status, String title) {
        this.id = id;
        this.status = status;
        this.title = title;
    }
}

package edu.eskisehir.teklifyap.domain.model;


import lombok.*;

import javax.persistence.*;
import java.io.File;
import java.util.Date;

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
}

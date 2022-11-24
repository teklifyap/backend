package edu.eskisehir.teklifyap.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.eskisehir.teklifyap.domain.embeded.OfferItemId;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offer_item")
public class OfferItem {

    @EmbeddedId
    private OfferItemId offerItemId = new OfferItemId();
    private double quantity;

    @ManyToOne
    @MapsId("offerId")
    @JoinColumn(name = "offer_id")
    @JsonIgnore
    private Offer offer;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    @JsonIgnore
    private Item item;

}

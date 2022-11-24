package edu.eskisehir.teklifyap.domain.embeded;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;

@Data
@Embeddable
public class OfferItemId implements Serializable {



    private Long offerId;
    private Long itemId;

}

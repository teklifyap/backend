package edu.eskisehir.teklifyap.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOfferItemDto {

    private Long id;
    private double quantity;

}

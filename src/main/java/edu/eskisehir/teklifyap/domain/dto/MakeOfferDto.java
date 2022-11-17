package edu.eskisehir.teklifyap.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MakeOfferDto {

    private String receiverName;
    private String userName;
    private Double profitRate;

}

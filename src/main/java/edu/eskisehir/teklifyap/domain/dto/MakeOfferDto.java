package edu.eskisehir.teklifyap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakeOfferDto {

    private String title;
    private String receiverName;
    private String userName;
    private Double profitRate;

    private List<MakeOfferItemsDto> items;

}

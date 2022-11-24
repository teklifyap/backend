package edu.eskisehir.teklifyap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {

    private Long id;
    private LocalDateTime date;
    private boolean status;
    private String userName;
    private String receiverName;
    private double profitRate;

    private List<ItemDto> items;

}

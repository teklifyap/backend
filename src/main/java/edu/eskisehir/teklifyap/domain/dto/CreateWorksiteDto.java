package edu.eskisehir.teklifyap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWorksiteDto {

    private String name;
    private String userName;
    private Long offerId;
    private String address;
    private Double locationX;
    private Double locationY;
}

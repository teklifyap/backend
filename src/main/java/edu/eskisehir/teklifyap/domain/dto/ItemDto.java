package edu.eskisehir.teklifyap.domain.dto;

import edu.eskisehir.teklifyap.domain.enums.Unit;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long id;
    private String name;
    private double value;
    private Unit unit;

    private Double quantity;

}

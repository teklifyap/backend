package edu.eskisehir.teklifyap.domain.dto;

import edu.eskisehir.teklifyap.domain.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {

    private Long id;
    private String name;
    private String value;
    private Unit unit;

}

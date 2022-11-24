package edu.eskisehir.teklifyap.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.eskisehir.teklifyap.domain.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDto {

    private Long id;
    private String name;
    private double value;
    private Unit unit;

    private Double quantity;

}

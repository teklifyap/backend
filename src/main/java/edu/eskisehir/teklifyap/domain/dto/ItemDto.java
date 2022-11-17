package edu.eskisehir.teklifyap.domain.dto;

import edu.eskisehir.teklifyap.domain.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ItemDto {

    private Long id;
    private String name;
    private String value;
    private Unit unit;

    private Double quantity;

    public ItemDto(Long id, String name, String value, Unit unit, Double quantity) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.quantity = quantity;
    }

    public ItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}

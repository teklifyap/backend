package edu.eskisehir.teklifyap.domain.dto;

public class WorksiteDto {
    private Long id;
    private String name;
    private String address;
    private double locationX;
    private double locationY;

    public WorksiteDto(Long id, String name, String address, double locationX, double locationY) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public WorksiteDto() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }
}

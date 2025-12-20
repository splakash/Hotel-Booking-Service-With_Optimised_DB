package app.HotelManagement.catalog.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyResponse {

    private Long id;
    private String name;
    private String address;
    private Double lowestPrice;
    private Double ratings;
}
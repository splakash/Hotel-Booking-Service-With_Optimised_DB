package app.HotelManagement.catalog.DTO;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PropertyDetailsResponse {
    private Long propertyId;
    private String propertyName;
    private String address;
    private String contactEmail ;
    private String contactPhone;
    private Double lowestPrice;
    private Double ratings;
    private List roomTypes;
}

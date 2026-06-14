package app.HotelManagement.Services;

import app.HotelManagement.catalog.DTO.PropertyDTO.PropertyDetailsResponse;
import app.HotelManagement.catalog.Entity.Property;
import org.springframework.stereotype.Component;

@Component
public class PropertyMapper  {

    public static PropertyDetailsResponse mapToPropertyDetailsResponse(
            Property property,
            Double lowestPrice
    ) {

        PropertyDetailsResponse response =
                new PropertyDetailsResponse();

        response.setPropertyId(property.getId());
        response.setPropertyName(property.getName());
        response.setAddress(property.getCity());
        response.setRatings(5.0);
        response.setContactPhone(property.getContactPhone());
        response.setContactEmail(property.getContactEmail());
        response.setLowestPrice(lowestPrice);
        response.setRoomTypes(null);

        return response;
    }
}

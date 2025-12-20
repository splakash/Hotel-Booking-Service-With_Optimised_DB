package app.HotelManagement.Services;
import app.HotelManagement.catalog.DTO.PropertyDetailsResponse;
import app.HotelManagement.catalog.DTO.RoomTypeResponse;
import app.HotelManagement.catalog.Entity.Property;
import app.HotelManagement.catalog.Entity.RoomType;
import app.HotelManagement.catalog.Repository.PropertyRepo;
import app.HotelManagement.catalog.Repository.RoomRepo;
import app.HotelManagement.catalog.Repository.RoomTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyDetailsService {

    @Autowired
    private PropertyRepo propertyRepo;

    @Autowired
    private RoomTypeRepo roomTypeRepo;

    public PropertyDetailsResponse PropertyDetails(Long propertyId) {

        Property property = propertyRepo.findById(propertyId)
                .orElseThrow(()-> new RuntimeException("Property Not Found"));

        List<RoomType> roomType = roomTypeRepo.findByProperty(propertyId);

        List<RoomTypeResponse> roomTypes = roomType.stream().map(rt->{
            RoomTypeResponse dto = new RoomTypeResponse();
            dto.setId(rt.getId());
            dto.setName(rt.getName());
            dto.setDescription(rt.getDescription());
//            dto.setOccupancyAdults(rt.getOccupancyAdults());
//            dto.setOccupancyChildren(rt.getOccupancyChildren());
            return dto;
        }).toList();

        PropertyDetailsResponse propertyDetailsResponse = new PropertyDetailsResponse();
        propertyDetailsResponse.setPropertyName(property.getName());
        propertyDetailsResponse.setPropertyId(property.getId());
        propertyDetailsResponse.setAddress(property.getAddress());
        propertyDetailsResponse.setRatings(5.00);  // need to change the Ratings
        propertyDetailsResponse.setContactPhone(property.getContactPhone());
        propertyDetailsResponse.setContactEmail(property.getContactEmail());
        propertyDetailsResponse.setLowestPrice(200.00);
        propertyDetailsResponse.setRoomTypes(roomTypes);
        return propertyDetailsResponse;

    }
}

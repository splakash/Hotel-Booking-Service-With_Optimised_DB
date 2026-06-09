package app.HotelManagement.Services;
import app.HotelManagement.catalog.DTO.PropertyDTO.PropertyDetailsResponse;
import app.HotelManagement.catalog.DTO.RoomTypeDTO.RoomTypeResponse;
import app.HotelManagement.catalog.Entity.Property;
import app.HotelManagement.catalog.Entity.RoomType;
import app.HotelManagement.catalog.Repository.PropertyRepo;
import app.HotelManagement.catalog.Repository.RoomTypeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PropertyDetailsService {


    private PropertyRepo propertyRepo;
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
            dto.setBasePrice(rt.getBasePrice());
            dto.setTotalRoom(rt.getTotalRooms());
//            dto.setOccupancyAdults(rt.getOccupancyAdults());
//            dto.setOccupancyChildren(rt.getOccupancyChildren());
            return dto;
        }).toList();
        Double lowestPrice = roomTypeRepo.findLowestPriceByPropertyId(propertyId);
        return getPropertyDetailsResponse(property, lowestPrice, roomTypes);

    }

    private static PropertyDetailsResponse getPropertyDetailsResponse(Property property, Double lowestPrice, List<RoomTypeResponse> roomTypes) {
        PropertyDetailsResponse propertyDetailsResponse = new PropertyDetailsResponse();
        propertyDetailsResponse.setPropertyName(property.getName());
        propertyDetailsResponse.setPropertyId(property.getId());
        propertyDetailsResponse.setAddress(property.getAddress());
        propertyDetailsResponse.setRatings(5.00);  // need to change the Ratings
        propertyDetailsResponse.setContactPhone(property.getContactPhone());
        propertyDetailsResponse.setContactEmail(property.getContactEmail());
        propertyDetailsResponse.setLowestPrice(lowestPrice);
        propertyDetailsResponse.setRoomTypeResponseList(roomTypes);
        return propertyDetailsResponse;
    }
}

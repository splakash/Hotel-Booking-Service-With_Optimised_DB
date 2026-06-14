package app.HotelManagement.Services;

import app.HotelManagement.catalog.DTO.PropertyDTO.PropertyResponse;
import app.HotelManagement.catalog.DTO.PropertyDetailsProjection;
import app.HotelManagement.catalog.DTO.PropertyDTO.PropertyDetailsResponse;
import app.HotelManagement.catalog.DTO.RoomTypeDTO.RoomTypeResponse;
import app.HotelManagement.catalog.Repository.InventoryRepo;
import app.HotelManagement.catalog.Repository.RoomTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AvailabilityService {


    private final RoomTypeService roomTypeService;
    private final InventoryService inventoryService;
    private final InventoryRepo inventoryRepo;
    private final RoomTypeRepo roomTypeRepo;

    @Transactional(readOnly = true)
    public List<PropertyResponse> findAvailableProperties(
            LocalDate checkIn,
            LocalDate checkOut,
            String location
    ) {

        if (checkIn.isBefore(LocalDate.now())) {
            throw new RuntimeException("check in date not allowed");

        }
        if (checkOut.isBefore(checkIn) || checkIn.isEqual(checkOut)) {
            throw new RuntimeException("Check out date is not allowed");
        }

        List<PropertyDetailsProjection> searchResponse = inventoryRepo.fetchAvailableRoomTypeIds(
                checkIn,
                checkOut,
                location
        );

        HashSet< PropertyResponse> properties =
                new HashSet<>();

        for (PropertyDetailsProjection row : searchResponse) {


            PropertyResponse dto = new PropertyResponse();

                                dto.setId(
                                        row.getPropertyId());
                                dto.setName(
                                        row.getPropertyName());
                                  String address = row.getCity() + ", " +row.getState() + ", " + row.getCountry();
                                dto.setAddress(
                                        address);
                                dto.setRatings(
                                        row.getRatings());
            Double lowestPrice = roomTypeService.lowestPricePerProperty(row.getPropertyId());
                                dto.setLowestPrice(
                                        lowestPrice);
                                properties.add(dto);
        }

        return new ArrayList<>(
                properties);

    }

}

//        List<RoomType> roomTypes = roomTypeService.getAll();
//        Set<PropertyDetailsResponse> availableProperties = new HashSet<>();
//
//        for (RoomType roomType : roomTypes) {
//
//            List<LocalDate> dates = checkIn
//                    .datesUntil(checkOut)
//                    .toList();
//
//            List<Inventory> inventories =
//                    inventoryRepo.findByRoomtype_IdAndDateBetween(
//                            roomType.getId(),
//                            checkIn,
//                            checkOut.minusDays(1)
//                    );
//
//            Map<LocalDate, Inventory> inventoryMap =
//                    inventories.stream()
//                            .collect(Collectors.toMap(
//                                    Inventory::getDate,
//                                    i -> i
//                            ));
//
//            int minAvailable = Integer.MAX_VALUE;
//
//            for (LocalDate date : dates) {
//                Inventory inv = inventoryMap.get(date);
//
//                int available = (inv == null)
//                        ? roomType.getTotalRooms()
//                        : inv.getAvailableRooms();
//
//                minAvailable = Math.min(minAvailable, available);
//            }
//            Property property = roomType.getProperty();
//            if(location != null ){
//                if (minAvailable > 0 ) {
//                    if(property.getState().equals(location)  || property.getCity().equals(location) || property.getCountry().equals(location) ) {
//
//                        Double lowestPrice = roomTypeRepo.findLowestPriceByPropertyId(property.getId());
//                        availableProperties.add(PropertyMapper.mapToPropertyDetailsResponse(property,lowestPrice));
//
//
//                    }
//                }
//            }
//            else{
//                if (minAvailable > 0 ) {
//                        Double lowestPrice = roomTypeRepo.findLowestPriceByPropertyId(property.getId());
//                       availableProperties.add(PropertyMapper.mapToPropertyDetailsResponse(property,lowestPrice));
//                }
//            }
//        }
//
//        return new ArrayList<>(availableProperties);
//    }



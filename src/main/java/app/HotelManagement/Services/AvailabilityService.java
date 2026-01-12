package app.HotelManagement.Services;

import app.HotelManagement.catalog.Entity.Inventory;
import app.HotelManagement.catalog.Entity.Property;
import app.HotelManagement.catalog.Entity.RoomType;
import app.HotelManagement.catalog.Repository.InventoryRepo;
import app.HotelManagement.catalog.Repository.RoomTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityService {


    private final RoomTypeRepo roomTypeRepo;
    private final InventoryRepo inventoryRepo;

    @Transactional(readOnly = true)
    public List<Property> findAvailableProperties(
            LocalDate checkIn,
            LocalDate checkOut,
            String location
    ) {

        List<RoomType> roomTypes = roomTypeRepo.findAll();
        Set<Property> availableProperties = new HashSet<>();

        for (RoomType roomType : roomTypes) {

            List<LocalDate> dates = checkIn
                    .datesUntil(checkOut)
                    .toList();

            List<Inventory> inventories =
                    inventoryRepo.findByRoomtype_IdAndDateBetween(
                            roomType.getId(),
                            checkIn,
                            checkOut.minusDays(1)
                    );

            Map<LocalDate, Inventory> inventoryMap =
                    inventories.stream()
                            .collect(Collectors.toMap(
                                    Inventory::getDate,
                                    i -> i
                            ));

            int minAvailable = Integer.MAX_VALUE;

            for (LocalDate date : dates) {
                Inventory inv = inventoryMap.get(date);

                int available = (inv == null)
                        ? roomType.getTotalRooms()
                        : inv.getAvailableRooms();

                minAvailable = Math.min(minAvailable, available);
            }

            if(location != null ){
                if (minAvailable > 0 ) {
                    if(roomType.getProperty().getState().equals(location)  || roomType.getProperty().getCity().equals(location) || roomType.getProperty().getCountry().equals(location) ) {
                        availableProperties.add(roomType.getProperty());
                    }
                }
            }
            else{
                if (minAvailable > 0 ) {
                        availableProperties.add(roomType.getProperty());
                }
            }
        }

        return new ArrayList<>(availableProperties);
    }
}


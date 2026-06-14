package app.HotelManagement.Services;

import app.HotelManagement.ExceptionHandler.RoomTypeNotFoundException;
import app.HotelManagement.catalog.DTO.RoomTypeDTO.RoomTypeRequest;
import app.HotelManagement.catalog.DTO.RoomTypeDTO.RoomTypeResponse;
import app.HotelManagement.catalog.Entity.Inventory;
import app.HotelManagement.catalog.Entity.Property;
import app.HotelManagement.catalog.Entity.RoomType;
import app.HotelManagement.catalog.Repository.InventoryRepo;
import app.HotelManagement.catalog.Repository.RoomTypeRepo;
import app.HotelManagement.catalog.Repository.PropertyRepo;
import jakarta.el.PropertyNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomTypeService {


    private final RoomTypeRepo roomTypeRepo;
    private final PropertyRepo propertyRepo;
    private  final InventoryRepo inventoryRepo;

    public RoomTypeService(RoomTypeRepo roomTypeRepo, PropertyRepo propertyRepo, InventoryRepo inventoryRepo){
        this.roomTypeRepo = roomTypeRepo;
        this.propertyRepo = propertyRepo;
        this.inventoryRepo = inventoryRepo;
    }





    public ResponseEntity<?> addNewRoomTypeService(RoomTypeRequest roomTypeRequest) {

        Long propertyId = roomTypeRequest.getPropertyId();
        Property property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("Property does not exist for the given property id"));

        if(roomTypeRepo.existsByNameAndPropertyId(roomTypeRequest.getName(),roomTypeRequest.getPropertyId()))
        {
            return ResponseEntity.status(409)
                .body(java.util.Map.of("error", "Room Type with name already exists"));
        }
        RoomType roomType = new RoomType();
        roomType.setProperty(property);
        roomType.setName(roomTypeRequest.getName());
        roomType.setBasePrice(roomTypeRequest.getBasePrice());
        roomType.setTotalRooms(roomTypeRequest.getTotalRooms());
//        roomType.setOccupancyAdults(roomTypeRequest.getOccupancyAdults());
//        roomType.setOccupancyChildren(roomTypeRequest.getOccupancyChildren());
        roomType.setDescription(roomTypeRequest.getDescription());

        roomTypeRepo.save(roomType);
        generateInventory(roomType);
        return  ResponseEntity.ok().build();
    }

    @Transactional(readOnly = true)
    public Optional<RoomType> findById(Long id) {
        return roomTypeRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<RoomType>findAll(){
        return roomTypeRepo.findAll();
    }

    public ResponseEntity<?> removeRoomTypeService(Long id) {
        if(!roomTypeRepo.existsById(id)){
            return ResponseEntity.status(404)
                    .body(java.util.Map.of("error", "Room Type with id does not exists"));
        }
        roomTypeRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> updateRoomTypeService(@PathVariable Long id , @RequestBody RoomTypeRequest reqToUpdate){
        Optional<RoomType> OptionalRoomType = roomTypeRepo.findById(id);
        RoomType updatedRoomType = new RoomType();
        if(OptionalRoomType.isPresent()){
            updatedRoomType = OptionalRoomType.get();
            updatedRoomType.setName(reqToUpdate.getName());
//            updatedRoomType.setCode(reqToUpdate.getCode());
//            updatedRoomType.setOccupancyAdults(reqToUpdate.getOccupancyAdults());
//            updatedRoomType.setOccupancyChildren(reqToUpdate.getOccupancyChildren());
            updatedRoomType.setDescription(reqToUpdate.getDescription());

        }else{
            throw new PropertyNotFoundException("Room Type not found with id: " + id);
        }
        RoomType updated = roomTypeRepo.save(updatedRoomType);
        return ResponseEntity.ok(updated);
    }

    @Transactional
    public void generateInventory(RoomType roomType) {

        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(365);

        List<Inventory> inventories = new ArrayList<>();

        while (!start.isAfter(end)) {

            inventories.add(
                    Inventory.builder()
                            .property(roomType.getProperty())
                            .roomtype(roomType)
                            .date(start)
                            .reservedRooms(0)
                            .heldRooms(0)
                            .build()
            );

            start = start.plusDays(1);
        }

        inventoryRepo.saveAll(inventories);
    }

    public List<RoomTypeResponse> FetchRoomType(Long PropertyId) {
        return null;
    }

    public RoomType getById(
            @NotNull(message = "Room Type ID is required")
            Long roomTypeId) {

        return roomTypeRepo.findById(roomTypeId)
                .orElseThrow(
                        RoomTypeNotFoundException::new
                );
    }

    public List<RoomType> getAll(){
        return roomTypeRepo.findAll();
    }

    public Double lowestPricePerProperty(Long propertyId){
        return roomTypeRepo.findLowestPriceByPropertyId(propertyId);
    }

}

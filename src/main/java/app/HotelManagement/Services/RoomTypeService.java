package app.HotelManagement.Services;

import app.HotelManagement.catalog.DTO.RoomTypeRequest;
import app.HotelManagement.catalog.DTO.RoomTypeResponse;
import app.HotelManagement.catalog.Entity.Property;
import app.HotelManagement.catalog.Entity.RoomType;
import app.HotelManagement.catalog.Repository.RoomTypeRepo;
import app.HotelManagement.catalog.Repository.PropertyRepo;
import jakarta.el.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class RoomTypeService {

    @Autowired
    private RoomTypeRepo roomTypeRepo;

    @Autowired
    private PropertyRepo propertyRepo;



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
//        roomType.setOccupancyAdults(roomTypeRequest.getOccupancyAdults());
//        roomType.setOccupancyChildren(roomTypeRequest.getOccupancyChildren());
        roomType.setDescription(roomTypeRequest.getDescription());
        roomTypeRepo.save(roomType);
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

    public List<RoomTypeResponse> FetchRoomType(Long PropertyId) {
        return null;
    }
}

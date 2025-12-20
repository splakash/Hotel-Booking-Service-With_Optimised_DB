package app.HotelManagement.Services;


import app.HotelManagement.catalog.DTO.RoomRequest;
import app.HotelManagement.catalog.Entity.Property;
import app.HotelManagement.catalog.Entity.Room;
import app.HotelManagement.catalog.Entity.RoomType;
import app.HotelManagement.catalog.Repository.RoomRepo;
import app.HotelManagement.catalog.Repository.RoomTypeRepo;
import app.HotelManagement.catalog.Repository.PropertyRepo;

import org.hibernate.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private PropertyRepo propertyRepo;
    @Autowired
    private RoomTypeRepo roomTypeRepo;
    @Autowired
    private RoomRepo roomRepo;


    public ResponseEntity<?> AddRoomService(RoomRequest req) {

        Property property = propertyRepo.findById(req.getPropertyId())
                .orElseThrow(() -> new PropertyNotFoundException("Property does not exist for the given property id"+ req.getPropertyId()));


        RoomType roomType = roomTypeRepo.findById(req.getRoomTypeId())
                .orElseThrow(() -> new PropertyNotFoundException("Property does not exist for the given property id"));

        if (roomRepo.existsByPropertyIdAndRoomNumber(req.getPropertyId(), req.getRoomNumber())) {
            return ResponseEntity.status(409).build(); // Conflict
        }

        Room room = new Room();
        room.setRoomNumber(req.getRoomNumber());
        room.setFloor(req.getFloor());
        room.setStatus(req.getStatus());
        room.setProperty(property);
        room.setRoomtype(roomType);
        try {
            roomRepo.save(room);
        } catch (DataIntegrityViolationException ex) {
            // in case of race condition or DB constraint, return conflict
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.status(201).build();
    }

    @Transactional(readOnly = true)
    public Room findById(Long id){
        return roomRepo.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Room> findAll(){
        return roomRepo.findAll();
    }

    public ResponseEntity<?> removeRoomService(Long id) {
        if(!roomRepo.existsById(id)){
            return ResponseEntity.status(404)
                    .body(java.util.Map.of("error", "Room Type with id does not exists"));
        }
        roomRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> updateRoomService(Long id, RoomRequest req) {
        Optional<Room> roomToUpdate = roomRepo.findById(id);
        Room room = new Room();
        if(roomToUpdate.isPresent()){
            room = roomToUpdate.get();
            room.setRoomNumber(req.getRoomNumber());
            room.setFloor(req.getFloor());
            room.setStatus(req.getStatus());
        }else{
            throw new jakarta.el.PropertyNotFoundException("Room Type not found with id: " + id);
        }

        roomRepo.save(room);
        return ResponseEntity.ok(room);
    }
}

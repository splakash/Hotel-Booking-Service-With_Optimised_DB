package app.HotelManagement.MainController;


import app.HotelManagement.Services.RoomTypeService;
import app.HotelManagement.catalog.DTO.RoomTypeRequest;
import app.HotelManagement.catalog.DTO.RoomTypeResponse;
import app.HotelManagement.catalog.Entity.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;



    //Add new roomType
    @PostMapping("/create/room-type")
    public ResponseEntity<?> addNewRoomType(@RequestBody RoomTypeRequest roomTypeRequest){
        return roomTypeService.addNewRoomTypeService(roomTypeRequest);
    }

    public ResponseEntity<List<RoomTypeResponse>> ListOfRoomTypesForProperty(@RequestParam Long PropertyId){
        return ResponseEntity.ok(roomTypeService.FetchRoomType(PropertyId));
    }

    //update RoomType


    //Get All
    @GetMapping("/room-types")
    public ResponseEntity<List<RoomType>> getAllRoomType(){
        List<RoomType> all = roomTypeService.findAll();
        return ResponseEntity.ok(all);
    }

    //get Room type with id
    @GetMapping("/room-type/{id}")
    public ResponseEntity<?> getRoomType(@PathVariable Long id){
        return roomTypeService.findById(id)
                .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Delete
    @GetMapping("/remove/room-type/{id}")
    public ResponseEntity<?> removeRoomType(@PathVariable Long id){
        return roomTypeService.removeRoomTypeService(id);
    }

    //update
    @PostMapping("/update/room-type/{id}")
    public ResponseEntity<?> updateRoomType(@PathVariable Long id , @RequestBody RoomTypeRequest req){
        return roomTypeService.updateRoomTypeService(id,req);
    }
}

package app.HotelManagement.MainController;


import app.HotelManagement.Services.RoomTypeService;
import app.HotelManagement.catalog.DTO.RoomTypeRequest;
import app.HotelManagement.catalog.Entity.property;
import app.HotelManagement.catalog.Entity.roomType;
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
    @PostMapping("/roomtype/add")
    public ResponseEntity<?> addNewRoomType(@RequestBody RoomTypeRequest roomTypeRequest){
        return roomTypeService.addNewRoomTypeService(roomTypeRequest);
    }

    //Get All
    @GetMapping("/all/roomtype")
    public ResponseEntity<List<roomType>> getAllRoomType(){
        List<roomType> all = roomTypeService.findAll();
        return ResponseEntity.ok(all);
    }

    //get Room type with id
    @GetMapping("/get/roomtype/{id}")
    public ResponseEntity<?> getRoomType(@PathVariable Long id){
        return roomTypeService.findById(id)
                .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Delete
    @GetMapping("/remove/{id}")
    public ResponseEntity<?> removeRoomType(@PathVariable Long id){
        return roomTypeService.removeRoomTypeService(id);
    }

    //update
    @PostMapping("/roomtype/update/{id}")
    public ResponseEntity<?> updateRoomType(@PathVariable Long id , @RequestBody RoomTypeRequest req){
        return roomTypeService.updateRoomTypeService(id,req);
    }
}

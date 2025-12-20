package app.HotelManagement.MainController;

import app.HotelManagement.Services.RoomService;
import app.HotelManagement.catalog.DTO.RoomRequest;
import app.HotelManagement.catalog.Entity.Room;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class RoomController {

    @Autowired
    private RoomService roomService;

    //create new Room
    @PostMapping("/create/room")
    public ResponseEntity<?> AddRoom(@RequestBody RoomRequest req){
        return roomService.AddRoomService(req);
    }

    //get one room
    @GetMapping("/room/{id}")
    public ResponseEntity<?> getRoom(@PathVariable Long id){
        Room room =  roomService.findById(id);
        System.out.println(room.getRoomNumber());
        return ResponseEntity.ok(room);
    }
    //get All Room
    @GetMapping("/rooms")
    public ResponseEntity<?> getRooms(){
         List<Room> rooms = roomService.findAll();
         return ResponseEntity.ok(rooms);
    }

    //Delete room
    @GetMapping("/remove/room/{id}")
    public ResponseEntity<?> removeRoom(@PathVariable Long id){
        return roomService.removeRoomService(id);
    }

    //Update Room
    @PostMapping("/update/room/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody RoomRequest req){
         return roomService.updateRoomService(id,req);
    }

}

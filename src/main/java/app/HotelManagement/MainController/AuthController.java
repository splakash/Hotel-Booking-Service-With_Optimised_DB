package app.HotelManagement.MainController;


import app.HotelManagement.catalog.DTO.userDetailsDTO;
import app.HotelManagement.catalog.Entity.AppUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/create-user")
    public ResponseEntity<?> create_user(@RequestBody AppUser user){
        return null;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody userDetailsDTO userDto){
        return null;
    }

}

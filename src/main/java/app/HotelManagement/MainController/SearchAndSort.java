package app.HotelManagement.MainController;

import app.HotelManagement.Services.AvailabilityService;
import app.HotelManagement.catalog.DTO.SearchRequest;
import app.HotelManagement.catalog.Entity.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class SearchAndSort {

    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping("/search-details")
    public ResponseEntity<?> SearchByDate(@RequestBody SearchRequest req){
        List<Property> properties = availabilityService.findAvailableProperties(req.getCheckIn(),req.getCheckOut(),req.getLocation());
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }
}

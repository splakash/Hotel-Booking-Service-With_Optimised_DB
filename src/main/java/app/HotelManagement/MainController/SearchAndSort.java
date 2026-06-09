package app.HotelManagement.MainController;

import app.HotelManagement.Services.AvailabilityService;
import app.HotelManagement.catalog.DTO.PropertyDTO.PropertyDetailsResponse;
import app.HotelManagement.catalog.DTO.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class SearchAndSort {

    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping("/search-details")
    public ResponseEntity<?> SearchByDate(@RequestBody SearchRequest req){
        List<PropertyDetailsResponse> properties = availabilityService.findAvailableProperties(req.getCheckIn(),req.getCheckOut(),req.getLocation());
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }
}

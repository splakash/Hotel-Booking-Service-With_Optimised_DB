package app.HotelManagement.MainController;

import app.HotelManagement.Services.AvailabilityService;
import app.HotelManagement.catalog.Entity.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/admin")
public class SearchAndSort {

    @Autowired
    private AvailabilityService availabilityService;



    @GetMapping("/search-details")
    public ResponseEntity<?> SearchByDate(@RequestParam LocalDate checkIn , @RequestParam LocalDate checkOut){
        List<Property> properties = availabilityService.findAvailableProperties(checkIn,checkOut);

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }
}

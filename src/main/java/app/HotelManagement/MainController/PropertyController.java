package app.HotelManagement.MainController;
import app.HotelManagement.Services.PropertyDetailsService;
import app.HotelManagement.Services.PropertyService;
import app.HotelManagement.catalog.DTO.PropertyDetailsResponse;
import app.HotelManagement.catalog.DTO.PropertyResponse;
import app.HotelManagement.catalog.DTO.propertyRequest;
import app.HotelManagement.catalog.Entity.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class PropertyController {


    @Autowired
    private final PropertyService propertyservice;

    @Autowired
    private PropertyDetailsService propertyDetailsService;

    public PropertyController(PropertyService propertyservice) {
        this.propertyservice = propertyservice;
    }

    @GetMapping("/properties")
    public ResponseEntity<List<PropertyResponse>> list() {
        List<Property> all = propertyservice.findAll();
        return ResponseEntity.ok(propertyservice.PropertyList());
    }

    @GetMapping("/property/{id}/details")
    public ResponseEntity<PropertyDetailsResponse> getPropertyDetails(@PathVariable Long id){
        return ResponseEntity.ok(propertyDetailsService.PropertyDetails(id));
    }


    @GetMapping("/property/{id}")
    public ResponseEntity<Property> get(@PathVariable Long id) {
        return propertyservice.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



}

package app.HotelManagement.MainController;

import app.HotelManagement.Services.PropertyService;
import app.HotelManagement.catalog.DTO.propertyRequest;
import app.HotelManagement.catalog.Entity.property;
import app.HotelManagement.catalog.Repository.propertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin")
public class PropertyController {


    @Autowired
    private final PropertyService propertyservice;

    public PropertyController(PropertyService propertyservice) {
        this.propertyservice = propertyservice;
    }
    //add new property
    @PostMapping("/add/properties")
    public ResponseEntity<?> addNewProperty(@RequestBody propertyRequest propertyRequest){
        return propertyservice.addNewPropertyService(propertyRequest);
    }

    @GetMapping("/property/all")
    public ResponseEntity<List<property>> list() {
        List<property> all = propertyservice.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/properties/{id}")
    public ResponseEntity<property> get(@PathVariable Long id) {
        return propertyservice.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //delete new property
    @PostMapping("/remove/properties/{id}")
    public ResponseEntity<?> RemoveProperty(@PathVariable Long id){
        return propertyservice.RemovePropertyMethod(id);
    }
    //update new property
    @PostMapping("/update/properties/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id,@RequestBody propertyRequest property){
         return propertyservice.updatePropertyService(id,property);
    }
}

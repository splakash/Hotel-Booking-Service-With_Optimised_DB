package app.HotelManagement.MainController;

import app.HotelManagement.Services.PropertyService;
import app.HotelManagement.catalog.DTO.propertyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin")
public class PropertyControllerAdmin {

    @Autowired
    private final PropertyService propertyservice;

    public PropertyControllerAdmin(PropertyService propertyservice) {
        this.propertyservice = propertyservice;
    }

    @PostMapping("/create/property")
    public ResponseEntity<?> addNewProperty(@RequestBody propertyRequest propertyRequest){
        return propertyservice.addNewPropertyService(propertyRequest);
    }

    @PostMapping("/remove/property/{id}")
    public ResponseEntity<?> RemoveProperty(@PathVariable Long id){
        return propertyservice.RemovePropertyMethod(id);
    }

    //update new property
    @PostMapping("/update/property/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id,@RequestBody propertyRequest property){
        return propertyservice.updatePropertyService(id,property);
    }
}

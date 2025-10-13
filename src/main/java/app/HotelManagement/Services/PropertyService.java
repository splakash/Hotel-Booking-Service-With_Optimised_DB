package app.HotelManagement.Services;

import app.HotelManagement.catalog.DTO.propertyRequest;
import app.HotelManagement.catalog.Entity.property;
import app.HotelManagement.catalog.Repository.propertyRepo;
import jakarta.el.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private propertyRepo propertyRepo;

    public ResponseEntity<?> addNewPropertyService(propertyRequest propertyRequest) {
        if (propertyRepo.existsByName(propertyRequest.getName())) {
            return ResponseEntity.status(409)
                    .body(java.util.Map.of("error", "Property with name already exists"));
        }

        property p = new property();
        p.setName(propertyRequest.getName());
        p.setTimezone(propertyRequest.getTimezone());
        p.setAddress(propertyRequest.getAddress());        // stored as TEXT/JSON-string in entity
        p.setContactEmail(propertyRequest.getContactEmail());
        p.setContactPhone(propertyRequest.getContactPhone());

        property saved = propertyRepo.save(p);

        return ResponseEntity.status(201).build();
    }


    public ResponseEntity<?> RemovePropertyMethod(Long id) {
        if (!(propertyRepo.existsById(id))) {
            return ResponseEntity.status(404)
                    .body(java.util.Map.of("error", "Property with id does not exists"));
        }
        propertyRepo.deleteById(id);

        return ResponseEntity.noContent().build();
    }


    public ResponseEntity<?> updatePropertyService(Long id, propertyRequest propertyToUpdate) {
        Optional<property> optional = propertyRepo.findById(id);
        property updateProperty =  new property();
        if (optional.isPresent()) {
            updateProperty = optional.get();
            System.out.println(updateProperty);
            updateProperty.setName(propertyToUpdate.getName());
            updateProperty.setAddress(propertyToUpdate.getAddress());
            updateProperty.setContactEmail(propertyToUpdate.getContactEmail());
            updateProperty.setContactPhone(propertyToUpdate.getContactPhone());
        } else {
            // Handle not found case
            throw new PropertyNotFoundException("Property not found with id: " + id);
        }
        property updated = propertyRepo.save(updateProperty);

        return  ResponseEntity.ok(updated);


    }


    @Transactional(readOnly = true)
    public Optional<property> findById(Long id) {
        return propertyRepo.findById(id);
    }
    @Transactional(readOnly = true)
    public List<property> findAll() {
        return propertyRepo.findAll();
    }
}

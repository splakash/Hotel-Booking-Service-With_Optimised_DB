package app.HotelManagement.Services;

import app.HotelManagement.catalog.DTO.PropertyResponse;
import app.HotelManagement.catalog.DTO.propertyRequest;
import app.HotelManagement.catalog.Entity.Property;
import app.HotelManagement.catalog.Repository.PropertyRepo;
import jakarta.el.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepo propertyRepo;


    public List<PropertyResponse> PropertyList() {
        List<Property> properties = propertyRepo.findAll();
        return properties.stream().map(p -> {
//            Double lowestPrice = ratePlanRepository.findLowestPriceByProperty(p.getId()); // for now i am not calculating prices

            PropertyResponse dto = new PropertyResponse();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setAddress(p.getAddress());
            dto.setLowestPrice(299.00);
            dto.setRatings(4.0);
            //dto.setLowestPrice(lowestPrice != null ? lowestPrice : 0.0);

            return dto;
        }).toList();
    }


    public ResponseEntity<?> addNewPropertyService(propertyRequest propertyRequest) {
        if (propertyRepo.existsByName(propertyRequest.getName())) {
            return ResponseEntity.status(409)
                    .body(java.util.Map.of("error", "Property with name already exists"));
        }

        Property p = new Property();
        p.setName(propertyRequest.getName());
        p.setTimezone(propertyRequest.getTimezone());
        p.setAddress(propertyRequest.getAddress());        // stored as TEXT/JSON-string in entity
        p.setContactEmail(propertyRequest.getContactEmail());
        p.setContactPhone(propertyRequest.getContactPhone());

        Property saved = propertyRepo.save(p);

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
        Optional<Property> optional = propertyRepo.findById(id);
        Property updateProperty =  new Property();
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
        Property updated = propertyRepo.save(updateProperty);

        return  ResponseEntity.ok(updated);


    }


    @Transactional(readOnly = true)
    public Optional<Property> findById(Long id) {
        return propertyRepo.findById(id);
    }
    @Transactional(readOnly = true)
    public List<Property> findAll() {
        return propertyRepo.findAll();
    }



}

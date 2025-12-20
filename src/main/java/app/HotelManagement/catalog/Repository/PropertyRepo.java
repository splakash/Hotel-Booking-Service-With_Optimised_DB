package app.HotelManagement.catalog.Repository;

import app.HotelManagement.catalog.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepo extends JpaRepository<Property,Long> {
    boolean existsByName(String name);
}

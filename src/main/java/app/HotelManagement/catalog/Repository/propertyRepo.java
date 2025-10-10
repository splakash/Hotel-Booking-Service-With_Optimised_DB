package app.HotelManagement.catalog.Repository;

import app.HotelManagement.catalog.Entity.property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface propertyRepo extends JpaRepository<property,Long> {
}

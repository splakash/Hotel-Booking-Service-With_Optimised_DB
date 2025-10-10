package app.HotelManagement.catalog.Repository;

import app.HotelManagement.catalog.Entity.inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface inventoryRepo extends JpaRepository<inventory,Long> {
}

package app.HotelManagement.catalog.Repository;

import app.HotelManagement.catalog.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory,Long> {

    List<Inventory> findByRoomtype_IdAndDateBetween(
            Long roomTypeId,
            LocalDate start,
            LocalDate end
    );

    Optional<Inventory> findByRoomtype_IdAndDate(
            Long roomTypeId,
            LocalDate date
    );
}

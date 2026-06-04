package app.HotelManagement.catalog.Repository;

import app.HotelManagement.catalog.Entity.Inventory;
import app.HotelManagement.catalog.Entity.Property;
import app.HotelManagement.catalog.Entity.RoomType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Inventory i WHERE i.property = :property AND i.roomtype = :roomType AND i.date = :date")
    Optional<Inventory> findWithLock(
            @Param("property") Property property,
            @Param("roomType") RoomType roomType,
            @Param("date") LocalDate date
    );
}

package app.HotelManagement.catalog.Repository;

import app.HotelManagement.catalog.DTO.PropertyDetailsProjection;
import app.HotelManagement.catalog.Entity.Inventory;
import app.HotelManagement.catalog.Entity.Property;
import app.HotelManagement.catalog.Entity.RoomType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    @Query("SELECT i FROM Inventory i JOIN FETCH i.roomtype JOIN FETCH i.property WHERE i.property = :property AND i.roomtype = :roomType AND i.date = :finalDate")
    Optional<Inventory> findWithLock(
            @Param("property") Property property,
            @Param("roomType") RoomType roomType,
            @Param("finalDate") LocalDate finalDate
    );

    @Query(value = " SELECT p.id AS propertyId,p.name AS propertyName, p.contact_phone as contactPhone, p.contact_email as contactEmail, p.city AS city, p.state AS state, p.country AS country, rt.id AS roomTypeId, rt.description AS roomTypeDescription, rt.base_price AS basePrice, rt.total_rooms AS totalRooms,rt.occupancy_adults AS occupancyAdults,rt.occupancy_children AS occupancyChildren,MIN(rt.total_rooms - i.reserved_rooms - i.held_rooms) AS availableRooms FROM inventory i JOIN room_type rt  ON i.room_type_id = rt.id JOIN property p ON i.property_id = p.id WHERE i.date >= :checkIn AND i.date < :checkOut AND (:location IS NULL OR LOWER(p.city) LIKE LOWER(CONCAT('%', :location, '%')) OR LOWER(p.country) LIKE LOWER(CONCAT('%', :location, '%')) OR LOWER(p.state) LIKE LOWER(CONCAT('%', :location, '%'))) GROUP BY p.id, p.name, p.city, p.state, p.country,rt.id, rt.description, rt.base_price,rt.total_rooms, rt.occupancy_adults, rt.occupancy_children HAVING MIN(rt.total_rooms - i.reserved_rooms - i.held_rooms) > 0",nativeQuery = true)
    List<PropertyDetailsProjection> fetchAvailableRoomTypeIds(
            @Param("checkIn")    LocalDate checkIn,
            @Param("checkOut")   LocalDate checkOut,
            @Param("location")       String location
    );
}

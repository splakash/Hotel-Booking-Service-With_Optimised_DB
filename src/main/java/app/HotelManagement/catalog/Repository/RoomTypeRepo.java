package app.HotelManagement.catalog.Repository;

import app.HotelManagement.catalog.Entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomTypeRepo extends JpaRepository<RoomType,Long> {
    boolean existsByNameAndPropertyId(String name,long property_id);

    @Query("SELECT rt FROM RoomType rt WHERE rt.property.id = :propertyId")
    List<RoomType> findByProperty(@Param("propertyId") Long propertyId);

    @Query("SELECT MIN(rt.basePrice) FROM RoomType rt WHERE rt.property.id = :propertyId")
    Double findLowestPriceByPropertyId(Long propertyId);

    @Query("""
    SELECT rt
    FROM RoomType rt
    JOIN rt.property p
    JOIN Inventory i ON i.roomtype = rt
    WHERE
        (
            :location IS NULL
            OR LOWER(p.city) = LOWER(:location)
            OR LOWER(p.state) = LOWER(:location)
            OR LOWER(p.country) = LOWER(:location)
        )
        AND i.date >= :checkIn
        AND i.date < :checkOut
    GROUP BY
        rt.id,
        rt.name,
        rt.totalRooms,
        rt.basePrice,
        p.id
    HAVING MIN(
        rt.totalRooms
        - i.reservedRooms
        - i.heldRooms
    ) > 0
    """)
    List<RoomType> findAvailableRoomTypes(
            LocalDate checkIn,
            LocalDate checkOut,
            String location
    );
}

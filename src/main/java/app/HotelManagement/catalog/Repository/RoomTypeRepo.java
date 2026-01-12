package app.HotelManagement.catalog.Repository;

import app.HotelManagement.catalog.Entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepo extends JpaRepository<RoomType,Long> {
    boolean existsByNameAndPropertyId(String name,long property_id);

    @Query("SELECT rt FROM RoomType rt WHERE rt.property.id = :propertyId")
    List<RoomType> findByProperty(@Param("propertyId") Long propertyId);

    @Query("SELECT MIN(rt.basePrice) FROM RoomType rt WHERE rt.property.id = :propertyId")
    Double findLowestPriceByPropertyId(Long propertyId);
}

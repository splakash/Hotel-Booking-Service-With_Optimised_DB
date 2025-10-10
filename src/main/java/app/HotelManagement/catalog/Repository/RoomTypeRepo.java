package app.HotelManagement.catalog.Repository;

import app.HotelManagement.catalog.Entity.roomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepo extends JpaRepository<roomType,Long> {
}

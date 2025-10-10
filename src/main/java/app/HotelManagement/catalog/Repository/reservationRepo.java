package app.HotelManagement.catalog.Repository;

import app.HotelManagement.catalog.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface reservationRepo extends JpaRepository<Reservation,Long> {
}

package app.HotelManagement.ReservationManager.ReservationService;

import app.HotelManagement.Services.InventoryService;
import app.HotelManagement.catalog.Entity.Enum.ReservationStatus;
import app.HotelManagement.catalog.Entity.Reservation;
import app.HotelManagement.catalog.Repository.ReservationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@Transactional
@Service
public class ReservationExpiryService {

    private final ReservationRepo reservationRepo;
    private final InventoryService inventoryService;


    public void expireReservations() {

        List<Reservation> reservations =
                reservationRepo.findByStatus(
                        ReservationStatus.PENDING_PAYMENT);

        reservations.forEach(this::expire);
    }

    private void expire(Reservation reservation) {

        if (reservation.getCreatedAt()
                .isBefore(LocalDateTime.now().minusMinutes(5))) {

            inventoryService.releaseInventory(
                    reservation);

            reservation.setStatus(
                    ReservationStatus.EXPIRED);

            reservationRepo.save(reservation);
        }
    }
}
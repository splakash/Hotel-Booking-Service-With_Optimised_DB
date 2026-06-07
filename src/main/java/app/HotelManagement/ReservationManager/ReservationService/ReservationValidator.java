package app.HotelManagement.ReservationManager.ReservationService;


import app.HotelManagement.catalog.Entity.Enum.ReservationStatus;
import app.HotelManagement.catalog.Entity.Reservation;
import app.HotelManagement.catalog.Repository.ReservationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@Component
public class ReservationValidator {

    private final ReservationRepo reservationRepo;


    public void reservationExpiryCheck(Reservation reservation){
        if (reservation.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
            reservation.setStatus(ReservationStatus.EXPIRED);
            reservationRepo.save(reservation);
            throw new RuntimeException("Reservation expired");
        }
    }

}

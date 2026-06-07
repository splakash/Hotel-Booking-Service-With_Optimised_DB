package app.HotelManagement.Scheduler;

import app.HotelManagement.ReservationManager.ReservationService.ReservationExpiryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class ReservationExpiryScheduler {

    private final ReservationExpiryService expiryService;

    public ReservationExpiryScheduler(
            ReservationExpiryService expiryService) {
        this.expiryService = expiryService;
    }

    @Scheduled(fixedRate = 120000)
    public void run() {
        expiryService.expireReservations();
    }
}
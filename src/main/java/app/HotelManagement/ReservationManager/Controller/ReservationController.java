package app.HotelManagement.ReservationManager.Controller;



import app.HotelManagement.ReservationManager.ReservationService.ReservationService;
import app.HotelManagement.catalog.DTO.ReservationDTO.ReservationRequest;
import app.HotelManagement.catalog.DTO.ReservationDTO.ReservationDetailsResponse;
import app.HotelManagement.catalog.Entity.Reservation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/res")
public class ReservationController {



    @Autowired
    public ReservationService reservationService;

    //1. save successfully reserved bookings (payment done)

    //2. save bookings who filled the details payment pending
    @PostMapping("/reserve/booking")
    public ResponseEntity<?> booking(@RequestBody @Valid ReservationRequest reservationRequest){
        Reservation reservation = reservationService.initiateReservationService(reservationRequest);
        return ResponseEntity.ok(reservation.getCode());
    }

    @GetMapping("/booking/{id}")
    public ReservationDetailsResponse getBookingDetails(@PathVariable String id){
        return reservationService.getBookingDetailsService(id);
    }

    @GetMapping("/payments/success/{bookingId}")
    public ResponseEntity<?> success(@PathVariable("bookingId") String bookingId) {
        reservationService.confirmReservation(bookingId);
        return ResponseEntity.ok("Payment successful");
    }

//      once the payment method will be integrated then it will be activated
//    @GetMapping("/payments/failure/{id}")
//    public ResponseEntity<?> failure(@PathVariable Long id) {
//        reservationService.releaseReservation(id);
//        return ResponseEntity.ok("Payment failed");
//    }

    @GetMapping("/my-bookings")
    public ResponseEntity<?> fetchBookingByCustomer(Authentication authentication){
        if(authentication==null)throw new RuntimeException("Authentication is Invalid or Null");
        String UserID = authentication.getName();
        List<ReservationDetailsResponse> a1 = reservationService.fetchBookingByCustomerService(UserID);
        return ResponseEntity.ok(a1);
    }


}

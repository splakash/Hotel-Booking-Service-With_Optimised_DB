package app.HotelManagement.ReservationService;



import app.HotelManagement.catalog.DTO.ReservationRequest;
import app.HotelManagement.catalog.DTO.ReservationDetailsResponse;
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

    //2. save bookings who has filled the details payment pending
    @PostMapping("/reserve/booking")
    public ResponseEntity<?> booking(@RequestBody @Valid ReservationRequest reservationRequest){
        Reservation reservation = reservationService.confirmReservationService(reservationRequest);
        return ResponseEntity.ok(reservation.getCode());
    }

    @GetMapping("/booking/{id}")
    public ReservationDetailsResponse getBookingDetails(@PathVariable String id){
        return reservationService.getBookingDetailsService(id);
    }

    @PostMapping("/payments/success/{id}")
    public ResponseEntity<?> success(@PathVariable Long id) {
        reservationService.confirmReservation(id);
        return ResponseEntity.ok("Payment successful");
    }

//      once the payment method will be integrated then it will be activated
    @PostMapping("/payments/failure/{id}")
    public ResponseEntity<?> failure(@PathVariable Long id) {
        reservationService.releaseReservation(id);
        return ResponseEntity.ok("Payment failed");
    }
    // 3. retrieve booking information based on booking id;


    //4. update booking details based on booking id
    @GetMapping("/my-bookings")
    public ResponseEntity<?> fetchBookingByCustomer(Authentication authentication){
        System.out.println(authentication);
        if(authentication==null)throw new RuntimeException("Authentication is Invalid or Null");
        String UserID = authentication.getName();
        List<ReservationDetailsResponse> a1 = reservationService.fetchBookingByCustomerService(UserID);
        System.out.println(a1.size());
        return ResponseEntity.ok(a1);
    }

}

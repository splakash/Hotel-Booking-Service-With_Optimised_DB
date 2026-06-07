package app.HotelManagement.ExceptionHandler;


import app.HotelManagement.catalog.Entity.Reservation;
import jakarta.el.PropertyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<?> handlePropertyNotFound(PropertyNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(Map.of("error", ex.getMessage()));
    }

//    @ExceptionHandler(
//            RoomTypeNotFoundException.class)
//    public ResponseEntity<?> handleReservationNotFound(
//            RoomTypeNotFoundException ex) {
//
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(ex.getMessage());
//    }

    @ExceptionHandler(
            InventoryUnavailableException.class)
    public ResponseEntity<?> handleInventoryUnavailable(
            InventoryUnavailableException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }




}

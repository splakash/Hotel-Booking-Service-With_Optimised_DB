package app.HotelManagement.ReservationManager.Mapper;

import app.HotelManagement.catalog.DTO.ReservationRequest;
import app.HotelManagement.catalog.Entity.Enum.ReservationStatus;
import app.HotelManagement.catalog.Entity.Property;
import app.HotelManagement.catalog.Entity.Reservation;
import app.HotelManagement.catalog.Entity.RoomType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ReservationMapper {

    public Reservation toPendingReservation(
            ReservationRequest request,
            Property property,
            RoomType roomType) {

        return Reservation.builder()
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .contactPhone(request.getContactPhone())
                .contactName(request.getContactName())
                .contactEmail(request.getContactEmail())
                .guestAdult(request.getGuestAdult())
                .guestChildren(request.getGuestChildren())
                .status(ReservationStatus.PENDING_PAYMENT)
                .property(property)
                .roomtype(roomType)
                .totalAmount(request.getTotalAmount())
                .code(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .build();
    }
}

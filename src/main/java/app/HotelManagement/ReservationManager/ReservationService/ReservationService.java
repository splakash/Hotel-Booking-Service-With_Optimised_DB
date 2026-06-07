package app.HotelManagement.ReservationManager.ReservationService;


import app.HotelManagement.ReservationManager.Mapper.ReservationMapper;
import app.HotelManagement.Services.InventoryService;
import app.HotelManagement.Services.PropertyService;
import app.HotelManagement.Services.RoomTypeService;
import app.HotelManagement.catalog.DTO.ReservationRequest;
import app.HotelManagement.catalog.DTO.ReservationDetailsResponse;
import app.HotelManagement.catalog.Entity.Enum.ReservationStatus;
import app.HotelManagement.catalog.Entity.Inventory;
import app.HotelManagement.catalog.Entity.Property;
import app.HotelManagement.catalog.Entity.Reservation;
import app.HotelManagement.catalog.Entity.RoomType;
import app.HotelManagement.catalog.Repository.InventoryRepo;
import app.HotelManagement.catalog.Repository.PropertyRepo;
import app.HotelManagement.catalog.Repository.ReservationRepo;
import app.HotelManagement.catalog.Repository.RoomTypeRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepo reservationRepo;
    private final InventoryService inventoryService;
    private final PropertyService propertyService;
    private final RoomTypeService roomTypeService;
    private final ReservationValidator reservationValidator;
    private final ReservationMapper reservationMapper;





    @Transactional
    public Reservation initiateReservationService(
            ReservationRequest request) {

        Property property = propertyService.getById(
                request.getPropertyId());

        RoomType roomType = roomTypeService.getById(
                request.getRoomTypeId());

        inventoryService.holdInventory(
                property,
                roomType,
                request.getCheckIn(),
                request.getCheckOut());

        Reservation reservation =
                reservationMapper.toPendingReservation(
                        request,
                        property,
                        roomType);

        return reservationRepo.save(
                reservation);
    }

    public ReservationDetailsResponse getBookingDetailsService(String reservationId) {
        ReservationDetailsResponse BookingDetailsResponse;
        try {
            BookingDetailsResponse = reservationRepo.getBookingDetailsByBookingCode(reservationId);

            if (BookingDetailsResponse == null) {
                throw new RuntimeException("Reservation not found with code: " + reservationId);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching reservation", e);
        }
        return BookingDetailsResponse;
    }

    @Transactional
    public void confirmReservation(String bookingId ) {

        Reservation reservation = reservationRepo.findByCode(bookingId);
        if(reservation == null )throw new RuntimeException("Booking Details Not Found");
        if (reservation.getStatus() != ReservationStatus.PENDING_PAYMENT) {
            throw new RuntimeException("Invalid state");
        }
        reservationValidator.reservationExpiryCheck(reservation);
        inventoryService.confirmInventory(
                reservation);

        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationRepo.save(reservation);
    }

    public List<ReservationDetailsResponse> fetchBookingByCustomerService(String contactEmail){

        // Query to fetch reservation details along with property name, address and room type
        List<ReservationDetailsResponse> a1= reservationRepo.getBookingDetailsByCustomerId(contactEmail);
        if(a1.isEmpty()){
            throw  new RuntimeException("No Bookings");
        }

        return a1;
    }


}

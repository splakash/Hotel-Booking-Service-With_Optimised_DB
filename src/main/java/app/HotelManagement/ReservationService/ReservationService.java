package app.HotelManagement.ReservationService;


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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    private final PropertyRepo propertyRepo;

    private final RoomTypeRepo roomTypeRepo;

    private final ReservationRepo reservationRepo;

    private final InventoryRepo inventoryRepo;

    public ReservationService(PropertyRepo propertyRepo, RoomTypeRepo roomTypeRepo, ReservationRepo reservationRepo, InventoryRepo inventoryRepo) {
        this.propertyRepo = propertyRepo;
        this.roomTypeRepo = roomTypeRepo;
        this.reservationRepo = reservationRepo;
        this.inventoryRepo = inventoryRepo;
    }

    public Reservation confirmReservationService(ReservationRequest request){

        Property property = (propertyRepo.findById(request.getPropertyId()).orElseThrow());
        RoomType roomType = (roomTypeRepo.findById(request.getRoomTypeId()).orElseThrow());

        LocalDate date = request.getCheckIn();
        // logic to check the room availability at booking time

        Reservation newReservation = new Reservation();
        newReservation.setCheckIn(request.getCheckIn());
        newReservation.setCheckOut(request.getCheckOut());
        newReservation.setContactPhone(request.getContactPhone());
        newReservation.setContactName(request.getContactName());
        newReservation.setContactEmail(request.getContactEmail());
        newReservation.setGuestAdult(request.getGuestAdult());
        newReservation.setGuestChildren(request.getGuestChildren());
        newReservation.setStatus(ReservationStatus.PENDING_PAYMENT);
        newReservation.setProperty(property);
        newReservation.setRoomtype(roomType);
        newReservation.setTotalAmount(request.getTotalAmount());
        newReservation.setCode(UUID.randomUUID().toString());


        return reservationRepo.save(newReservation);

    }

    public ReservationDetailsResponse getBookingDetailsService(String reservationId) {
        ReservationDetailsResponse BookingDetailsResponse;
        try {
            BookingDetailsResponse = reservationRepo.getBookingDetailsByBookingCode(reservationId);

            if (BookingDetailsResponse == null) {
                throw new RuntimeException("Reservation not found with code: " + reservationId);
            }
            //implement custom query to update ReservationDetailsResponse's property id and room type id with its name
        } catch (RuntimeException e) {
            throw e; // rethrow custom/runtime exceptions
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching reservation", e);
        }
        return BookingDetailsResponse;
    }

    @Transactional
    public void confirmReservation(Long reservationId) {

        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (reservation.getStatus() != ReservationStatus.PENDING_PAYMENT) {
            throw new RuntimeException("Invalid state");
        }

        LocalDate date = reservation.getCheckIn();

        while (date.isBefore(reservation.getCheckOut())) {

            Inventory inventory = inventoryRepo
                    .findWithLock(reservation.getProperty(), reservation.getRoomtype(), date)
                    .orElseThrow(() -> new RuntimeException("Inventory not found"));

            inventory.setHeldRooms(inventory.getHeldRooms() - 1);
            inventory.setReservedRooms(inventory.getReservedRooms() + 1);

            inventoryRepo.save(inventory);

            date = date.plusDays(1);
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationRepo.save(reservation);
    }

    @Transactional
    public void releaseReservation(Long reservationId) {

        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (reservation.getStatus() != ReservationStatus.PENDING_PAYMENT) {
            return;
        }

        LocalDate date = reservation.getCheckIn();

        while (date.isBefore(reservation.getCheckOut())) {

            Inventory inventory = inventoryRepo
                    .findWithLock(reservation.getProperty(), reservation.getRoomtype(), date)
                    .orElseThrow(() -> new RuntimeException("Inventory not found"));

            inventory.setHeldRooms(inventory.getHeldRooms() - 1);

            inventoryRepo.save(inventory);

            date = date.plusDays(1);
        }

        reservation.setStatus(ReservationStatus.EXPIRED);
        reservationRepo.save(reservation);
    }



    public void expireReservations() {

        List<Reservation> pendingReservations =
                reservationRepo.findByStatus(ReservationStatus.PENDING_PAYMENT);
        System.out.println("Scheduled triggers");
        for (Reservation res : pendingReservations) {
            if (res.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(10))) {
                releaseReservation(res.getId());
            }
        }
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

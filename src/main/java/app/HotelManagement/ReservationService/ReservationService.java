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
import java.util.ArrayList;
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



    @Transactional
    public Reservation initiateReservationService(ReservationRequest request){

        Property property = propertyRepo.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));
        RoomType roomType = roomTypeRepo.findById(request.getRoomTypeId())
                .orElseThrow(() -> new RuntimeException("RoomType not found"));

        // Step 1: Check availability and hold inventory for all dates
        LocalDate date = request.getCheckIn();
        List<Inventory> toUpdate = new ArrayList<>();

        while (date.isBefore(request.getCheckOut())) {

            LocalDate finalDate = date;
            Inventory inventory = inventoryRepo
                    .findWithLock(property, roomType, finalDate)
                    .orElseThrow(() -> new RuntimeException("Inventory not found for date: " + finalDate));

            // Uses your @Transient method which now accounts for heldRooms too
            if (inventory.getAvailableRooms() <= 0) {
                throw new RuntimeException("Room not available for date: " + date);
            }

            inventory.setHeldRooms(inventory.getHeldRooms() + 1);
            toUpdate.add(inventory);

            date = date.plusDays(1);
        }

        // Step 2: Save all inventory updates at once
        inventoryRepo.saveAll(toUpdate);

        // Step 3: Save reservation
        Reservation reservation = new Reservation();
        reservation.setCheckIn(request.getCheckIn());
        reservation.setCheckOut(request.getCheckOut());
        reservation.setContactPhone(request.getContactPhone());
        reservation.setContactName(request.getContactName());
        reservation.setContactEmail(request.getContactEmail());
        reservation.setGuestAdult(request.getGuestAdult());
        reservation.setGuestChildren(request.getGuestChildren());
        reservation.setStatus(ReservationStatus.PENDING_PAYMENT);
        reservation.setProperty(property);
        reservation.setRoomtype(roomType);
        reservation.setTotalAmount(request.getTotalAmount());
        reservation.setCode(UUID.randomUUID().toString());
        reservation.setCreatedAt(LocalDateTime.now());

        return reservationRepo.save(reservation);
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


        //validation: if reservation.createdAt has over 5 min then it should call failed reservation
//        if (reservation.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
//            reservation.setStatus(ReservationStatus.EXPIRED);
//            reservationRepo.save(reservation);
//            throw new RuntimeException("Reservation expired");
//        }
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


    @Scheduled(fixedRate = 120000) // every  2 min
    @Transactional
    public void expireReservations() {

        List<Reservation> pendingReservations =
                reservationRepo.findByStatus(ReservationStatus.PENDING_PAYMENT);
        System.out.println("zscheduler hit");
        for (Reservation res : pendingReservations) {

            if (res.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
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

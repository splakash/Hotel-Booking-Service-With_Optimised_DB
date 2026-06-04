package app.HotelManagement.catalog.Repository;


import app.HotelManagement.catalog.DTO.ReservationDetailsResponse;
import app.HotelManagement.catalog.Entity.Enum.ReservationStatus;
import app.HotelManagement.catalog.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation,Long> {
    List<Reservation> findByStatus(ReservationStatus reservationStatus);

    Reservation findByCode(String code);
    Reservation findByContactEmail(String contactEmail);


    @Query("""
       SELECT new app.HotelManagement.catalog.DTO.ReservationDetailsResponse(
            r.checkIn,
            r.checkOut,
            r.code,
            r.contactEmail,
            r.contactName,
            r.contactPhone,
            r.updatedAt,
            r.guestAdult,
            r.guestChildren,
            r.status,
            r.totalAmount,
            p.name,
            p.city,
            p.state,
            p.country,
            rt.name
       )
       FROM Reservation r
       JOIN r.property p
       JOIN r.roomtype rt
       WHERE r.contactEmail = :contactEmail
       """)
    List<ReservationDetailsResponse> getBookingDetailsByCustomerId(
            @Param("contactEmail") String contactEmail);


    @Query("""
       SELECT new app.HotelManagement.catalog.DTO.ReservationDetailsResponse(
            r.checkIn,
            r.checkOut,
            r.code,
            r.contactEmail,
            r.contactName,
            r.contactPhone,
            r.updatedAt,
            r.guestAdult,
            r.guestChildren,
            r.status,
            r.totalAmount,
            p.name,
            p.city,
            p.state,
            p.country,
            rt.name
       )
       FROM Reservation r
       JOIN r.property p
       JOIN r.roomtype rt
       WHERE r.code = :code
       """)
    ReservationDetailsResponse getBookingDetailsByBookingCode(
            @Param("code" ) String code
    );
}

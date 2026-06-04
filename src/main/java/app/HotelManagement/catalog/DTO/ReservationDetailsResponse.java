package app.HotelManagement.catalog.DTO;
import app.HotelManagement.catalog.Entity.Enum.ReservationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor

@Builder
public class ReservationDetailsResponse {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String code;
    private String contactEmail;
    private String contactName;
    private String contactPhone;
    private LocalDateTime updatedAt;
    private int guestAdult;
    private int guestChildren;
    private ReservationStatus status;
    private BigDecimal totalAmount;
    private String propertyName;
    private String city;
    private String state;
    private String country;
    private String roomTypeName;

    public ReservationDetailsResponse(
            LocalDate checkIn,
            LocalDate checkOut,
            String code,
            String contactEmail,
            String contactName,
            String contactPhone,
            LocalDateTime updatedAt,
            int guestAdult,
            int guestChildren,
            ReservationStatus status,
            BigDecimal totalAmount,
            String propertyName,
            String city,
            String state,
            String country,
            String roomTypeName
    ) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.code = code;
        this.contactEmail = contactEmail;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.updatedAt = updatedAt;
        this.guestAdult = guestAdult;
        this.guestChildren = guestChildren;
        this.status = status;
        this.totalAmount = totalAmount;
        this.propertyName = propertyName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.roomTypeName = roomTypeName;
    }


}


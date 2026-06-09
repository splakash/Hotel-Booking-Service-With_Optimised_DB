package app.HotelManagement.catalog.DTO.ReservationDTO;


import jakarta.validation.constraints.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "Room Type ID is required")
    private Long roomTypeId;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkIn;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOut;

    @Min(value = 1, message = "At least one adult is required")
    private int guestAdult;

    @Min(value = 0, message = "Children cannot be negative")
    private int guestChildren;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal totalAmount;

    @Email(message = "Invalid email format")
    private String contactEmail;

    @Min(value = 1, message = "At least one Room is required")
    private int noOfRooms;

    @NotBlank(message = "Contact name is required")
    private String contactName;

    @NotBlank(message = "Contact phone is required")
    private String contactPhone;
}

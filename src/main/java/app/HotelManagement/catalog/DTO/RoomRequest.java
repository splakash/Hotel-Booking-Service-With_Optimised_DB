package app.HotelManagement.catalog.DTO;


import app.HotelManagement.catalog.Entity.Enum.RoomStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequest {

        @NotNull(message = "Property ID is required")
        private Long propertyId;

        @NotNull(message = "Room Type ID is required")
        private Long roomTypeId;

        @NotBlank(message = "Room Number is required")
        @Size(max = 50)
        private String roomNumber;

        //Not blank is only for String not for integer and enums for those not null is required
        @NotNull()
        private int floor;

        @NotNull(message = "Room Status is required")
        private RoomStatus status;


}

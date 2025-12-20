package app.HotelManagement.catalog.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomTypeRequest {

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotBlank(message = "Code is required")
    @Size(max = 50)
    private String code;

    @NotBlank(message = "total number of room is required")
    private Integer totalRooms;
    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;

    private double basePrice;

//    @NotNull(message = "Occupancy (adults) is required")
//    @Min(value = 1, message = "Adults must be at least 1")
//    private Integer occupancyAdults;
//
//    @NotNull(message = "Occupancy (children) is required")
//    @Min(value = 0, message = "Children cannot be negative")
//    private Integer occupancyChildren;

    @Size(max = 500)
    private String description;
}

package app.HotelManagement.catalog.DTO.RoomTypeDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomTypeResponse {

    private Long id;
    private String name;
    private String description;
    private Integer totalRoom;
    private double basePrice;
    private Integer occupancyAdults;
    private Integer occupancyChildren;
}

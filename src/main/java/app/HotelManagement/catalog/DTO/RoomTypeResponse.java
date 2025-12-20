package app.HotelManagement.catalog.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

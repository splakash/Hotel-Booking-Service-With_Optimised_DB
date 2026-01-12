package app.HotelManagement.catalog.DTO;


import app.HotelManagement.catalog.Entity.Enum.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userDetailsDTO {

    private String username;
    private String password;
    private UserRole role;


}

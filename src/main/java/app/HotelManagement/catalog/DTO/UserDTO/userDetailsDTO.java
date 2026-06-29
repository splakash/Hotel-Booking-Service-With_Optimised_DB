package app.HotelManagement.catalog.DTO.UserDTO;


import app.HotelManagement.catalog.Entity.Enum.AuthProvider;
import app.HotelManagement.catalog.Entity.Enum.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userDetailsDTO {

    private String username;
    private String name;
    private String password;
    private UserRole role;
    private String googleId;
    private AuthProvider authProvider;


}

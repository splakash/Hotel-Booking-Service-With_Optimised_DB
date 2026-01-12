package app.HotelManagement.UserManagement;


import app.HotelManagement.catalog.DTO.userDetailsDTO;
import app.HotelManagement.catalog.Entity.AppUser;
import app.HotelManagement.catalog.Repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AppUserRepo UserRepo;

    public userDetailsDTO registerUser(userDetailsDTO user) {
        if(UserRepo.existsByUsername(user.getUsername()))throw new RuntimeException("User Already Exists");

        AppUser appUser = new AppUser();
        appUser.setUsername(user.getUsername());
        appUser.setPassword(passwordEncoder.encode(user.getPassword())); // hash password
        appUser.setRole(user.getRole());
        UserRepo.save(appUser);
        return user;
    }
}

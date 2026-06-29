package app.HotelManagement.Services;


import app.HotelManagement.catalog.Entity.AppUser;
import app.HotelManagement.catalog.Entity.Enum.AuthProvider;
import app.HotelManagement.catalog.Entity.Enum.UserRole;
import app.HotelManagement.catalog.Repository.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AppUserService {

    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;

    public AppUser findOrCreateGoogleUser(String email,String name, String googleId){
        return appUserRepo.findByUsername(email)
                .map(user->{
                    if(user.getGoogleId()==null){
                        user.setGoogleId(googleId);
                        user.setAuthProvider(AuthProvider.GOOGLE);
                        return appUserRepo.save(user);
                    }
                    return user;
                })
                .orElseGet(()->{
                    AppUser user=new AppUser();
                    user.setUsername(email);
                    user.setName(name);
                    user.setGoogleId(googleId);
                    user.setAuthProvider(AuthProvider.GOOGLE);
                    user.setRole(UserRole.USER);
                    user.setPassword(
                            passwordEncoder.encode(
                                    UUID.randomUUID().toString())
                    );
                    return appUserRepo.save(user);
                });}
}


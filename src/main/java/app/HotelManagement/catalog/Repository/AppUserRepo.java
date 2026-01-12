package app.HotelManagement.catalog.Repository;

import app.HotelManagement.catalog.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser,Long> {

    boolean existsByUsername(String userName);

    AppUser findByUsername(String username);
}

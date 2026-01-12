package app.HotelManagement.UserManagement;


import app.HotelManagement.catalog.DTO.AuthResponse;
import app.HotelManagement.catalog.DTO.userDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/create-user")
    public ResponseEntity<?> create_user(@RequestBody userDetailsDTO user){
        userDetailsDTO dto = authService.registerUser(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody userDetailsDTO userDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword()
                )
        );

        String token = jwtUtil.generateToken(authentication.getName(),userDto.getRole().toString());

        return ResponseEntity.ok(new AuthResponse(token));
    }

}

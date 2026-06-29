package app.HotelManagement.UserManagement;


import app.HotelManagement.Services.AuthService;
import app.HotelManagement.catalog.DTO.TokenDTO;
import app.HotelManagement.catalog.DTO.UserDTO.userDetailsDTO;
import app.HotelManagement.catalog.DTO.UserDTO.userNameDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final  JwtUtil jwtUtil;


    @PostMapping("/create-user")
    public ResponseEntity<?> create_user(@RequestBody userDetailsDTO user){
        userDetailsDTO dto = authService.registerUser(user);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
        Map<String, String> user = new HashMap<>();
        user.put("username", authentication.getName());

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("USER");

        user.put("role", role);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?>resetPassword (@RequestBody userDetailsDTO UpdatedUser){

        return null;
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgotPassword(@RequestBody userNameDTO userName){

        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody userDetailsDTO userDto, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword()
                )
        );

        String token = jwtUtil.generateToken(authentication.getName(),userDto.getRole().toString());
        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(true)            // keep false for local dev (HTTP), true in production
                .sameSite("None")          // use Lax for Dev Lax works when frontend/backend are same host
                .path("/")
                .maxAge(Duration.ofDays(1))
                .build();
        Map<String, String> body = new HashMap<>();
//        body we need to add name
        body.put("username", authentication.getName());
        body.put("role", String.valueOf(userDto.getRole()));
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(body);
    }

    @PostMapping("/extract-userName")
    public ResponseEntity<?> getUserName(@RequestBody TokenDTO token) {
        try {
            String userName = jwtUtil.extractUsername(token.getToken());
            if (userName == null) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
            }
            return ResponseEntity.ok(Map.of("userName", userName));

        } catch (ExpiredJwtException e) {
            // ✅ Return 401 instead of crashing with 500
            return ResponseEntity.status(401).body(Map.of("error", "Token expired"));

        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
    }


}

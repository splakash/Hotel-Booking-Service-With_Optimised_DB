package app.HotelManagement.catalog.Entity;

import app.HotelManagement.catalog.Entity.Enum.AuthProvider;
import app.HotelManagement.catalog.Entity.Enum.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String username;

    @Column(name = "password", nullable = false, length = 120)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role ;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = true)
    private String name;

    private String googleId;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    // ---- Audit ----
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ---- JPA Callbacks ----
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        if (this.role == null) this.role = UserRole.USER;
    }
}

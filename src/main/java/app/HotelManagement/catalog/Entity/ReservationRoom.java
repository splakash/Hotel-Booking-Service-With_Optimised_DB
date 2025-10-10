package app.HotelManagement.catalog.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
        name = "reservation_room",
        indexes = {

                @Index(name = "idx_resroom_reservation", columnList = "reservation_id"),
                @Index(name = "idx_resroom_room", columnList = "room_id")
        }
)
public class ReservationRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---- Relationships ----

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "reservation_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_resroom_reservation")
    )
    private Reservation reservation;

    /**
     * Room is optional at the time of booking.
     * You may assign a room later during check-in.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "room_id",
            foreignKey = @ForeignKey(name = "fk_resroom_room")
    )
    private Room room;

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
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}

package app.HotelManagement.catalog.Entity;

import app.HotelManagement.catalog.Entity.Enum.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "reservation",
        uniqueConstraints = {
        @UniqueConstraint(name = "uk_reservation_code",
                columnNames = "code")},
        indexes = {
                @Index(name = "idx_reservation_status", columnList = "status"),
                @Index(name = "idx_reservation_checkin", columnList = "check_in"),
                @Index(name = "idx_reservation_checkout", columnList = "check_out")
        }

)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    //Relationship property_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false,foreignKey = @ForeignKey(name = "fk_reservation_property"))
    private Property property;

    //Relationship Room_type_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false,foreignKey = @ForeignKey(name = "fk_reservation_room_type"))
    private RoomType roomtype;

    @OneToMany(mappedBy = "reservation")
    private List<Payment> payments = new ArrayList<>();

    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;

    @Column(name = "check_out", nullable = false)
    private  LocalDate checkOut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private ReservationStatus status = ReservationStatus.PENDING_PAYMENT;


    @Column(name = "guest_adults")
    private  int guestAdult;


    @Column(name = "guest_children")
    private int guestChildren;

    @Column(name = "total_amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalAmount;
    //private currency
    @Column(name = "contact_name", length = 150)
    private String contactName;

    @Column(name = "contact_email", length = 150)
    private String contactEmail;

    @Column(name = "contact_phone", length = 30)
    private String contactPhone;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() { this.createdAt = LocalDateTime.now(); this.updatedAt = this.createdAt; }

    @PreUpdate
    public void onUpdate() { this.updatedAt = LocalDateTime.now(); }

}

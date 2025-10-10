package app.HotelManagement.catalog.Entity;

import app.HotelManagement.catalog.Entity.Enum.PaymentMethod;
import app.HotelManagement.catalog.Entity.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payment",
        indexes = {
                @Index(name = "idx_payment_reservation", columnList = "reservation_id"),
                @Index(name = "idx_payment_status", columnList = "status")
        }
)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "reservation_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_payment_reservation")
    )
    private Reservation reservation;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency = "INR";

    @Column(length = 30)
    private String provider;           // e.g., RAZORPAY, STRIPE

    @Column(name = "intent_id", length = 120)
    private String intentId;           // gateway payment/intent id

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status = PaymentStatus.INITIATED;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private PaymentMethod method;      // CARD / UPI / NETBANKING / CASH, etc.

    @Column(name = "captured_at")
    private LocalDateTime capturedAt;

    @Column(name = "refund_amount", precision = 12, scale = 2)
    private BigDecimal  refundAmount;

    // ---- Audit ----
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

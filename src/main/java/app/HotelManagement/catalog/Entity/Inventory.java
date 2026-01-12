package app.HotelManagement.catalog.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "inventory",
        uniqueConstraints = {
        @UniqueConstraint(name = "uk_inventory_property_roomType_date",columnNames = {
                "property_id, room_type_id,date"
        })
        },
        indexes = {
                @Index(name = "idx_inventory_prop_roomtype", columnList = "property_id, room_type_id"),
                @Index(name = "idx_inventory_date", columnList = "date")

        }
)
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Relationship property_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_inventory_property")
    )
    private Property property;
    //Relationship Room_type_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_inventory_room_type")
    )
    private RoomType roomtype;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "reserved_rooms",nullable = false)
    private Integer reservedRooms;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        if (reservedRooms == null) reservedRooms = 0;

    }
    @PreUpdate
    public void onUpdate() { this.updatedAt = LocalDateTime.now(); }
    @Transient
    public int getAvailableRooms() {
        return Math.max(
                0,
                roomtype.getTotalRooms() - reservedRooms
        );
    }
}

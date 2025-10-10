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
public class inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relationship property_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_inventory_property")
    )
    private property property;

    //Relationship Room_type_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_inventory_room_type")
    )
    private roomType roomtype;



    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "total_rooms",nullable = false)
    private Integer totalRooms;

    @Column(name = "reserved_rooms",nullable = false)
    private Integer reservedRooms;

    @Column(name = "OOO_rooms",nullable = false)
    private Integer OutOfOrderRooms;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        if (reservedRooms == null) reservedRooms = 0;
        if (OutOfOrderRooms == null) OutOfOrderRooms = 0;
    }


    @PreUpdate
    public void onUpdate() { this.updatedAt = LocalDateTime.now(); }
    @Transient
    public int getAvialableRooms(){
        int total = totalRooms == null ? 0:  totalRooms;
        int reserved = reservedRooms == null ? 0 : reservedRooms;
        int ooo = OutOfOrderRooms == null ? 0 : OutOfOrderRooms;
        int availableRoom = total - reserved - ooo;

        return Math.max(0,availableRoom);
    }

}

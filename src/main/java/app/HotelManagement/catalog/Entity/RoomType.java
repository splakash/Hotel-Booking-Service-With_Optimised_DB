package app.HotelManagement.catalog.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "room_type",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_room_type_property_code", columnNames = {"property_id", "code"})
        })
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relationship
    @ManyToOne
    @JoinColumn(name = "propert_id")
    private Property property;

    @OneToMany(mappedBy = "roomtype")
    private List<Inventory> inventories;

    @OneToMany(mappedBy = "roomtype") //mapped by name should be as it is as it is in declared class
    private List<Reservation> reservations;



    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "base_price", nullable = true)
    private Double basePrice;
//    @Column(name = "AirConditionar")
//    private boolean airCondition;
//
//    @Column(name = "wifiService")
//    private boolean wifi;
@Column(name = "total_rooms",nullable = false)
private Integer totalRooms;
    @Column(name = "occupancy_adults", nullable = false)
    private Integer occupancyAdults = 2;

    @Column(name = "occupancy_children", nullable = false)
    private Integer occupancyChildren = 2;

    @Column(columnDefinition = "TEXT")
    private String description;


    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }


    @PreUpdate
    public void onUpdate() { this.updatedAt = LocalDateTime.now(); }

}

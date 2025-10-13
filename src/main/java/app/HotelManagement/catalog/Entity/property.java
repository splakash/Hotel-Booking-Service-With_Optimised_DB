package app.HotelManagement.catalog.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "property")
public class property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String timezone;

    private String address;

    @Column(name ="contact_email"  )
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



    //Relationship rooms
    @OneToMany(mappedBy = "property")
    private List<Room> rooms;

    @OneToMany(mappedBy = "property")
    private List<inventory> inventories;

    @OneToMany(mappedBy = "property")
    private List<Reservation> reservations;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }


    @PreUpdate
    public void onUpdate() { this.updatedAt = LocalDateTime.now(); }

}

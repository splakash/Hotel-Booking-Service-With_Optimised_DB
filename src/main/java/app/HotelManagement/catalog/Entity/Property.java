package app.HotelManagement.catalog.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "property")
public class Property {

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


    @OneToMany(mappedBy = "property")
    private List<Inventory> inventories;

    @OneToMany(mappedBy = "property")
    private List<Reservation> reservations = new ArrayList<>();


//    //inverse Side does not contain foreign key details
//    @OneToMany(mappedBy = "property")
//    private List<Room> rooms = new ArrayList<>();

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }


    @PreUpdate
    public void onUpdate() { this.updatedAt = LocalDateTime.now(); }

}

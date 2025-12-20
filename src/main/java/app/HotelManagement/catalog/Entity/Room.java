package app.HotelManagement.catalog.Entity;


import app.HotelManagement.catalog.Entity.Enum.RoomStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@Table(name = "room",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_", columnNames = {"property_id","RoomNumber"}

        )
    },indexes = {
        @Index(name = "property_room_type",columnList = "property_id,room_type_id")
}
)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relationship property_id
    //Owning Side it contains foreign key
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    //Relationship Room_type_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomtype;

    @OneToOne(mappedBy = "room")
    @JoinColumn(name="Reservation_Room_id")
    private ReservationRoom reservationRoom;

    @Column(nullable = false,length = 50)
    private String roomNumber;

    @Column(length = 32)
    private int floor;




    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private RoomStatus status = RoomStatus.CLEAN;


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

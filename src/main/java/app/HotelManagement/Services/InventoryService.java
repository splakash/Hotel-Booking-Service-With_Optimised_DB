package app.HotelManagement.Services;


import app.HotelManagement.ExceptionHandler.InventoryNotFoundException;
import app.HotelManagement.ExceptionHandler.InventoryUnavailableException;
import app.HotelManagement.catalog.Entity.Inventory;
import app.HotelManagement.catalog.Entity.Property;
import app.HotelManagement.catalog.Entity.Reservation;
import app.HotelManagement.catalog.Entity.RoomType;
import app.HotelManagement.catalog.Repository.InventoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Service
public class InventoryService {
    private final InventoryRepo inventoryRepo;

    public InventoryService(
            InventoryRepo inventoryRepo) {

        this.inventoryRepo = inventoryRepo;
    }

    public void holdInventory(
            Property property,
            RoomType roomType,
            LocalDate checkIn,
            LocalDate checkOut) {

        LocalDate date = checkIn;

        while(date.isBefore(checkOut)) {

            Inventory inventory =
                    inventoryRepo.findWithLock(
                                    property,
                                    roomType,
                                    date)
                            .orElseThrow(
                                    InventoryNotFoundException::new);

            if(inventory.getAvailableRooms() <= 0) {
                throw new InventoryUnavailableException();
            }

            inventory.setHeldRooms(
                    inventory.getHeldRooms() + 1);

            date = date.plusDays(1);
        }
    }


    public void releaseInventory(Reservation reservation){


        LocalDate date = reservation.getCheckIn();
        while (date.isBefore(reservation.getCheckOut())) {

            Inventory inventory = inventoryRepo
                    .findWithLock(reservation.getProperty(), reservation.getRoomtype(), date)
                    .orElseThrow(() -> new RuntimeException("Inventory not found"));

            inventory.setHeldRooms(inventory.getHeldRooms() - 1);

            inventoryRepo.save(inventory);

            date = date.plusDays(1);
        }
    }

    public void confirmInventory(
            Reservation reservation) {

        LocalDate date = reservation.getCheckIn();

        while (date.isBefore(reservation.getCheckOut())) {

            Inventory inventory =
                    inventoryRepo.findWithLock(
                                    reservation.getProperty(),
                                    reservation.getRoomtype(),
                                    date)
                            .orElseThrow(
                                    InventoryNotFoundException::new);

            inventory.setHeldRooms(
                    inventory.getHeldRooms() - 1);

            inventory.setReservedRooms(
                    inventory.getReservedRooms() + 1);

            date = date.plusDays(1);
        }
    }
}
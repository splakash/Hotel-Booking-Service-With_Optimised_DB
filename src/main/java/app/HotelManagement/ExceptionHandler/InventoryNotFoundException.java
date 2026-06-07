package app.HotelManagement.ExceptionHandler;

public class InventoryNotFoundException
        extends RuntimeException {

    public InventoryNotFoundException() {
        super("Inventory not found");
    }
}




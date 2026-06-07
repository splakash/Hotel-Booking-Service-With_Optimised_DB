package app.HotelManagement.ExceptionHandler;

public class InventoryUnavailableException
        extends RuntimeException {

    public InventoryUnavailableException() {
        super("Room unavailable");
    }
}

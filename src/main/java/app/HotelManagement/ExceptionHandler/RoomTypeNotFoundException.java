package app.HotelManagement.ExceptionHandler;

public class RoomTypeNotFoundException extends RuntimeException{
    public RoomTypeNotFoundException() {
        super("Room unavailable");
    }
}

package app.HotelManagement.catalog.DTO;

public interface PropertyDetailsProjection {

    Long getPropertyId();

    String getPropertyName();

    String getCity();
    String getState();
    String getCountry();

    String getContactEmail();

    String getContactPhone();

    Double getRatings();

    Long getRoomTypeId();

    String getRoomTypeDescription();

    Double getBasePrice();

    Integer getTotalRooms();

    Integer getOccupancyAdults();

    Integer getOccupancyChildren();

    Integer getAvailableRooms();
}
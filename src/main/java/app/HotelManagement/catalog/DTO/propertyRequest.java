package app.HotelManagement.catalog.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class propertyRequest {
        @NotBlank
        @Size(max = 150)
        private String name;

        @NotBlank @Size(max = 100)
        private String timezone;

        // store full address as JSON/text for now (or use typed object later)
        @Size(max = 2000)
        private String address;

        @Size(max = 150)
        private String contactEmail;

        @Size(max = 30)
        private String contactPhone;
    }

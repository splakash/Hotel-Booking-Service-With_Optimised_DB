package app.HotelManagement;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SelfPingScheduler {

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10 * 60 * 1000) // every 10 minutes
    public void keepAlive() {
        try {
            restTemplate.getForObject(
                    "https://hotel-booking-service-rgs2.onrender.com/hello",
                    String.class
            );
        } catch (Exception e) {
            // ignore errors
        }
    }
}


package app.HotelManagement.catalog.Entity.Chatbot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FAQsRequest {
    String question;
    String answer;
    String category;
    String keywords;
    Integer priority;
}

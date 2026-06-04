package app.HotelManagement.catalog.Entity.Chatbot;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeminiRequest {

    private String model;
    private List<Message> messages;



    public static class Message {
        private String role;
        private String content;


    }
}
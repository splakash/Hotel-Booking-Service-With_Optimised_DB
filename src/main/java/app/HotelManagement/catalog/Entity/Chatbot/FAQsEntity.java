package app.HotelManagement.catalog.Entity.Chatbot;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FAQsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @Column(length = 2000)
    private String answer;

    private String category;

    @Column(length = 1000)
    private String keywords; // comma-separated keywords

    private Integer priority;
}

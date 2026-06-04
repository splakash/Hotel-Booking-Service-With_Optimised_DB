package app.HotelManagement.catalog.Repository.Chatbot;

import app.HotelManagement.catalog.Entity.Chatbot.FAQsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQsRepo extends JpaRepository<FAQsEntity,Long> {
}

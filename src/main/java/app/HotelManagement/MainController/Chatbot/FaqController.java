package app.HotelManagement.MainController.Chatbot;


import app.HotelManagement.Services.Chatbot.FaqService;
import app.HotelManagement.catalog.Entity.Chatbot.FAQsEntity;
import app.HotelManagement.catalog.Entity.Chatbot.FAQsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class FaqController {

    @Autowired
    public FaqService faqService;

    @GetMapping("/faqs")
    public List<FAQsEntity> getFaqs(){
        return faqService.getFaqsService();
    }

    @PostMapping("/add/faqs")
    public FAQsEntity addFaqs(@RequestBody FAQsRequest faQsEntity){
          return faqService.addFaqsService(faQsEntity);

    }

}

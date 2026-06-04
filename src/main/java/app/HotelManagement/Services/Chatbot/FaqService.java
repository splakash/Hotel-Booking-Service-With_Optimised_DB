package app.HotelManagement.Services.Chatbot;

import app.HotelManagement.catalog.Entity.Chatbot.FAQsEntity;
import app.HotelManagement.catalog.Entity.Chatbot.FAQsRequest;
import app.HotelManagement.catalog.Repository.Chatbot.FAQsRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class FaqService {

    private final FAQsRepo faQsRepo;

    public FaqService(FAQsRepo faQsRepo){
        this.faQsRepo = faQsRepo;
    }


    public List<FAQsEntity> getFaqsService(){
        return faQsRepo.findAll();
    }


    public FAQsEntity addFaqsService(FAQsRequest faQsRequest) {
        System.out.println(faQsRequest);
        FAQsEntity faQsEntity = new FAQsEntity();
        faQsEntity.setQuestion(faQsRequest.getQuestion());
        faQsEntity.setCategory(faQsRequest.getCategory());
        faQsEntity.setAnswer(faQsRequest.getAnswer());
        faQsEntity.setPriority(faQsEntity.getPriority());
        faQsEntity.setKeywords(faQsRequest.getKeywords());
        return faQsRepo.save(faQsEntity);

    }
}

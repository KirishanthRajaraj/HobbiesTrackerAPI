package com.kiri.hobby_tracker.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiri.hobby_tracker.Service.GoogleService;

@CrossOrigin
@RestController
public class GoogleAIController {

    private final GoogleService googleService;

    public GoogleAIController(GoogleService googleService) {
        this.googleService = googleService;
    }

    @GetMapping("/getGoogleAIResponse")
    public String getGoogleAIResponse() {
        try {
            String res = googleService.GoogleGenerativeAIAPIReq("Testquery");
            return res;
        } catch (Exception e) {
            System.out.println("Error in fetching google AI API Response: " + e);
        }
        return "Error in fetching AI Response";
    }
}

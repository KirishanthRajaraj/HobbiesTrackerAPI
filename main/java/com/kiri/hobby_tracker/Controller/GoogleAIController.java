package com.kiri.hobby_tracker.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiri.hobby_tracker.Service.GoogleService;

@CrossOrigin
@RestController
public class GoogleAIController {

    private final GoogleService googleService;

    public GoogleAIController(GoogleService googleService) {
        this.googleService = googleService;
    }

    @PostMapping("/getGoogleAIResponse/")
    public String getGoogleAIResponse(@RequestBody String prompt) {
        try {
            String res = googleService.GoogleGenerativeAIAPIReq(prompt);
            return res;
        } catch (Exception e) {
            System.out.println("Error in fetching google AI API Response: " + e);
        }
        return "Error in fetching AI Response";
    }
}

package com.kiri.hobby_tracker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HobbiesController {
    @GetMapping("/getHobbies")
    public String getHobbies() {
        return "Reading, Hiking, Cooking, Painting";
    }
}

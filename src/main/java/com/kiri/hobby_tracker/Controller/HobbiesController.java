package com.kiri.hobby_tracker.Controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiri.hobby_tracker.Model.Hobby;
import com.kiri.hobby_tracker.Service.HobbiesService;



@CrossOrigin
@RestController
public class HobbiesController {
    private final HobbiesService hobbiesService;
    public HobbiesController(HobbiesService hobbiesService) {
        this.hobbiesService = hobbiesService;
    }

    @GetMapping("/getAllHobbies")
    public List<Hobby> getHobbies() {
        var hobbies = hobbiesService.getAllHobbies();
        return hobbies;
    }

    @PostMapping("/addHobby")
    public Hobby addHobby(@RequestBody Hobby hobby) {
        return hobbiesService.addHobby(hobby);
    }

    @PutMapping("/editHobby/{id}")
    public Hobby editHobby(@RequestBody Hobby hobby, @PathVariable Long id) {
        return hobbiesService.editHobby(id, hobby);
    }

    @DeleteMapping("/deleteHobby/{id}")
    public ResponseEntity<Void> deleteHobby(@PathVariable Long id) {
        return hobbiesService.deleteHobby(id);
    }
}

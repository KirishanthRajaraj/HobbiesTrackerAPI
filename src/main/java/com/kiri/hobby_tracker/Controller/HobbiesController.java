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
import com.kiri.hobby_tracker.Model.HobbyDTO;
import com.kiri.hobby_tracker.Model.HobbyDatesDTO;
import com.kiri.hobby_tracker.Service.HobbiesService;

@CrossOrigin
@RestController
public class HobbiesController {

    private final HobbiesService hobbiesService;

    public HobbiesController(HobbiesService hobbiesService) {
        this.hobbiesService = hobbiesService;
    }

    @GetMapping("/getAllHobbies")
    public List<HobbyDTO> getAllHobbies() {
        try {
            var hobbies = hobbiesService.getAllHobbies();
            return hobbies;
        } catch (Exception e) {
            System.out.println("Error fetching hobbies: " + e.getMessage());
        }
        return List.of();
    }

    @PostMapping("/addHobby")
    public Hobby addHobby(@RequestBody HobbyDTO hobby) {
        return hobbiesService.addHobby(hobby);
    }

    @PutMapping("/editHobby/{id}")
    public Hobby editHobby(@RequestBody HobbyDTO hobby, @PathVariable Long id) {
        try {
            Hobby hobbyToReturn = hobbiesService.editHobby(id, hobby);
            return hobbyToReturn;
        } catch (Exception e) {
            System.out.println("Error fetching hobbies: " + e.getMessage());
        }
        return new Hobby();
    }

    @PutMapping("/updateHobbyDate/")
    public ResponseEntity<Void> updateHobbyDate(@RequestBody HobbyDatesDTO hobbyDate) {
        try {
            hobbiesService.updateHobbyDate(hobbyDate);
        } catch (Exception e) {
            System.out.println("Error fetching hobbies: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllHobbyDates/")
    public List<HobbyDatesDTO> getAllHobbyDates() {
        try {
            return hobbiesService.getAllHobbyDates();
        } catch (Exception e) {
            System.out.println("Error fetching hobbies: " + e.getMessage());
        }
        return null;
    }

    @DeleteMapping("/deleteHobby/{id}")
    public ResponseEntity<Void> deleteHobby(@PathVariable Long id) {
        return hobbiesService.deleteHobby(id);
    }
}

package com.kiri.hobby_tracker.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiri.hobby_tracker.Model.Hobby;
import com.kiri.hobby_tracker.Model.HobbyDTO;
import com.kiri.hobby_tracker.Model.HobbyDatesDTO;
import com.kiri.hobby_tracker.Service.HobbiesService;

import jakarta.persistence.EntityNotFoundException;

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

    @GetMapping("/getHobbyById/{id}")
    public HobbyDTO getHobbyById(@PathVariable Long id) {
        try {
            var hobbies = hobbiesService.getHobbyById(id);
            return hobbies;
        } catch (Exception e) {
            System.out.println("Error fetching hobbies: " + e.getMessage());
        }
        return null;
    }

    @PostMapping("/addHobby")
    public Hobby addHobby(@RequestBody HobbyDTO hobby) {
        return hobbiesService.addHobby(hobby);
    }

    @PutMapping("/editHobby/{id}")
    public ResponseEntity<Hobby> editHobby(@RequestBody HobbyDTO hobby, @PathVariable Long id) {
        try {
            Hobby hobbyToReturn = hobbiesService.editHobby(id, hobby);
            return ResponseEntity.ok(hobbyToReturn);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    @PatchMapping("/patchHobby/{id}")
    public ResponseEntity<Hobby> patchHobby(@RequestBody HobbyDTO hobby, @PathVariable Long id) {
        try {
            Hobby updated = hobbiesService.patchHobby(id, hobby);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    @PutMapping("/updateHobbyDate/")
    public HobbyDatesDTO updateHobbyDate(@RequestBody HobbyDatesDTO hobbyDate) {
        HobbyDatesDTO updatedHobbyDate = null;
        try {
            updatedHobbyDate = hobbiesService.updateHobbyDate(hobbyDate);
        } catch (Exception e) {
            System.out.println("Error fetching hobbies: " + e.getMessage());
        }
        return updatedHobbyDate;
    }

    @DeleteMapping("/removeHobbyDate/")
    public ResponseEntity<Void> removeHobbyDate(@RequestBody HobbyDatesDTO hobbyDate) {
        try {
            hobbiesService.removeHobbyDate(hobbyDate);
        } catch (Exception e) {
            System.out.println("Error fetching hobbies: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    /*@PutMapping("/updateIntervalHobbyDate/")
    public ResponseEntity<Void> updateIntervalHobbyDate(@RequestBody HobbyDatesDTO hobbyDate) {
        try {
            hobbiesService.updateIntervalHobbyDate(hobbyDate);
        } catch (Exception e) {
            System.out.println("Error fetching hobbies: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/removeIntervalHobbyDate/")
    public ResponseEntity<Void> removeIntervalHobbyDate(@RequestBody HobbyDatesDTO hobbyDate) {
        try {
            hobbiesService.removeIntervalHobbyDate(hobbyDate);
        } catch (Exception e) {
            System.out.println("Error fetching hobbies: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }*/
    @PutMapping("/updateHobbyPoints/{hobbyId}")
    public Integer updateHobbyPoints(@PathVariable Long hobbyId) {
        Integer updatedPoints = null;
        try {
            updatedPoints = hobbiesService.updateHobbyPoints(hobbyId);
        } catch (Exception e) {
            System.out.println("Error fetching hobbies: " + e.getMessage());
        }
        return updatedPoints;
    }

    @DeleteMapping("/removeHobbyPoints/{hobbyId}")
    public Integer removeHobbyPoints(@PathVariable Long hobbyId) {
        Integer updatedPoints = null;
        try {
            updatedPoints = hobbiesService.removeHobbyPoints(hobbyId);
        } catch (Exception e) {
            System.out.println("Error fetching hobbies: " + e.getMessage());
        }
        return updatedPoints;
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

    @GetMapping("/getHobbyDatesByHobbyId/{hobbyId}")
    public List<HobbyDatesDTO> getHobbyDatesByHobbyId(@PathVariable Long hobbyId) {
        try {
            return hobbiesService.getHobbyDatesByHobbyId(hobbyId);
        } catch (Exception e) {
            System.out.println("Error fetching hobbies: " + e.getMessage());
        }
        return null;
    }

    /*@GetMapping("/getHobbyDatesByHobbyId/{hobbyId}")
    public List<Hobby> getIntervalDatesByHobbyId(@PathVariable Long hobbyId) {
        try {
            return hobbiesService.getIntervalDatesByHobbyId(hobbyId);
        } catch (Exception e) {
            System.out.println("Error fetching hobbies: " + e.getMessage());
        }
        return null;
    }*/
    @DeleteMapping("/deleteHobby/{id}")
    public ResponseEntity<Void> deleteHobby(@PathVariable Long id) {
        return hobbiesService.deleteHobby(id);
    }
}

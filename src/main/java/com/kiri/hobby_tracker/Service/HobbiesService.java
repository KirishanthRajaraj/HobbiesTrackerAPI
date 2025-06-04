package com.kiri.hobby_tracker.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kiri.hobby_tracker.Model.Hobby;
import com.kiri.hobby_tracker.Repository.IHobbiesRepository;

@Service
public class HobbiesService {
    private final IHobbiesRepository hobbyRepository;

    public HobbiesService(IHobbiesRepository hobbyRepository) {
        this.hobbyRepository = hobbyRepository;
    }
    public List<Hobby> getAllHobbies() {
        return hobbyRepository.findAll();
    }
    public Hobby addHobby(Hobby hobby) {
        hobby.setId(null);
        return hobbyRepository.save(hobby);
    }

    public Hobby editHobby(long id, Hobby hobby) {
        Hobby existing = hobbyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hobby not found"));
        existing.setName(hobby.getName());
        existing.setDescription(hobby.getDescription());

        return hobbyRepository.save(existing);
    }

    public ResponseEntity<Void> deleteHobby(long id) {
        if (!hobbyRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        hobbyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

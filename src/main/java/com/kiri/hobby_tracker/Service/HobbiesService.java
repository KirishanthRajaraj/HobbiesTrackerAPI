package com.kiri.hobby_tracker.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.kiri.hobby_tracker.Model.Category;
import com.kiri.hobby_tracker.Model.CategoryDTO;
import com.kiri.hobby_tracker.Model.Hobby;
import com.kiri.hobby_tracker.Model.HobbyDTO;
import com.kiri.hobby_tracker.Model.HobbyDates;
import com.kiri.hobby_tracker.Model.HobbyDatesDTO;
import com.kiri.hobby_tracker.Model.Minuspoint;
import com.kiri.hobby_tracker.Model.Pluspoint;
import com.kiri.hobby_tracker.Model.PointsDTO;
import com.kiri.hobby_tracker.Repository.ICategoriesRepository;
import com.kiri.hobby_tracker.Repository.IHobbiesRepository;
import com.kiri.hobby_tracker.Repository.IHobbyDatesRepository;
import com.kiri.hobby_tracker.Repository.IMinuspointRepository;
import com.kiri.hobby_tracker.Repository.IPluspointRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class HobbiesService {

    private final IHobbiesRepository hobbyRepository;
    private final ICategoriesRepository categoriesRepository;
    private final IMinuspointRepository minuspointRepository;
    private final IPluspointRepository pluspointRepository;
    private final IHobbyDatesRepository hobbyDatesRepository;

    public HobbiesService(IHobbiesRepository hobbyRepository,
            IMinuspointRepository minuspointRepository,
            IPluspointRepository pluspointRepository,
            ICategoriesRepository categoryRepository,
            IHobbyDatesRepository hobbyDatesRepository) {
        this.hobbyRepository = hobbyRepository;
        this.minuspointRepository = minuspointRepository;
        this.pluspointRepository = pluspointRepository;
        this.categoriesRepository = categoryRepository;
        this.hobbyDatesRepository = hobbyDatesRepository;
    }

    public List<HobbyDTO> getAllHobbies() {
        List<Hobby> hobbies = hobbyRepository.findAll();
        List<HobbyDTO> hobbyDTOs = new ArrayList<>();
        for (Hobby hobby : hobbies) {
            HobbyDTO hobbyDTO = new HobbyDTO();
            hobbyDTO.setId(hobby.getId());
            hobbyDTO.setName(hobby.getName());
            hobbyDTO.setDescription(hobby.getDescription());
            hobbyDTO.setEffortLevel(hobby.getEffortLevel());
            hobbyDTO.setInterestLevel(hobby.getInterestLevel());

            List<PointsDTO> pluspoints = new ArrayList<>();
            for (Pluspoint pp : hobby.getPluspoints()) {
                PointsDTO ppDTO = new PointsDTO();
                ppDTO.setId(pp.getId());
                ppDTO.setText(pp.getText());
                ppDTO.setHobbyId(hobby.getId());
                pluspoints.add(ppDTO);
            }
            hobbyDTO.setPluspoints(pluspoints);

            List<PointsDTO> minuspoints = new ArrayList<>();
            for (Minuspoint mp : hobby.getMinuspoints()) {
                PointsDTO mpDTO = new PointsDTO();
                mpDTO.setId(mp.getId());
                mpDTO.setText(mp.getText());
                mpDTO.setHobbyId(hobby.getId());
                minuspoints.add(mpDTO);
            }
            hobbyDTO.setMinuspoints(minuspoints);

            List<CategoryDTO> categories = new ArrayList<>();
            for (Category category : hobby.getCategories()) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(category.getId());
                categoryDTO.setName(category.getName());
                categories.add(categoryDTO);
            }
            hobbyDTO.setCategories(categories);

            hobbyDTOs.add(hobbyDTO);
        }

        return hobbyDTOs;
    }

    public Hobby addHobby(HobbyDTO hobbyDTO) {
        Hobby newHobby = new Hobby();
        newHobby.setName(hobbyDTO.getName());
        newHobby.setDescription(hobbyDTO.getDescription());
        newHobby.setEffortLevel(hobbyDTO.getEffortLevel());
        newHobby.setInterestLevel(hobbyDTO.getInterestLevel());

        List<Minuspoint> minuspoints = new ArrayList<>();
        if (hobbyDTO.getMinuspoints() != null) {
            for (PointsDTO mp : hobbyDTO.getMinuspoints()) {
                Minuspoint ms = new Minuspoint();
                ms.setText(mp.getText());
                ms.setHobby(newHobby);
                minuspoints.add(ms);
            }
        }

        List<Pluspoint> pluspoints = new ArrayList<>();
        if (hobbyDTO.getPluspoints() != null) {
            for (PointsDTO pp : hobbyDTO.getPluspoints()) {
                Pluspoint ps = new Pluspoint();
                ps.setText(pp.getText());
                ps.setHobby(newHobby);
                pluspoints.add(ps);
            }
        }

        newHobby.setPluspoints(pluspoints);
        newHobby.setMinuspoints(minuspoints);

        // Update categories
        Set<Category> newCategories = new HashSet<>();

        if (hobbyDTO.getCategories() != null) {
            for (CategoryDTO categoryDTO : hobbyDTO.getCategories()) {
                Category cat = categoriesRepository.findById(categoryDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryDTO.getId()));
                newCategories.add(cat);
            }
        }

        newHobby.getCategories().clear();
        newHobby.getCategories().addAll(newCategories);

        return hobbyRepository.save(newHobby);
    }

    @Transactional
    public Hobby editHobby(long id, HobbyDTO hobbyDTO) {
        Hobby existingHobby = hobbyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hobby not found"));

        existingHobby.setName(hobbyDTO.getName());
        existingHobby.setDescription(hobbyDTO.getDescription());
        existingHobby.setEffortLevel(hobbyDTO.getEffortLevel());
        existingHobby.setInterestLevel(hobbyDTO.getInterestLevel());

        // Update minuspoints
        existingHobby.getMinuspoints().clear();
        if (hobbyDTO.getMinuspoints() != null) {
            for (PointsDTO mp : hobbyDTO.getMinuspoints()) {
                Minuspoint ms = new Minuspoint();
                ms.setText(mp.getText());
                ms.setHobby(existingHobby);
                existingHobby.getMinuspoints().add(ms);
            }
        }

        // Update pluspoints
        existingHobby.getPluspoints().clear();
        if (hobbyDTO.getPluspoints() != null) {
            for (PointsDTO pp : hobbyDTO.getPluspoints()) {
                Pluspoint ps = new Pluspoint();
                ps.setText(pp.getText());
                ps.setHobby(existingHobby);
                existingHobby.getPluspoints().add(ps);
            }
        }

        // Update categories
        Set<Category> newCategories = new HashSet<>();

        if (hobbyDTO.getCategories() != null) {
            for (CategoryDTO categoryDTO : hobbyDTO.getCategories()) {
                Category cat = categoriesRepository.findById(categoryDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryDTO.getId()));
                newCategories.add(cat);
            }
        }

        existingHobby.getCategories().clear();
        existingHobby.getCategories().addAll(newCategories);

        for (Category category : newCategories) {
            category.getHobbies().add(existingHobby);
        }

        return hobbyRepository.save(existingHobby);
    }

    @Transactional
    @Async
    public CompletableFuture<Void> updateHobbyDate(HobbyDatesDTO newHobbyDateDTO) {

        Hobby existingHobby = hobbyRepository.findById(newHobbyDateDTO.getHobbyId())
                .orElseThrow(() -> new RuntimeException("Hobby not found"));

        HobbyDates newHobbyDate = hobbyDatesRepository.findById(newHobbyDateDTO.getId())
                .orElseGet(() -> {
                    HobbyDates hd = new HobbyDates();
                    hd.setDate(newHobbyDateDTO.getDate());
                    hd.setHobby(existingHobby);
                    return hd;
                });

        List<HobbyDates> existingDates = existingHobby.getDates();

        // loop through hobbydates
        // determine if the date already exists for the existing hobby
        // if it does, update the date
        // if it does not, add the date to the existing hobby
        if (newHobbyDate.getId() != null) {
            // Try to find existing HobbyDates by id
            Optional<HobbyDates> existingDateOpt = existingDates.stream()
                    .filter(d -> d.getId().equals(newHobbyDate.getId()))
                    .findFirst();

            if (existingDateOpt.isPresent()) {
                // Update the existing entity fields
                HobbyDates existingDate = existingDateOpt.get();
                existingDate.setDate(newHobbyDate.getDate());
                // update other fields as needed
            } else {
                // id present but not found in existing list - treat as new
                newHobbyDate.setHobby(existingHobby);
                existingDates.add(newHobbyDate);
            }
        } else {
            // new HobbyDates (no id)
            newHobbyDate.setHobby(existingHobby);
            existingDates.add(newHobbyDate);
        }

        hobbyRepository.save(existingHobby);
        return CompletableFuture.completedFuture(null);
    }

    public ResponseEntity<Void> deleteHobby(long id) {
        if (!hobbyRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        hobbyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<HobbyDatesDTO> getAllHobbyDates() {
        List<HobbyDates> hds = hobbyDatesRepository.findAll();
        List<HobbyDatesDTO> hdsDTO = new ArrayList<>();
        for (HobbyDates hd : hds) {
            HobbyDatesDTO hdDTO = new HobbyDatesDTO();
            hdDTO.setDate(hd.getDate());
            hdDTO.setHobbyId(hd.getHobby().getId());
            hdDTO.setId(hd.getId());
            hdsDTO.add(hdDTO);
        }
        return hdsDTO;
    }

    @Transactional
    @Async
    public CompletableFuture<Void> removeHobbyDate(HobbyDatesDTO hobbyDateDTO) {
        HobbyDates hobbyDate = hobbyDatesRepository.findById(hobbyDateDTO.getId())
                .orElseThrow(() -> new RuntimeException("Hobby date not found"));

        hobbyDatesRepository.delete(hobbyDate);
        return CompletableFuture.completedFuture(null);
    }
}
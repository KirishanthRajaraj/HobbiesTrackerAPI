package com.kiri.hobby_tracker.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kiri.hobby_tracker.Model.Category;
import com.kiri.hobby_tracker.Model.CategoryDTO;
import com.kiri.hobby_tracker.Model.Hobby;
import com.kiri.hobby_tracker.Model.HobbyDTO;
import com.kiri.hobby_tracker.Model.Minuspoint;
import com.kiri.hobby_tracker.Model.Pluspoint;
import com.kiri.hobby_tracker.Model.PointsDTO;
import com.kiri.hobby_tracker.Repository.ICategoriesRepository;
import com.kiri.hobby_tracker.Repository.IHobbiesRepository;
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

    public HobbiesService(IHobbiesRepository hobbyRepository,
            IMinuspointRepository minuspointRepository,
            IPluspointRepository pluspointRepository,
            ICategoriesRepository categoryRepository) {
        this.hobbyRepository = hobbyRepository;
        this.minuspointRepository = minuspointRepository;
        this.pluspointRepository = pluspointRepository;
        this.categoriesRepository = categoryRepository;
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
        Hobby existing = hobbyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hobby not found"));

        existing.setName(hobbyDTO.getName());
        existing.setDescription(hobbyDTO.getDescription());
        existing.setEffortLevel(hobbyDTO.getEffortLevel());
        existing.setInterestLevel(hobbyDTO.getInterestLevel());

        // Update minuspoints
        existing.getMinuspoints().clear();
        if (hobbyDTO.getMinuspoints() != null) {
            for (PointsDTO mp : hobbyDTO.getMinuspoints()) {
                Minuspoint ms = new Minuspoint();
                ms.setText(mp.getText());
                ms.setHobby(existing);
                existing.getMinuspoints().add(ms);
            }
        }

        // Update pluspoints
        existing.getPluspoints().clear();
        if (hobbyDTO.getPluspoints() != null) {
            for (PointsDTO pp : hobbyDTO.getPluspoints()) {
                Pluspoint ps = new Pluspoint();
                ps.setText(pp.getText());
                ps.setHobby(existing);
                existing.getPluspoints().add(ps);
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
        
        existing.getCategories().clear();
        existing.getCategories().addAll(newCategories);
        
        for (Category category : newCategories) {
            category.getHobbies().add(existing);
        }
        
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

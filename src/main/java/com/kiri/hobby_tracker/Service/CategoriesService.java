package com.kiri.hobby_tracker.Service;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiri.hobby_tracker.Model.Category;
import com.kiri.hobby_tracker.Model.CategoryDTO;
import com.kiri.hobby_tracker.Model.Hobby;
import com.kiri.hobby_tracker.Repository.ICategoriesRepository;

@Service
public class CategoriesService {

    private final ICategoriesRepository categoriesRepository;

    public CategoriesService(ICategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoriesRepository.findAll();
        List<CategoryDTO> dtos = new ArrayList<>();

        for (Category category : categories) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(category.getId());
            dto.setName(category.getName());

            Set<Hobby> hobbies = new HashSet<>();
            hobbies = new HashSet<>(category.getHobbies());

            Hobby matchedHobby = hobbies.stream().findFirst().orElse(null);
            dto.setHobbyId(matchedHobby != null ? matchedHobby.getId() : null);
            dtos.add(dto);
        }

        return dtos;
    }
}

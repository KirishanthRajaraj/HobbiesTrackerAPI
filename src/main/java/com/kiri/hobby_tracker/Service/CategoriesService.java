package com.kiri.hobby_tracker.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kiri.hobby_tracker.Model.Category;
import com.kiri.hobby_tracker.Repository.ICategoriesRepository;

@Service
public class CategoriesService {
    private final ICategoriesRepository categoriesRepository;

    public CategoriesService(ICategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<Category> getAllCategories() {
        return categoriesRepository.findAll();
    }
}
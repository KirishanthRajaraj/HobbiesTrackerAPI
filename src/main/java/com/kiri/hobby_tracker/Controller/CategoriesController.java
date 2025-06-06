package com.kiri.hobby_tracker.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiri.hobby_tracker.Model.CategoryDTO;
import com.kiri.hobby_tracker.Service.CategoriesService;

@CrossOrigin
@RestController
public class CategoriesController {

    private final CategoriesService hobbiesService;

    public CategoriesController(CategoriesService hobbiesService) {
        this.hobbiesService = hobbiesService;
    }

    @GetMapping("/getAllCategories")
    public List<CategoryDTO> getAllCategories() {
        try {
            var categories = hobbiesService.getAllCategories();
            return categories;
        } catch (Exception e) {
            System.out.println("Error fetching categories: " + e);
        }
        return List.of();
    }
}

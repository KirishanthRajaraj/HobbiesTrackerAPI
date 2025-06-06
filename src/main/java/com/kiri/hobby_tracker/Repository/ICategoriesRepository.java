package com.kiri.hobby_tracker.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiri.hobby_tracker.Model.Category;

public interface ICategoriesRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}   
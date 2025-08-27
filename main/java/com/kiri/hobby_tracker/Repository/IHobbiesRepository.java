package com.kiri.hobby_tracker.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kiri.hobby_tracker.Model.Hobby;

public interface IHobbiesRepository extends JpaRepository<Hobby, Long> {
    
}   
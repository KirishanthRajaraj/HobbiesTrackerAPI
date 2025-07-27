package com.kiri.hobby_tracker.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kiri.hobby_tracker.Model.HobbyDates;

public interface IHobbyDatesRepository extends JpaRepository<HobbyDates, Long>  {
    
}

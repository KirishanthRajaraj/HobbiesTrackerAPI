package com.kiri.hobby_tracker.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiri.hobby_tracker.Model.HobbyDates;

public interface IHobbyDatesRepository extends JpaRepository<HobbyDates, Long>  {
    List<HobbyDates> findByHobby_Id(Long hobbyId);
}

package com.kiri.hobby_tracker.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kiri.hobby_tracker.Model.Minuspoint;

public interface IMinuspointRepository extends JpaRepository<Minuspoint, Long>  {
    
}

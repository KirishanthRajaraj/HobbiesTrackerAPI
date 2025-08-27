package com.kiri.hobby_tracker.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kiri.hobby_tracker.Model.Pluspoint;

public interface IPluspointRepository extends JpaRepository<Pluspoint, Long>  {
    
}

package com.kiri.hobby_tracker.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kiri.hobby_tracker.Model.User;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

package dev.maxuz.kwh.database.repository;

import dev.maxuz.kwh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String username);
}
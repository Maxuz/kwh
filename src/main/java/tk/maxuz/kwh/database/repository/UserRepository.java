package tk.maxuz.kwh.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.maxuz.kwh.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String username);
}
package tk.maxuz.kwh.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.maxuz.kwh.database.dao.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
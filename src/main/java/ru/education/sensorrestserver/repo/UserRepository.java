package ru.education.sensorrestserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.education.sensorrestserver.models.User;

import java.util.Optional;

/**
 * @author Kirill Popov
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);
}

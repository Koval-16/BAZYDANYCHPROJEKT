package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);

    List<User> findAllByRole_Name(String roleName);

    boolean existsByLogin(String login);
}
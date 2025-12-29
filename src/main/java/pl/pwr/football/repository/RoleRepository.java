package pl.pwr.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    // Znajdź rolę po nazwie (np. "TRENER")
    Optional<Role> findByName(String name);
}
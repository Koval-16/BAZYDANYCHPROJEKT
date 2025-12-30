package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.entities.Season;

import java.util.Optional;

public interface SeasonRepository extends JpaRepository<Season,Integer> {
    boolean existsByActiveTrue();
    Optional<Season> findByActiveTrue();
}

package pl.pwr.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.Season;

public interface SeasonRepository extends JpaRepository<Season,Integer> {
}

package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.entities.League;

public interface LeagueRepository extends JpaRepository<League,Integer>{

    boolean existsByName(String name);
    boolean existsByAbbreviation(String abbreviation);

}

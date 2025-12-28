package pl.pwr.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.League;

public interface LeagueRepository extends JpaRepository<League,Integer>{
}

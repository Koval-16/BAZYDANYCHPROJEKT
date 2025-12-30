package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.entities.Match;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    boolean existsByLeagueSeasonId(Integer leagueSeasonId);
}

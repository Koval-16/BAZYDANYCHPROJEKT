package pl.pwr.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.Match;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    boolean existsByLeagueSeasonId(Integer leagueSeasonId);
}

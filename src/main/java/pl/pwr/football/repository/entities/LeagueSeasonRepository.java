package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.entities.LeagueSeason;

public interface LeagueSeasonRepository extends JpaRepository<LeagueSeason, Integer> {
    boolean existsBySeasonIdAndLeagueId(Integer seasonId, Integer leagueId);
}
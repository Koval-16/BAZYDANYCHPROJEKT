package pl.pwr.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.League;
import pl.pwr.football.entity.LeagueSeason;
import pl.pwr.football.entity.Season;

public interface LeagueSeasonRepository extends JpaRepository<LeagueSeason, Integer> {
    boolean existsBySeasonIdAndLeagueId(Integer seasonId, Integer leagueId);
}
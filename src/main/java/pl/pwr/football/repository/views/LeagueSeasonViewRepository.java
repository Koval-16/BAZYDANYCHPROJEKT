package pl.pwr.football.repository.views;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.views.LeagueSeasonView;

import java.util.List;

public interface LeagueSeasonViewRepository extends JpaRepository<LeagueSeasonView, Integer> {
    List<LeagueSeasonView> findByLeagueId(Integer leagueId);
    List<LeagueSeasonView> findBySeasonId(Integer seasonId);
}

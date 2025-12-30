package pl.pwr.football.repository.views;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.views.TeamInLeagueView;
import java.util.List;

public interface TeamInLeagueViewRepository extends JpaRepository<TeamInLeagueView, Integer> {
    List<TeamInLeagueView> findByLeagueSeasonId(Integer leagueSeasonId);
}
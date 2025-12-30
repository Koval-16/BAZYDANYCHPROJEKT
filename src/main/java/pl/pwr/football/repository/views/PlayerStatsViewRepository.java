package pl.pwr.football.repository.views;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.views.PlayerStatsView;
import java.util.List;

public interface PlayerStatsViewRepository extends JpaRepository<PlayerStatsView, Integer> {
    List<PlayerStatsView> findByLeagueSeasonId(Integer leagueSeasonId);
    List<PlayerStatsView> findByTeamId(Integer teamId);
}
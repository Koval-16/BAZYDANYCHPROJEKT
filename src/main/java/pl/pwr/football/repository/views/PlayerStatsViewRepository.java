package pl.pwr.football.repository.views;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.views.PlayerStatsView;
import java.util.List;

public interface PlayerStatsViewRepository extends JpaRepository<PlayerStatsView, Integer> {
    List<PlayerStatsView> findByLeagueSeasonId(Integer leagueSeasonId);
    List<PlayerStatsView> findByTeamId(Integer teamId);

    // 1. Najlepsi strzelcy (Gole > 0, sortowane malejąco)
    List<PlayerStatsView> findByLeagueSeasonIdAndGoalsGreaterThanOrderByGoalsDesc(Integer leagueSeasonId, Integer minGoals);

    // 2. Żółte kartki (Kartki > 0, sortowane malejąco)
    List<PlayerStatsView> findByLeagueSeasonIdAndYellowCardsGreaterThanOrderByYellowCardsDesc(Integer leagueSeasonId, Integer minCards);

    // 3. Czerwone kartki (Kartki > 0, sortowane malejąco)
    List<PlayerStatsView> findByLeagueSeasonIdAndRedCardsGreaterThanOrderByRedCardsDesc(Integer leagueSeasonId, Integer minCards);
}
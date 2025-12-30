package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.entities.TeamInLeague;

import java.util.List;

public interface TeamInLeagueRepository extends JpaRepository<TeamInLeague, Integer> {
    // Sprawdzamy, czy drużyna już jest w tej lidze w tym sezonie
    boolean existsByLeagueSeasonIdAndTeamId(Integer leagueSeasonId, Integer teamId);

    List<TeamInLeague> findAllByLeagueSeasonId(Integer leagueSeasonId);
}

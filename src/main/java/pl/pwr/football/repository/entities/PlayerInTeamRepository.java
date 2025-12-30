package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.entities.PlayerInTeam;

public interface PlayerInTeamRepository extends JpaRepository<PlayerInTeam, Integer> {

    boolean existsByPlayerIdAndTeamInLeagueId(Integer playerId, Integer teamInLeagueId);
}
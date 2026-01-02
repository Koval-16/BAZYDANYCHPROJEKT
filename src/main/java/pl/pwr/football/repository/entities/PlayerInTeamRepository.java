package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.entities.PlayerInTeam;

import java.util.List;
import java.util.Optional;

public interface PlayerInTeamRepository extends JpaRepository<PlayerInTeam, Integer> {

    boolean existsByPlayerIdAndTeamInLeagueId(Integer playerId, Integer teamInLeagueId);

    List<PlayerInTeam> findAllByTeamInLeagueId(Integer teamInLeagueId);

    List<PlayerInTeam> findAllByPlayerId(Integer playerId);

    Optional<PlayerInTeam> findByPlayerIdAndTeamInLeagueId(Integer playerId, Integer teamInLeagueId);
}
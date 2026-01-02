package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.pwr.football.entity.entities.Match;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    boolean existsByLeagueSeasonId(Integer leagueSeasonId);

    @Query("SELECT m FROM Match m WHERE m.hostId = :teamId OR m.awayId = :teamId ORDER BY m.id ASC")
    List<Match> findAllByTeamId(@Param("teamId") Integer teamId);

    @Query("SELECT m FROM Match m WHERE (m.hostId = :teamId OR m.awayId = :teamId) AND m.date > :afterDate ORDER BY m.date ASC")
    List<Match> findNextMatchesForTeam(@Param("teamId") Integer teamId, @Param("afterDate") LocalDate afterDate);
}

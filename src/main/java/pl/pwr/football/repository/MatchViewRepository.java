package pl.pwr.football.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.pwr.football.entity.MatchView;

import java.util.List;

public interface MatchViewRepository extends JpaRepository<MatchView, Integer> {

    List<MatchView> findByLeagueSeasonId(Integer leagueSeasonId);

    List<MatchView> findByHostIdOrAwayId(Integer hostId, Integer awayId);

    @Query("SELECT m FROM MatchView m WHERE (m.hostId = :teamId OR m.awayId = :teamId) AND m.isPlayed = true ORDER BY m.date DESC")
    List<MatchView> findLastMatches(@Param("teamId") Integer teamId, Pageable pageable);

    // 2. Następne 5 meczów (Zagrane = false, Sortowanie: Data ROSNĄCO)
    @Query("SELECT m FROM MatchView m WHERE (m.hostId = :teamId OR m.awayId = :teamId) AND m.isPlayed = false ORDER BY m.date ASC")
    List<MatchView> findNextMatches(@Param("teamId") Integer teamId, Pageable pageable);

}
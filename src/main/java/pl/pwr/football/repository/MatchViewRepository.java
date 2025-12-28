package pl.pwr.football.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.pwr.football.entity.MatchView;

import java.util.List;

public interface MatchViewRepository extends JpaRepository<MatchView, Integer> {

    List<MatchView> findByLigaSezonId(Integer ligaSezonId);

    List<MatchView> findByGospodarzIdOrGoscId(Integer gospodarzId, Integer goscId);

    @Query("SELECT m FROM MatchView m WHERE (m.gospodarzId = :teamId OR m.goscId = :teamId) AND m.czyZagrany = true ORDER BY m.data DESC")
    List<MatchView> findLastMatches(@Param("teamId") Integer teamId, Pageable pageable);

    // 2. Następne 5 meczów (Zagrane = false, Sortowanie: Data ROSNĄCO)
    @Query("SELECT m FROM MatchView m WHERE (m.gospodarzId = :teamId OR m.goscId = :teamId) AND m.czyZagrany = false ORDER BY m.data ASC")
    List<MatchView> findNextMatches(@Param("teamId") Integer teamId, Pageable pageable);

}
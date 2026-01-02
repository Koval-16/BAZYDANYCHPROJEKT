package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.pwr.football.entity.entities.CoachInTeam;

import java.util.Optional;

public interface CoachInTeamRepository extends JpaRepository<CoachInTeam, Integer> {
    // Czy drużyna ma już trenera?
    boolean existsByTeamInLeagueId(Integer teamInLeagueId);

    // Czy ten konkretny trener jest już w tej drużynie?
    boolean existsByCoachIdAndTeamInLeagueId(Integer coachId, Integer teamInLeagueId);

    // KLUCZOWE DLA TRENERA: Znajdź ID drużyny w lidze, którą trener prowadzi w AKTYWNYM sezonie
    @Query("""
        SELECT cc.teamInLeagueId 
        FROM CoachInTeam cc
        JOIN TeamInLeague til ON cc.teamInLeagueId = til.id
        JOIN LeagueSeason ls ON til.leagueSeasonId = ls.id
        JOIN Season s ON ls.seasonId = s.id
        WHERE cc.coachId = :coachId AND s.active = true
    """)
    Integer findActiveTeamInLeagueIdByCoachId(@Param("coachId") Integer coachId);

}
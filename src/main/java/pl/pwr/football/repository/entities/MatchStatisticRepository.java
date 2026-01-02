package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.entities.MatchStatistic;
import java.util.List;

public interface MatchStatisticRepository extends JpaRepository<MatchStatistic, Integer> {
    // Pobierz wszystkie zdarzenia z danego meczu
    List<MatchStatistic> findByMatchId(Integer matchId);

    Integer countByMatchIdAndPlayerInTeamIdAndType(Integer matchId, Integer playerInTeamId, Integer type);
}
package pl.pwr.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.LeagueSeasonView;

import java.util.List;

public interface LeagueSeasonViewRepository extends JpaRepository<LeagueSeasonView, Integer> {

    List<LeagueSeasonView> findByLigaId(Integer ligaId);
    List<LeagueSeasonView> findBySezonId(Integer sezonId);

}

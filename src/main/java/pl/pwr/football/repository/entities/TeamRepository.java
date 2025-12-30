package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.entities.Team;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Integer> {

    boolean existsByName(String name);
    boolean existsByAbbreviation(String abbreviation);

    List<Team> findAllByOrderByNameAsc();
}
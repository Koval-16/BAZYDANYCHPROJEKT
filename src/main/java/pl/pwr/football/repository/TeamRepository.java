package pl.pwr.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Integer> {
    //Optional<Team> findById(Integer id);
    //List<Team> findAll();
    boolean existsByName(String name);
    boolean existsByAbbreviation(String abbreviation);
}
package pl.pwr.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.PlayerView;

import java.util.List;

public interface PlayerViewRepository extends JpaRepository<PlayerView, Integer> {
    // findAll() masz za darmo
    List<PlayerView> findByDruzynaID(Integer druzynaId);
}
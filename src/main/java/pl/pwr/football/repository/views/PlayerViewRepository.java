package pl.pwr.football.repository.views;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.views.PlayerView;

import java.util.List;

public interface PlayerViewRepository extends JpaRepository<PlayerView, Integer> {
    List<PlayerView> findByTeamId(Integer teamId);
}
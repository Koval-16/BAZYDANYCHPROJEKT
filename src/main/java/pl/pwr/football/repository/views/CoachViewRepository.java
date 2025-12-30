package pl.pwr.football.repository.views;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.views.CoachView;
import java.util.List;

public interface CoachViewRepository extends JpaRepository<CoachView, Integer> {
    List<CoachView> findByTeamId(Integer teamId);
}
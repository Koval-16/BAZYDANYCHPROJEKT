package pl.pwr.football.repository.views;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.views.RefereeView;

public interface RefereeViewRepository extends JpaRepository<RefereeView, Integer> {
}
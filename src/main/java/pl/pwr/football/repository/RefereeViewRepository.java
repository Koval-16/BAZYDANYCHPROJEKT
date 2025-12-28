package pl.pwr.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.RefereeView;
import java.util.List;

public interface RefereeViewRepository extends JpaRepository<RefereeView, Integer> {
}
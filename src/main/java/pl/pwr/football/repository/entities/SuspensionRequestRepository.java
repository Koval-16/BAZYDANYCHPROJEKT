package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.entities.SuspensionRequest;
import java.util.List;

public interface SuspensionRequestRepository extends JpaRepository<SuspensionRequest, Integer> {
    // Znajdź wnioski nierozpatrzone (reviewed = false)
    List<SuspensionRequest> findByReviewedFalse();
}
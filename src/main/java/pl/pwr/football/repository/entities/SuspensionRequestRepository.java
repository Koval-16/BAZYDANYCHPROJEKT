package pl.pwr.football.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.entities.SuspensionRequest;
import java.util.List;

public interface SuspensionRequestRepository extends JpaRepository<SuspensionRequest, Integer> {
    List<SuspensionRequest> findByStatus(Integer status);

    // Sprawdzenie czy wniosek już istnieje dla tej kartki (żeby sędzia nie klikał 5 razy)
    boolean existsByMatchStatisticId(Integer statId);

    boolean existsByPlayerInTeamIdAndSuspensionMatchIdAndStatus(Integer contractId, Integer matchId, Integer status);
}
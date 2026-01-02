package pl.pwr.football.repository.views;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.football.entity.views.SuspensionRequestView;
import java.util.List;

public interface SuspensionRequestViewRepository extends JpaRepository<SuspensionRequestView, Integer> {
    // Pobierz wszystkie wnioski o danym statusie (np. 0 = oczekujące)
    List<SuspensionRequestView> findByStatus(Integer status);
}
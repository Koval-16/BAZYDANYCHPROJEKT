package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.football.entity.entities.Match;
import pl.pwr.football.entity.entities.PlayerInTeam;
import pl.pwr.football.entity.entities.SuspensionRequest;
import pl.pwr.football.entity.views.SuspensionRequestView; // Import widoku
import pl.pwr.football.repository.entities.MatchRepository;
import pl.pwr.football.repository.entities.PlayerInTeamRepository;
import pl.pwr.football.repository.entities.SuspensionRequestRepository;
import pl.pwr.football.repository.views.SuspensionRequestViewRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class SuspensionService {

    private final SuspensionRequestRepository requestRepo;
    private final MatchRepository matchRepo;
    private final PlayerInTeamRepository contractRepo;
    private final SuspensionRequestViewRepository suspensionRequestViewRepository;

    public SuspensionService(SuspensionRequestRepository requestRepo, MatchRepository matchRepo,
                             SuspensionRequestViewRepository suspensionRequestViewRepository, PlayerInTeamRepository contractRepo) {
        this.requestRepo = requestRepo;
        this.matchRepo = matchRepo;
        this.contractRepo = contractRepo;
        this.suspensionRequestViewRepository = suspensionRequestViewRepository;
    }

    public void createRequest(Integer refereeId, Integer contractId, Integer statId) {
        if (requestRepo.existsByMatchStatisticId(statId)) { // Zakładam, że w repo masz metodę existsByStatId (lub MatchStatisticId)
            throw new IllegalStateException("Wniosek dla tej kartki już został wysłany.");
        }
        SuspensionRequest request = new SuspensionRequest(refereeId, contractId, statId);
        requestRepo.save(request);
    }

    // --- POPRAWKA: Metoda zwracająca WIDOKI dla Admina ---
    public List<SuspensionRequestView> getPendingRequestViews() {
        return suspensionRequestViewRepository.findByStatus(0);
    }

    // F21: Admin podejmuje decyzję
    @Transactional
    public void resolveRequest(Integer requestId, boolean accepted) {
        SuspensionRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Brak wniosku"));

        if (accepted) {
            request.setStatus(1);

            PlayerInTeam contract = contractRepo.findById(request.getPlayerInTeamId()).orElseThrow(); // getContractId() zamiast getPlayerInTeamId() jeśli tak nazwałeś w encji
            Integer teamId = contract.getTeamInLeagueId();

            List<Match> nextMatches = matchRepo.findNextMatchesForTeam(teamId, LocalDate.now().minusDays(1));

            if (!nextMatches.isEmpty()) {
                Match nextMatch = nextMatches.get(0);
                request.setSuspensionMatchId(nextMatch.getId());
            } else {
                // Opcjonalnie: logowanie, że brak przyszłych meczów
            }

        } else {
            request.setStatus(2); // Odrzucony
        }
        requestRepo.save(request);
    }

    // Walidacja czy grać
    public boolean isPlayerSuspendedForMatch(Integer contractId, Integer matchId) {
        return requestRepo.existsByPlayerInTeamIdAndSuspensionMatchIdAndStatus(contractId, matchId, 1);
    }
}
package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.entity.entities.CoachInTeam;
import pl.pwr.football.repository.entities.CoachInTeamRepository;

@Service
public class ContractService {

    private final CoachInTeamRepository coachInTeamRepository;

    public ContractService(CoachInTeamRepository coachInTeamRepository) {
        this.coachInTeamRepository = coachInTeamRepository;
    }

    // Przeniesione z TeamInLeagueService
    public void assignCoachToTeam(Integer teamInLeagueId, Integer coachId) {
        // 1. Sprawdź czy drużyna ma już trenera
        if (coachInTeamRepository.existsByTeamInLeagueId(teamInLeagueId)) {
            throw new IllegalArgumentException("Ta drużyna ma już trenera!");
        }

        CoachInTeam contract = new CoachInTeam();
        contract.setTeamInLeagueId(teamInLeagueId);
        contract.setCoachId(coachId);

        coachInTeamRepository.save(contract);
    }

    public void addPlayerToTeam(Integer teamId, Integer playerId) {
    }
}
package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.football.entity.entities.CoachInTeam;
import pl.pwr.football.entity.entities.PlayerInTeam;
import pl.pwr.football.entity.entities.User;
import pl.pwr.football.repository.entities.*;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    private final CoachInTeamRepository coachInTeamRepository;
    private final PlayerInTeamRepository playerInTeamRepository;
    private final TeamInLeagueRepository teamInLeagueRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    public ContractService(CoachInTeamRepository coachInTeamRepository,
                           PlayerInTeamRepository playerInTeamRepository,
                           TeamInLeagueRepository teamInLeagueRepository,
                           UserRepository userRepository,
                           MatchRepository matchRepository) {
        this.coachInTeamRepository = coachInTeamRepository;
        this.playerInTeamRepository = playerInTeamRepository;
        this.teamInLeagueRepository = teamInLeagueRepository;
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
    }

    public void assignCoachToTeam(Integer teamInLeagueId, Integer coachId) {
        if (coachInTeamRepository.existsByTeamInLeagueId(teamInLeagueId)) {
            throw new IllegalArgumentException("Ta drużyna ma już trenera!");
        }
        CoachInTeam contract = new CoachInTeam();
        contract.setTeamInLeagueId(teamInLeagueId);
        contract.setCoachId(coachId);
        coachInTeamRepository.save(contract);
    }

    public List<PlayerInTeam> getPlayerContracts(Integer playerId) {
        return playerInTeamRepository.findAllByPlayerId(playerId);
    }

    public List<PlayerInTeam> getTeamSquad(Integer teamInLeagueId) {
        return playerInTeamRepository.findAllByTeamInLeagueId(teamInLeagueId);
    }

    @Transactional
    public void requestPlayerTransfer(Integer teamInLeagueId, Integer playerId) {
        // Sprawdzamy czy nie ma już jakiegokolwiek wpisu (aktywnego lub niepotwierdzonego) w tym sezonie
        // Zakładam, że jeśli był "historyczny" (active=false, confirmed=true), to można dodać go ponownie
        // Ale najprościej sprawdzić czy nie ma aktywnego/oczekującego:

        Optional<PlayerInTeam> existing = playerInTeamRepository.findByPlayerIdAndTeamInLeagueId(playerId, teamInLeagueId);

        if (existing.isPresent()) {
            PlayerInTeam pit = existing.get();
            if (pit.isActive() || !pit.isConfirmed()) {
                throw new IllegalArgumentException("Ten piłkarz jest już w Twojej drużynie (lub ma oczekującą ofertę).");
            }
            pit.setConfirmed(false);
            pit.setActive(false);
            playerInTeamRepository.save(pit);
            return;
        }

        PlayerInTeam contract = new PlayerInTeam();
        contract.setTeamInLeagueId(teamInLeagueId);
        contract.setPlayerId(playerId);

        contract.setConfirmed(false);
        contract.setActive(false);

        playerInTeamRepository.save(contract);
    }

    @Transactional
    public void acceptContract(Integer contractId) {
        PlayerInTeam contract = playerInTeamRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono kontraktu."));

        // Zmieniamy flagi - staje się pełnoprawnym zawodnikiem
        contract.setConfirmed(true);
        contract.setActive(true);
        playerInTeamRepository.save(contract);

        // F32: Automatycznie ustawiamy, że piłkarz znalazł klub
        User player = userRepository.findById(contract.getPlayerId()).orElseThrow();
        player.setLookingForClub(false);
        userRepository.save(player);
    }

    @Transactional
    public void terminateContract(Integer contractId) {
        PlayerInTeam contract = playerInTeamRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono kontraktu."));

        if (!contract.isConfirmed()) {
            // SCENARIUSZ A: Piłkarz jeszcze nie potwierdził (To tylko oferta).
            // Niezależnie czy to trener anuluje ofertę, czy piłkarz ją odrzuca.
            // Usuwamy rekord fizycznie z bazy, bo nie ma żadnych statystyk z nim związanych.
            playerInTeamRepository.delete(contract);
        } else {
            // SCENARIUSZ B: Piłkarz był już w składzie (Confirmed = true).
            // Niezależnie czy trener go wyrzuca, czy piłkarz odchodzi.
            // NIE usuwamy rekordu, żeby nie zepsuć statystyk meczowych (Foreign Keys).
            // Zmieniamy flagę Active na false (Soft Delete).

            contract.setActive(false);
            // Confirmed zostaje true (bo historycznie był potwierdzony)

            playerInTeamRepository.save(contract);

            // Opcjonalnie: Można by tu ustawić player.setLookingForClub(true),
            // ale F32 mówi, że piłkarz robi to sam.
        }
    }

    public void addPlayerToTeam(Integer teamId, Integer playerId) {
    }



    public Integer getCoachCurrentTeamId(Integer coachId) {
        // Korzystamy z metody w repozytorium, która ma JOIN do Season i sprawdza s.active = true
        // Jeśli nie znajdzie (brak kontraktu lub sezon nieaktywny), zwraca null
        return coachInTeamRepository.findActiveTeamInLeagueIdByCoachId(coachId);
    }
}
package pl.pwr.football.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.football.entity.entities.Match;
import pl.pwr.football.entity.views.MatchView;
import pl.pwr.football.repository.entities.MatchRepository;
import pl.pwr.football.repository.views.MatchViewRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatchService {

    private final MatchViewRepository matchViewRepository;
    private final MatchRepository matchRepository;

    public MatchService(MatchViewRepository matchViewRepository, MatchRepository matchRepository) {
        this.matchViewRepository = matchViewRepository;
        this.matchRepository = matchRepository;
    }

    public List<MatchView> getAllMatches() {
        return matchViewRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<MatchView> getMatchesByLeague(Integer leagueSeasonId) {
        return matchViewRepository.findByLeagueSeasonId(
                leagueSeasonId,
                Sort.by(Sort.Direction.ASC, "id") // Sortuj rosnąco po ID
        );
    }

    public List<MatchView> getMatchesByTeam(Integer hostId, Integer awayId){
        return matchViewRepository.findByHostIdOrAwayId(hostId, awayId);
    }

    public List<MatchView> getLast5Matches(Integer teamId) {
        return matchViewRepository.findLastMatches(teamId, PageRequest.of(0, 5));
    }

    public List<MatchView> getNext5Matches(Integer teamId) {
        return matchViewRepository.findNextMatches(teamId, PageRequest.of(0, 5));
    }

    // ----------------

    @Transactional
    public void proposeMatchDate(Integer matchId, LocalDate proposedDate, Integer coachTeamId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Mecz nie istnieje"));
        if (!match.getHostId().equals(coachTeamId)) {
            throw new IllegalStateException("Tylko trener gospodarzy może zaproponować datę.");
        }
        if (match.getProposalStatus() > 1) {
            throw new IllegalStateException("Nie można zmienić daty - jest w trakcie akceptacji.");
        }
        match.setProposedDate(proposedDate);
        match.setProposalStatus(1); // 1 = Propozycja wysłana
        matchRepository.save(match);
    }

    @Transactional
    public void respondToProposal(Integer matchId, boolean accepted, Integer coachTeamId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Mecz nie istnieje"));
        if (!match.getAwayId().equals(coachTeamId)) {
            throw new IllegalStateException("Tylko trener gości może zaakceptować/odrzucić propozycję.");
        }
        if (match.getProposalStatus() != 1) {
            throw new IllegalStateException("Brak aktywnej propozycji do rozpatrzenia.");
        }
        if (accepted) {
            // AKCEPTACJA -> Status 2 (Czeka na Admina)
            match.setProposalStatus(2);
        } else {
            // ODRZUCENIE -> Status 0 (Reset, brak daty)
            match.setProposalStatus(0);
            match.setProposedDate(null);
        }
        matchRepository.save(match);
    }

    @Transactional
    public void adminDecideDate(Integer matchId, boolean accepted) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Mecz nie istnieje"));

        // Admin może decydować tylko gdy status = 2 (czeka na admina)
        if (match.getProposalStatus() == null || match.getProposalStatus() != 2) {
            throw new IllegalStateException("Ten mecz nie oczekuje na zatwierdzenie daty.");
        }

        if (accepted) {
            // AKCEPTACJA:
            // 1. Ustaw oficjalną datę na proponowaną
            match.setDate(match.getProposedDate());
            // 2. Zmień status na 3 (Zatwierdzone)
            match.setProposalStatus(3);
        } else {
            // ODRZUCENIE:
            // 1. Wyczyść propozycję
            match.setProposedDate(null);
            // 2. Zresetuj status na 0 (Brak propozycji)
            match.setProposalStatus(0);
        }
        matchRepository.save(match);
    }

    @Transactional
    public void assignReferee(Integer matchId, Integer refereeId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Mecz nie istnieje"));
        match.setRefereeId(refereeId);
        matchRepository.save(match);
    }

    public List<MatchView> getMatchesForReferee(Integer refereeId) {
        return matchViewRepository.findByRefereeIdOrderByDateAsc(refereeId);
    }

    @Transactional
    public void submitMatchResult(Integer matchId, Integer homeScore, Integer awayScore) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Mecz nie istnieje"));
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Wynik nie może być ujemny.");
        }
        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
        match.setPlayed(true);
        match.setProposalStatus(3);
        matchRepository.save(match);
    }

    public Match getMatchById(Integer matchId) {
        return matchRepository.findById(matchId).orElseThrow(() -> new IllegalArgumentException("Mecz nie istnieje"));
    }

    public List<MatchView> findMatchesForTeamInActiveSeason (Integer id) {
        return matchViewRepository.findMatchesForTeamInActiveSeason(id);
    }
}
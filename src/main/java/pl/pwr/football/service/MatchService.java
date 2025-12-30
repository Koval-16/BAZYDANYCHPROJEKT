package pl.pwr.football.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.pwr.football.entity.views.MatchView;
import pl.pwr.football.repository.views.MatchViewRepository;

import java.util.List;

@Service
public class MatchService {

    private final MatchViewRepository matchViewRepository;

    public MatchService(MatchViewRepository matchViewRepository) {
        this.matchViewRepository = matchViewRepository;
    }

    public List<MatchView> getAllMatches() {
        return matchViewRepository.findAll();
    }

    public List<MatchView> getMatchesByLeague(Integer leagueSeasonId) {
        return matchViewRepository.findByLeagueSeasonId(leagueSeasonId);
    }

    // Pobiera mecze gdzie dana drużyna jest gospodarzem LUB gościem
    // (W kontrolerze przekażesz to samo ID jako oba parametry)
    public List<MatchView> getMatchesByTeam(Integer hostId, Integer awayId){
        return matchViewRepository.findByHostIdOrAwayId(hostId, awayId);
    }

    // A) 5 Ostatnich Meczów
    public List<MatchView> getLast5Matches(Integer teamId) {
        return matchViewRepository.findLastMatches(teamId, PageRequest.of(0, 5));
    }

    // B) 5 Następnych Meczów
    public List<MatchView> getNext5Matches(Integer teamId) {
        return matchViewRepository.findNextMatches(teamId, PageRequest.of(0, 5));
    }
}
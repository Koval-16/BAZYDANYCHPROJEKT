package pl.pwr.football.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.pwr.football.entity.MatchView;
import pl.pwr.football.repository.MatchViewRepository;

import java.util.List;

@Service
public class MatchService {

    private final MatchViewRepository matchViewRepository;

    public MatchService(MatchViewRepository matchViewRepository) {
        this.matchViewRepository = matchViewRepository;
    }

    public List<MatchView> get_all_matches() {
        return matchViewRepository.findAll();
    }

    public List<MatchView> get_matches_by_league(Integer ligaSezonId) {
        return matchViewRepository.findByLeagueSeasonId(ligaSezonId);
    }

    public List<MatchView> get_matches_by_team(Integer gospodarzId, Integer goscId){
        return matchViewRepository.findByHostIdOrAwayId(gospodarzId, goscId);
    }

    public List<MatchView> getLast5Matches(Integer teamId) {
        return matchViewRepository.findLastMatches(teamId, PageRequest.of(0, 5));
    }

    // B) 5 Następnych Meczów
    public List<MatchView> getNext5Matches(Integer teamId) {
        return matchViewRepository.findNextMatches(teamId, PageRequest.of(0, 5));
    }


}
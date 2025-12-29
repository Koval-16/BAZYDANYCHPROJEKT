package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.entity.LeagueSeasonView;
import pl.pwr.football.repository.LeagueSeasonViewRepository;

import java.util.List;

@Service
public class LeagueSeasonViewService {

    private final LeagueSeasonViewRepository leagueSeasonViewRepository;

    public LeagueSeasonViewService(LeagueSeasonViewRepository leagueSeasonViewRepository){
        this.leagueSeasonViewRepository = leagueSeasonViewRepository;
    }

    public List<LeagueSeasonView> get_leagueseason_by_league_id(Integer ligaID){
        return leagueSeasonViewRepository.findByLeagueId(ligaID);
    }

    public List<LeagueSeasonView> get_leagueseason_by_season_id(Integer sezonID){
        return leagueSeasonViewRepository.findBySeasonId(sezonID);
    }

    public LeagueSeasonView findById(Integer id) {
        return leagueSeasonViewRepository.findById(id).orElse(null);
    }
}

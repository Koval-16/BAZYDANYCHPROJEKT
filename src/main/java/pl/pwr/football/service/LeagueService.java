package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.entity.League;
import pl.pwr.football.repository.LeagueRepository;

import java.util.List;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;

    LeagueService(LeagueRepository leagueRepository){
        this.leagueRepository = leagueRepository;
    }

    public List<League> get_all_leagues(){
        return leagueRepository.findAll();
    }

}

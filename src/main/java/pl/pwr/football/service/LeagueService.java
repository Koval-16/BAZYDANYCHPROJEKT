package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.dto.LeagueCreateDto;
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

    public void createLeague(LeagueCreateDto dto) {
        if (leagueRepository.existsByLeagueName(dto.getName())) {
            throw new IllegalArgumentException("Liga o nazwie '" + dto.getName() + "' już istnieje!");
        }
        if (leagueRepository.existsByLeagueAbbreviation(dto.getAbbreviation())) {
            throw new IllegalArgumentException("Liga ze skrótem '" + dto.getAbbreviation() + "' już istnieje!");
        }

        League league = new League();
        league.setLeagueName(dto.getName());
        league.setLeagueAbbreviation(dto.getAbbreviation().toUpperCase());

        leagueRepository.save(league);
    }

}

package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.entity.Team;
import pl.pwr.football.repository.TeamRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public List<Team> get_all_teams(){
        return teamRepository.findAll();
    }

    public Team get_team_by_id(Integer id){
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono drużyny o ID: " + id));
    }
}

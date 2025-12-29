package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.dto.TeamAddedDto;
import pl.pwr.football.entity.Team;
import pl.pwr.football.repository.TeamRepository;

import java.time.LocalDate;
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

    // Metoda rzucająca wyjątek, jeśli dane są zduplikowane
    public void addTeam(TeamAddedDto dto) throws IllegalArgumentException {

        if (teamRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Drużyna o takiej nazwie już istnieje!");
        }
        if (teamRepository.existsByAbbreviation(dto.getAbbreviation())) {
            throw new IllegalArgumentException("Taki skrót jest już zajęty!");
        }

        Team team = new Team();
        team.setName(dto.getName());
        team.setAbbreviation(dto.getAbbreviation().toUpperCase()); // Skróty zwykle wielkimi literami

        team.setFoundationDate(LocalDate.now());

        team.setKitsHome(dto.getKitsHome());
        team.setKitsAway(dto.getKitsAway());
        team.setStadium(dto.getStadium());
        team.setAddress(dto.getAddress());

        teamRepository.save(team);
    }

}

package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.entity.Team;
import pl.pwr.football.entity.TeamInLeague;
import pl.pwr.football.repository.TeamInLeagueRepository;
import pl.pwr.football.repository.TeamRepository;

import java.util.List;

@Service
public class TeamInLeagueService {

    private final TeamInLeagueRepository teamInLeagueRepository;
    private final TeamRepository teamRepository;
    private final LeagueSeasonViewService leagueSeasonViewService; // Do sprawdzenia czy sezon aktywny

    public TeamInLeagueService(TeamInLeagueRepository teamInLeagueRepository,
                               TeamRepository teamRepository,
                               LeagueSeasonViewService leagueSeasonViewService) {
        this.teamInLeagueRepository = teamInLeagueRepository;
        this.teamRepository = teamRepository;
        this.leagueSeasonViewService = leagueSeasonViewService;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAllByOrderByNameAsc();
    }

    public void addTeamToLeague(Integer leagueSeasonId, Integer teamId) {
        // 1. Walidacja: Czy sezon jest aktywny?
        // Pobieramy widok, żeby sprawdzić flagę active
        var lsView = leagueSeasonViewService.findById(leagueSeasonId); // Metoda do dodania w ViewService
        if (lsView != null && !lsView.getSeasonIsActive()) {
            throw new IllegalStateException("Nie można dodawać drużyn do zakończonego sezonu!");
        }

        // 2. Walidacja: Czy drużyna istnieje?
        if (!teamRepository.existsById(teamId)) {
            throw new IllegalArgumentException("Wybrana drużyna nie istnieje.");
        }

        // 3. Walidacja: Duplikat
        if (teamInLeagueRepository.existsByLeagueSeasonIdAndTeamId(leagueSeasonId, teamId)) {
            throw new IllegalArgumentException("Ta drużyna już gra w tej lidze w tym sezonie.");
        }

        // 4. Zapis (statystyki domyślnie 0 w klasie)
        TeamInLeague til = new TeamInLeague();
        til.setLeagueSeasonId(leagueSeasonId);
        til.setTeamId(teamId);

        teamInLeagueRepository.save(til);
    }
}
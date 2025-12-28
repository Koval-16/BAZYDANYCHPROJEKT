package pl.pwr.football.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.pwr.football.repository.PlayerViewRepository;
import pl.pwr.football.service.MatchService;
import pl.pwr.football.service.PersonViewService;
import pl.pwr.football.service.PlayerViewService;
import pl.pwr.football.service.TeamService;

@Controller
public class TeamController {

    private final TeamService teamService;
    private final PlayerViewService playerViewService;
    private final MatchService matchService;

    public TeamController(TeamService teamService, PlayerViewService playerViewService, MatchService matchService){
        this.teamService = teamService;
        this.playerViewService = playerViewService;
        this.matchService = matchService;
    }

    @GetMapping("/druzyny")
    public String get_all_teams(Model model) {
        var teams = teamService.get_all_teams();
        model.addAttribute("teams", teams);
        return "teams";
    }

    @GetMapping("/druzyny/{id}")
    public String getTeamDetails(@PathVariable Integer id, Model model) {
        var team = teamService.get_team_by_id(id);
        var squad = playerViewService.get_team_players(id);
        var lastMatches = matchService.getLast5Matches(id);
        var nextMatches = matchService.getNext5Matches(id);

        model.addAttribute("team", team);
        model.addAttribute("squad", squad);
        model.addAttribute("lastMatches", lastMatches); // Przekazujemy do HTML
        model.addAttribute("nextMatches", nextMatches); // Przekazujemy do HTML
        return "team-details";
    }

}

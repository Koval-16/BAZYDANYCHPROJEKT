package pl.pwr.football.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.pwr.football.service.LeagueSeasonViewService;
import pl.pwr.football.service.LeagueService;

@Controller
public class LeagueController {

    private final LeagueService leagueService;
    private final LeagueSeasonViewService leagueSeasonViewService;

    public LeagueController(LeagueService leagueService, LeagueSeasonViewService leagueSeasonViewService){
        this.leagueService = leagueService;
        this.leagueSeasonViewService = leagueSeasonViewService;
    }

    @GetMapping("/ligi")
    public String get_all_seasons(Model model) {
        var leagues = leagueService.get_all_leagues();
        model.addAttribute("leagues", leagues);
        return "leagues";
    }

    @GetMapping("/ligi/{leagueID}")
    public String get_by_league_id(@PathVariable Integer leagueID, Model model){
        var ls = leagueSeasonViewService.get_leagueseason_by_league_id(leagueID);
        model.addAttribute("ls", ls);
        return "league-details";
    }


}

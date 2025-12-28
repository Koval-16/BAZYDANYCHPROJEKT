package pl.pwr.football.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.pwr.football.service.LeagueSeasonViewService;
import pl.pwr.football.service.SeasonService;

@Controller
public class SeasonController {

    private final SeasonService seasonService;
    private final LeagueSeasonViewService leagueSeasonViewService;

    public SeasonController(SeasonService seasonService, LeagueSeasonViewService leagueSeasonViewService){
        this.seasonService = seasonService;
        this.leagueSeasonViewService = leagueSeasonViewService;
    }

    @GetMapping("/sezony")
    public String get_all_seasons(Model model) {
        var seasons = seasonService.get_all_seasons();
        model.addAttribute("seasons", seasons);
        return "seasons";
    }

    @GetMapping("/sezony/{seasonID}")
    public String get_by_season_id(@PathVariable Integer seasonID, Model model){
        var ls = leagueSeasonViewService.get_leagueseason_by_season_id(seasonID);
        model.addAttribute("ls", ls);
        return "season-details";
    }
}

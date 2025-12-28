package pl.pwr.football.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.pwr.football.service.LeagueSeasonViewService;

@Controller
public class LeagueSeasonController {

    private final LeagueSeasonViewService leagueSeasonViewService;

    public LeagueSeasonController(LeagueSeasonViewService leagueSeasonViewService){
        this.leagueSeasonViewService = leagueSeasonViewService;
    }

}

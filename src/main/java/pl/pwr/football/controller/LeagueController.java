package pl.pwr.football.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pwr.football.dto.LeagueCreateDto;
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
        if (!model.containsAttribute("newLeague")) {
            model.addAttribute("newLeague", new LeagueCreateDto());
        }
        return "leagues";
    }

    @GetMapping("/ligi/{leagueID}")
    public String get_by_league_id(@PathVariable Integer leagueID, Model model){
        var ls = leagueSeasonViewService.get_leagueseason_by_league_id(leagueID);
        model.addAttribute("ls", ls);
        return "league-details";
    }

    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/admin/ligi/nowa")
    public String createLeague(@Valid @ModelAttribute("newLeague") LeagueCreateDto leagueDto,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("leagues", leagueService.get_all_leagues());
            return "leagues";
        }

        try {
            leagueService.createLeague(leagueDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("leagues", leagueService.get_all_leagues());
            return "leagues";
        }

        return "redirect:/ligi";
    }

}

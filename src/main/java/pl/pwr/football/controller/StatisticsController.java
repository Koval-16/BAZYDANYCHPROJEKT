package pl.pwr.football.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.pwr.football.service.LeagueManagementService;
import pl.pwr.football.service.MatchStatisticsService;

@Controller
public class StatisticsController {

    private final MatchStatisticsService statisticsService;
    private final LeagueManagementService leagueService; // Żeby pobrać nazwę ligi do nagłówka

    public StatisticsController(MatchStatisticsService statisticsService, LeagueManagementService leagueService) {
        this.statisticsService = statisticsService;
        this.leagueService = leagueService;
    }

    @GetMapping("/dom/rozgrywki/{id}/statystyki")
    public String showLeagueStats(@PathVariable("id") Integer leagueSeasonId, Model model) {
        // 1. Info o lidze (nagłówek)
        var leagueSeason = leagueService.getLeagueSeasonViewById(leagueSeasonId);
        if (leagueSeason == null) return "redirect:/ligi";

        model.addAttribute("league", leagueSeason);

        // 2. Statystyki
        model.addAttribute("scorers", statisticsService.getTopScorers(leagueSeasonId));
        model.addAttribute("yellows", statisticsService.getYellowCardsStats(leagueSeasonId));
        model.addAttribute("reds", statisticsService.getRedCardsStats(leagueSeasonId));

        return "league-stats";
    }
}
package pl.pwr.football.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.pwr.football.service.MatchService;

@Controller
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // F06: Wszystkie mecze (np. panel sędziego)
    @GetMapping("/dom/mecze")
    public String getAllMatches(Model model) {
        var mecze = matchService.getAllMatches();
        model.addAttribute("mecze", mecze);
        return "lista-meczy"; // Plik HTML z listą meczów
    }

    // F06: Mecze w konkretnej lidze (Terminarz)
    @GetMapping("/dom/rozgrywki/{id}/mecze")
    public String getMatchesByLeague(@PathVariable Integer id, Model model) {
        var mecze = matchService.getMatchesByLeague(id);
        model.addAttribute("mecze", mecze);
        return "lista-meczy"; // Plik HTML z terminarzem
    }
}
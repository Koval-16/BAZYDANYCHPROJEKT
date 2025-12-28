package pl.pwr.football.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pwr.football.service.MatchService;

@Controller
public class MatchController {

    private final MatchService matchService;

    // Ręczny konstruktor
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/mecze")
    public String listaMeczy(Model model) {
        // Pobieramy dane z widoku SQL przez nasz Serwis
        var mecze = matchService.get_all_matches();

        // Wkładamy do modelu, żeby HTML to widział
        model.addAttribute("mecze", mecze);

        return "lista-meczy"; // Musisz stworzyć plik lista-meczy.html
    }
}
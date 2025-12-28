package pl.pwr.football.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pwr.football.service.PersonViewService;

@Controller
public class UserController {

    private final PersonViewService personViewService;

    public UserController(PersonViewService personViewService) {
        this.personViewService = personViewService;
    }

    @GetMapping("/pilkarze")
    public String listaPilkarzy(Model model) {
        var lista = personViewService.getAllPlayers();
        model.addAttribute("tytul", "Lista Piłkarzy");
        model.addAttribute("typ", "PILKARZ"); // Flaga dla HTML
        model.addAttribute("osoby", lista);
        return "lista-osob";
    }

    @GetMapping("/sedziowie")
    public String listaSedziow(Model model) {
        var lista = personViewService.getAllReferees();
        model.addAttribute("tytul", "Lista Sędziów");
        model.addAttribute("typ", "SEDZIA"); // Flaga dla HTML
        model.addAttribute("osoby", lista);
        return "lista-osob";
    }
}
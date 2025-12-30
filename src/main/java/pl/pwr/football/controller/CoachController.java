package pl.pwr.football.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pwr.football.service.ContractService;
import pl.pwr.football.service.UserService;

@Controller
@PreAuthorize("hasRole('Trener')")
public class CoachController {

    private final ContractService contractService;
    private final UserService userService;

    public CoachController(ContractService contractService, UserService userService) {
        this.contractService = contractService;
        this.userService = userService;
    }

    // Panel trenera
    @GetMapping("/trener/panel")
    public String coachPanel(Model model) {
        // Tu w przyszłości pobierzemy ID drużyny, którą prowadzi zalogowany trener
        // Na razie mockujemy lub zostawiamy puste
        return "coach-dashboard";
    }

    // F26: Lista piłkarzy szukających klubu
    @GetMapping("/trener/transfery")
    public String transferMarket(Model model) {
        // Zakładam, że dodamy taką metodę w UserService lub PersonViewService
        // model.addAttribute("freePlayers", userService.getFreePlayers());
        return "transfer-market";
    }

    // F23: Dodanie piłkarza do drużyny
    @PostMapping("/trener/dodaj-pilkarza")
    public String addPlayerToTeam(@RequestParam Integer teamId,
                                  @RequestParam Integer playerId,
                                  RedirectAttributes ra) {
        try {
            contractService.addPlayerToTeam(teamId, playerId);
            ra.addFlashAttribute("successMessage", "Piłkarz zakontraktowany!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/trener/panel";
    }
}
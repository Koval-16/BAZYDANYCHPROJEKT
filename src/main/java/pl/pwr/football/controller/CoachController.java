package pl.pwr.football.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pwr.football.entity.entities.PlayerInTeam;
import pl.pwr.football.entity.entities.Team;
import pl.pwr.football.entity.entities.User;
import pl.pwr.football.service.ContractService;
import pl.pwr.football.service.LeagueManagementService;
import pl.pwr.football.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("hasRole('Trener')")
public class CoachController {

    private final ContractService contractService;
    private final UserService userService;
    private final LeagueManagementService leagueManagementService; // Do pobrania info o drużynie

    public CoachController(ContractService contractService, UserService userService, LeagueManagementService leagueManagementService) {
        this.contractService = contractService;
        this.userService = userService;
        this.leagueManagementService = leagueManagementService;
    }

    // Panel trenera
    @GetMapping("/trener/panel")
    public String coachPanel(Model model, Principal principal) {
        // 1. Dane trenera i ID drużyny
        User coach = userService.getUserByLogin(principal.getName());
        Integer teamId = contractService.getCoachCurrentTeamId(coach.getId());

        if (teamId == null) {
            return "no-team";
        }

        Team team = leagueManagementService.getTeamByTeamInLeagueId(teamId);
        model.addAttribute("team", team);

        List<PlayerInTeam> contracts = contractService.getTeamSquad(teamId);
        List<SquadMemberViewDto> squadList = new ArrayList<>();
        for (PlayerInTeam pit : contracts) {
            if (!pit.isActive() && pit.isConfirmed()) continue;
            User playerUser = userService.getUserById(pit.getPlayerId());
            squadList.add(new SquadMemberViewDto(pit, playerUser));
        }
        model.addAttribute("squad", squadList);
        model.addAttribute("teamInLeagueId", teamId);
        model.addAttribute("teamId", teamId);
        model.addAttribute("freePlayers", userService.getFreePlayers());
        return "coach-team";
    }
    public record SquadMemberViewDto(PlayerInTeam contract, User user) {}

    @PostMapping("/trener/edytuj-druzyne")
    public String editTeam(@RequestParam Integer teamId,
                           @RequestParam String name,
                           @RequestParam String abbreviation,
                           @RequestParam String stadium,
                           @RequestParam String address,
                           @RequestParam String kitsHome,
                           @RequestParam String kitsAway,
                           RedirectAttributes ra) {
        try {
            leagueManagementService.updateTeamDetails(teamId, name, abbreviation, stadium, address, kitsHome, kitsAway);
            ra.addFlashAttribute("successMessage", "Dane drużyny zaktualizowane!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Błąd edycji: " + e.getMessage());
        }
        return "redirect:/trener/panel";
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
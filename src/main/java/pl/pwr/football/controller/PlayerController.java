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
@PreAuthorize("hasRole('Pilkarz')")
public class PlayerController {

    private final ContractService contractService;
    private final UserService userService;
    private final LeagueManagementService leagueManagementService;

    public PlayerController(ContractService contractService, UserService userService, LeagueManagementService leagueManagementService) {
        this.contractService = contractService;
        this.userService = userService;
        this.leagueManagementService = leagueManagementService;
    }

    @GetMapping("/pilkarz/panel")
    public String playerDashboard(Model model, Principal principal) {
        // 1. Pobierz dane piłkarza
        User player = userService.getUserByLogin(principal.getName());
        model.addAttribute("user", player);

        // 2. Pobierz wszystkie kontrakty (aktywne, historyczne, zaproszenia)
        List<PlayerInTeam> allContracts = contractService.getPlayerContracts(player.getId());

        // 3. Rozdzielamy na listy do widoku + mapujemy nazwy drużyn
        List<ContractViewDto> invitations = new ArrayList<>();
        ContractViewDto currentTeam = null;

        for (PlayerInTeam pit : allContracts) {
            // Pobieramy nazwę drużyny (potrzebna metoda w LeagueManagementService, którą dodaliśmy przy Trenerze)
            Team team = leagueManagementService.getTeamByTeamInLeagueId(pit.getTeamInLeagueId());

            if (!pit.isConfirmed()) {
                // To jest ZAPROSZENIE (F34)
                invitations.add(new ContractViewDto(pit, team));
            }
            else if (pit.isActive()) {
                // To jest OBECNA DRUŻYNA (F33)
                // Zakładamy, że piłkarz może grać tylko w jednej na raz
                currentTeam = new ContractViewDto(pit, team);
            }
            // Historyczne (confirmed=true, active=false) pomijamy w tym widoku
        }

        model.addAttribute("invitations", invitations);
        model.addAttribute("currentTeam", currentTeam);

        return "player-dashboard";
    }

    // F34: Akceptacja zaproszenia
    @PostMapping("/pilkarz/akceptuj")
    public String acceptInvitation(@RequestParam Integer contractId, RedirectAttributes ra) {
        try {
            contractService.acceptContract(contractId);
            ra.addFlashAttribute("successMessage", "Dołączyłeś do drużyny!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/pilkarz/panel";
    }

    // F34 (Odrzucenie) oraz F33 (Opuszczenie)
    // ContractService.terminateContract sam rozpozna co robić (delete vs soft-delete)
    @PostMapping("/pilkarz/zakoncz")
    public String endContract(@RequestParam Integer contractId, RedirectAttributes ra) {
        try {
            contractService.terminateContract(contractId);
            ra.addFlashAttribute("successMessage", "Opuszczono drużynę / Odrzucono ofertę.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/pilkarz/panel";
    }

    // F32: Zmiana statusu "Szuka klubu"
    @PostMapping("/pilkarz/status")
    public String updateStatus(@RequestParam(defaultValue = "false") boolean lookingForClub,
                               Principal principal) {
        userService.updateLookingForClub(principal.getName(), lookingForClub);
        return "redirect:/pilkarz/panel";
    }

    // DTO do wyświetlania
    public record ContractViewDto(PlayerInTeam contract, Team team) {}
}
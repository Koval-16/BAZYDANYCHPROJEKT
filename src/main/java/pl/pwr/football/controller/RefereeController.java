package pl.pwr.football.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pwr.football.entity.entities.MatchStatistic;
import pl.pwr.football.entity.entities.User;
import pl.pwr.football.entity.views.PlayerStatsView;
import pl.pwr.football.repository.views.PlayerStatsViewRepository;
import pl.pwr.football.service.*;
import pl.pwr.football.entity.entities.Match; // Zwykła encja (potrzebna do ID drużyn)
import pl.pwr.football.entity.views.MatchView; // Widok

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sedzia")
@PreAuthorize("hasRole('Sedzia')") // Tylko dla sędziego (sprawdź nazwę roli w bazie!)
public class RefereeController {

    private final MatchService matchService;
    private final UserService userService;
    private final MatchStatisticsService statsService; // wstrzykujemy
    private final SuspensionService suspensionService; // potrzebne do składów
    private final PlayerStatsViewRepository playerStatsViewRepository;

    public RefereeController(MatchService matchService, UserService userService,
                             MatchStatisticsService statsService, SuspensionService suspensionService,
                             PlayerStatsViewRepository playerStatsViewRepository) {
        this.matchService = matchService;
        this.userService = userService;
        this.statsService = statsService;
        this.suspensionService = suspensionService;
        this.playerStatsViewRepository = playerStatsViewRepository;
    }

    // Lista meczów do sędziowania
    @GetMapping("/mecze")
    public String refereeMatches(Model model, Principal principal) {
        User referee = userService.getUserByLogin(principal.getName());

        // Pobieramy mecze przypisane do tego sędziego
        var matches = matchService.getMatchesForReferee(referee.getId());

        model.addAttribute("matches", matches);

        return "referee-matches";
    }

    // F29: Zapis wyniku
    @PostMapping("/mecze/wynik")
    public String submitResult(@RequestParam Integer matchId,
                               @RequestParam Integer homeScore,
                               @RequestParam Integer awayScore,
                               RedirectAttributes ra) {
        try {
            matchService.submitMatchResult(matchId, homeScore, awayScore);
            ra.addFlashAttribute("successMessage", "Wynik został zatwierdzony.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Błąd zapisu: " + e.getMessage());
        }
        return "redirect:/sedzia/mecze";
    }

    // F30: Widok statystyk
    @GetMapping("/mecze/{matchId}/statystyki")
    public String manageStats(@PathVariable Integer matchId, Model model) {
        // 1. Dane meczu
        Match match = matchService.getMatchById(matchId);
        if (!match.isPlayed()) return "redirect:/sedzia/mecze";

        // 2. Składy z WIDOKU (to nam daje ID kontraktu i Nazwiska)
        List<PlayerStatsView> hostSquad = playerStatsViewRepository.findByTeamId(match.getHostId());
        List<PlayerStatsView> awaySquad = playerStatsViewRepository.findByTeamId(match.getAwayId());

        // 3. Obecne statystyki meczu
        List<MatchStatistic> stats = statsService.getStatsForMatch(matchId);

        // 4. Mapowanie statystyk na nazwiska (używając pobranych wcześniej list)
        // Tworzymy mapę ID_Kontraktu -> Imię Nazwisko dla szybkiego dostępu
        Map<Integer, String> namesMap = hostSquad.stream().collect(Collectors.toMap(PlayerStatsView::getId, ps -> ps.getName() + " " + ps.getSurname()));
        namesMap.putAll(awaySquad.stream().collect(Collectors.toMap(PlayerStatsView::getId, ps -> ps.getName() + " " + ps.getSurname())));

        List<StatViewDto> statsDto = new ArrayList<>();
        for (MatchStatistic s : stats) {
            String name = namesMap.getOrDefault(s.getPlayerInTeamId(), "Nieznany");
            statsDto.add(new StatViewDto(s, name));
        }

        model.addAttribute("match", match);
        model.addAttribute("hostSquad", hostSquad);
        model.addAttribute("awaySquad", awaySquad);
        model.addAttribute("stats", statsDto);

        return "match-stats";
    }

    @PostMapping("/mecze/statystyki/dodaj")
    public String addStat(@RequestParam Integer matchId,
                          @RequestParam Integer contractId,
                          @RequestParam Integer type,
                          RedirectAttributes ra) {
        try {
            statsService.addStatistic(matchId, contractId, type);
            ra.addFlashAttribute("successMessage", "Dodano.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/sedzia/mecze/" + matchId + "/statystyki";
    }

    @PostMapping("/mecze/statystyki/usun")
    public String removeStat(@RequestParam Integer matchId, @RequestParam Integer statId) {
        statsService.removeStatistic(statId);
        return "redirect:/sedzia/mecze/" + matchId + "/statystyki";
    }

    @PostMapping("/mecze/statystyki/wniosek")
    public String reportSuspension(@RequestParam Integer matchId,
                                   @RequestParam Integer contractId,
                                   @RequestParam Integer statId,
                                   Principal principal,
                                   RedirectAttributes ra) {
        try {
            User referee = userService.getUserByLogin(principal.getName());
            suspensionService.createRequest(referee.getId(), contractId, statId);
            ra.addFlashAttribute("successMessage", "Wysłano wniosek o zawieszenie.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/sedzia/mecze/" + matchId + "/statystyki";
    }

    public record StatViewDto(MatchStatistic stat, String playerName) {}
}
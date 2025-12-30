package pl.pwr.football.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.pwr.football.dto.LeagueCreateDto;
import pl.pwr.football.dto.LeagueSeasonAddedDto;
import pl.pwr.football.dto.SeasonCreateDto;
import pl.pwr.football.dto.TeamAddedDto;
import pl.pwr.football.dto.TeamInLeagueDto;

import pl.pwr.football.service.*;

@Controller
public class LeagueManagementController {

    private final LeagueManagementService leagueManagementService;
    private final PersonViewService personViewService;
    private final MatchService matchService;

    // NOWE SERWISY (niezbędne do widoku manage-teams.html)
    private final TableService tableService;
    private final MatchScheduleService matchScheduleService; // Zmienione z ScheduleService na MatchScheduleService (zależnie jak masz w projekcie)
    private final UserService userService;

    // Konstruktor z wszystkimi zależnościami
    public LeagueManagementController(LeagueManagementService leagueManagementService,
                                      PersonViewService personViewService,
                                      MatchService matchService,
                                      TableService tableService,
                                      MatchScheduleService matchScheduleService,
                                      UserService userService) {
        this.leagueManagementService = leagueManagementService;
        this.personViewService = personViewService;
        this.matchService = matchService;
        this.tableService = tableService;
        this.matchScheduleService = matchScheduleService;
        this.userService = userService;
    }

    // ================= SEZONY (SEASONS) =================

    @GetMapping("/sezony")
    public String getAllSeasons(Model model) {
        model.addAttribute("seasons", leagueManagementService.getAllSeasons());
        if (!model.containsAttribute("newSeason")) {
            model.addAttribute("newSeason", new SeasonCreateDto());
        }
        return "seasons";
    }

    @GetMapping("/sezony/{seasonId}")
    public String getSeasonDetails(@PathVariable Integer seasonId, Model model) {
        var lsList = leagueManagementService.getLeagueSeasonsBySeasonId(seasonId);
        var season = leagueManagementService.getSeasonById(seasonId);

        model.addAttribute("ls", lsList);
        model.addAttribute("season", season);

        model.addAttribute("allLeagues", leagueManagementService.getAllLeagues());
        model.addAttribute("addLeagueDto", new LeagueSeasonAddedDto());

        return "season-details";
    }

    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/admin/sezony/nowy")
    public String createSeason(@Valid @ModelAttribute("newSeason") SeasonCreateDto dto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("seasons", leagueManagementService.getAllSeasons());
            return "seasons";
        }
        try {
            leagueManagementService.createSeason(dto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("seasons", leagueManagementService.getAllSeasons());
            return "seasons";
        }
        return "redirect:/sezony";
    }

    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/admin/sezony/{id}/archiwizuj")
    public String archiveSeason(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            leagueManagementService.archiveSeason(id);
            ra.addFlashAttribute("successMessage", "Sezon został zakończony.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessageRedirect", "Nie można zakończyć: " + e.getMessage());
        }
        return "redirect:/sezony";
    }

    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/admin/sezony/{seasonId}/dodaj-lige")
    public String addLeagueToSeason(@PathVariable Integer seasonId,
                                    @ModelAttribute LeagueSeasonAddedDto dto,
                                    RedirectAttributes ra) {
        try {
            leagueManagementService.addLeagueToSeason(seasonId, dto.getLeagueId());
            ra.addFlashAttribute("successMessage", "Liga dodana do sezonu.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/sezony/" + seasonId;
    }

    // ================= LIGI (LEAGUES) =================

    @GetMapping("/ligi")
    public String getAllLeagues(Model model) {
        model.addAttribute("leagues", leagueManagementService.getAllLeagues());
        if (!model.containsAttribute("newLeague")) {
            model.addAttribute("newLeague", new LeagueCreateDto());
        }
        return "leagues";
    }

    // BRAKOWAŁO TEJ METODY - HISTORIA LIGI (/ligi/1)
    // To ona obsługuje plik league-details.html, który mi pokazałeś wcześniej
    @GetMapping("/ligi/{leagueId}")
    public String getLeagueHistory(@PathVariable Integer leagueId, Model model) {
        var history = leagueManagementService.getLeagueSeasonsByLeagueId(leagueId);
        model.addAttribute("ls", history);
        return "league-details";
    }

    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/admin/ligi/nowa")
    public String createLeague(@Valid @ModelAttribute("newLeague") LeagueCreateDto dto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("leagues", leagueManagementService.getAllLeagues());
            return "leagues";
        }
        try {
            leagueManagementService.createLeague(dto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("leagues", leagueManagementService.getAllLeagues());
            return "leagues";
        }
        return "redirect:/ligi";
    }

    // ================= KONKRETNE ROZGRYWKI (LeagueSeason) =================

    // TO JEST KLUCZOWA METODA, KTÓRĄ ODKOMENTOWAŁEM I NAPRAWIŁEM
    @GetMapping("/admin/rozgrywki/{leagueSeasonId}/druzyny")
    public String getLeagueSeasonDetails(@PathVariable Integer leagueSeasonId, Model model) {
        // 1. Informacje o lidze (nagłówek)
        var view = leagueManagementService.getLeagueSeasonViewById(leagueSeasonId);
        if (view == null) return "redirect:/sezony";

        model.addAttribute("leagueSeason", view);

        // 2. Tabela drużyn (lista w HTML: ${currentTeams})
        model.addAttribute("currentTeams", tableService.getTable(leagueSeasonId));

        // 3. Wszystkie drużyny (lista w HTML: ${allTeams}) - do selecta
        model.addAttribute("allTeams", leagueManagementService.getAllTeams());

        // 4. Obiekt formularza (w HTML: th:object="${addTeamDto}")
        model.addAttribute("addTeamDto", new TeamInLeagueDto());

        // 5. Czy terminarz jest wygenerowany? (w HTML: ${hasSchedule})
        boolean hasSchedule = matchScheduleService.hasSchedule(leagueSeasonId);
        model.addAttribute("hasSchedule", hasSchedule);

        // 6. Lista trenerów (w HTML: ${allCoaches})
        model.addAttribute("allCoaches", userService.getFreeCoaches());

        // Zwracamy nazwę pliku HTML, który wkleiłeś na początku
        return "manage-teams";
    }

    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/admin/rozgrywki/{lsId}/druzyny/dodaj")
    public String addTeamToLeagueSeason(@PathVariable Integer lsId,
                                        @ModelAttribute TeamInLeagueDto dto,
                                        RedirectAttributes ra) {
        try {
            leagueManagementService.addTeamToLeagueSeason(lsId, dto.getTeamId());
            ra.addFlashAttribute("successMessage", "Drużyna dodana do rozgrywek.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/rozgrywki/{lsId}/druzyny";
    }

    // ================= DRUŻYNY (TEAMS) =================

    @GetMapping("/druzyny")
    public String getAllTeams(Model model) {
        model.addAttribute("teams", leagueManagementService.getAllTeams());
        return "teams";
    }

    @GetMapping("/druzyny/{id}")
    public String getTeamDetails(@PathVariable Integer id, Model model) {
        var team = leagueManagementService.getTeamById(id);

        // Używam metody snake_case zgodnie z Twoim serwisem PersonViewService
        var squad = personViewService.get_team_players(id);

        var lastMatches = matchService.getLast5Matches(id);
        var nextMatches = matchService.getNext5Matches(id);

        model.addAttribute("team", team);
        model.addAttribute("squad", squad);
        model.addAttribute("lastMatches", lastMatches);
        model.addAttribute("nextMatches", nextMatches);
        return "team-details";
    }

    @GetMapping("/admin/druzyny/nowa")
    public String showAddTeamForm(Model model) {
        model.addAttribute("teamDto", new TeamAddedDto());
        return "add-team";
    }

    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/admin/druzyny/nowa")
    public String addTeam(@Valid @ModelAttribute("teamDto") TeamAddedDto dto,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "add-team";
        }
        try {
            leagueManagementService.createTeam(dto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "add-team";
        }
        return "redirect:/druzyny";
    }
}
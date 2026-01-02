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
    private final TableService tableService;
    private final MatchScheduleService matchScheduleService; // Zmienione z ScheduleService na MatchScheduleService (zależnie jak masz w projekcie)
    private final UserService userService;
    private final SuspensionService suspensionService;

    // Konstruktor z wszystkimi zależnościami
    public LeagueManagementController(LeagueManagementService leagueManagementService,
                                      PersonViewService personViewService,
                                      MatchService matchService,
                                      TableService tableService,
                                      MatchScheduleService matchScheduleService,
                                      UserService userService,
                                      SuspensionService suspensionService) {
        this.leagueManagementService = leagueManagementService;
        this.personViewService = personViewService;
        this.matchService = matchService;
        this.tableService = tableService;
        this.matchScheduleService = matchScheduleService;
        this.userService = userService;
        this.suspensionService = suspensionService;
    }

    // ================= SEZONY (SEASONS) =================

    @GetMapping("/dom/sezony")
    public String getAllSeasons(Model model) {
        model.addAttribute("seasons", leagueManagementService.getAllSeasons());
        if (!model.containsAttribute("newSeason")) {
            model.addAttribute("newSeason", new SeasonCreateDto());
        }
        return "seasons";
    }

    @GetMapping("/dom/sezony/{seasonId}")
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
        return "redirect:/dom/sezony";
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
        return "redirect:/dom/sezony";
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
        return "redirect:/dom/sezony/" + seasonId;
    }

    // ================= LIGI (LEAGUES) =================

    @GetMapping("/dom/ligi")
    public String getAllLeagues(Model model) {
        model.addAttribute("leagues", leagueManagementService.getAllLeagues());
        if (!model.containsAttribute("newLeague")) {
            model.addAttribute("newLeague", new LeagueCreateDto());
        }
        return "leagues";
    }

    // BRAKOWAŁO TEJ METODY - HISTORIA LIGI (/ligi/1)
    // To ona obsługuje plik league-details.html, który mi pokazałeś wcześniej
    @GetMapping("/dom/ligi/{leagueId}")
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
        return "redirect:/dom/ligi";
    }

    // ================= KONKRETNE ROZGRYWKI (LeagueSeason) =================

    // TO JEST KLUCZOWA METODA, KTÓRĄ ODKOMENTOWAŁEM I NAPRAWIŁEM
    @GetMapping("/admin/rozgrywki/{leagueSeasonId}/druzyny")
    public String getLeagueSeasonDetails(@PathVariable Integer leagueSeasonId, Model model) {
        var view = leagueManagementService.getLeagueSeasonViewById(leagueSeasonId);
        if (view == null) return "redirect:/dom/sezony";
        model.addAttribute("leagueSeason", view);

        model.addAttribute("currentTeams", tableService.getTable(leagueSeasonId));

        model.addAttribute("allTeams", leagueManagementService.getAllTeams());
        model.addAttribute("allCoaches", userService.getFreeCoaches());
        model.addAttribute("addTeamDto", new TeamInLeagueDto());
        boolean hasSchedule = matchScheduleService.hasSchedule(leagueSeasonId);
        model.addAttribute("hasSchedule", hasSchedule);
        model.addAttribute("allReferees", personViewService.getAllReferees());
        if (hasSchedule) {
            var schedule = matchService.getMatchesByLeague(leagueSeasonId);
            model.addAttribute("schedule", schedule);
        }

        return "manage-teams";
    }

    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/admin/rozgrywki/{leagueSeasonId}/mecze/decyzja")
    public String confirmMatchDate(@PathVariable Integer leagueSeasonId,
                                   @RequestParam Integer matchId,
                                   @RequestParam boolean accepted,
                                   RedirectAttributes ra) {
        try {
            matchService.adminDecideDate(matchId, accepted);
            ra.addFlashAttribute("successMessage", accepted ? "Zatwierdzono nową datę meczu." : "Odrzucono propozycję.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/rozgrywki/" + leagueSeasonId + "/druzyny";
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

    @GetMapping("/dom/druzyny")
    public String getAllTeams(Model model) {
        model.addAttribute("teams", leagueManagementService.getAllTeams());
        return "teams";
    }

    @GetMapping("/dom/druzyny/{id}")
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
        return "redirect:/dom/druzyny";
    }

    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/admin/rozgrywki/{leagueSeasonId}/mecze/sedzia")
    public String assignRefereeToMatch(@PathVariable Integer leagueSeasonId,
                                       @RequestParam Integer matchId,
                                       @RequestParam(required = false) Integer refereeId,
                                       RedirectAttributes ra) {
        try {
            // Jeśli refereeId jest null (np. wybrano opcję "Brak"), to też obsłużymy w serwisie (lub tu sprawdzamy)
            if (refereeId == null) {
                throw new IllegalArgumentException("Wybierz sędziego z listy.");
            }
            matchService.assignReferee(matchId, refereeId);
            ra.addFlashAttribute("successMessage", "Przydzielono sędziego do meczu.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/rozgrywki/" + leagueSeasonId + "/druzyny";
    }

    @GetMapping("/admin/wnioski")
    public String showSuspensionRequests(Model model) {
        // Pobieramy gotowe dane z widoku (Status 0 = Oczekujące)
        var requests = suspensionService.getPendingRequestViews();
        model.addAttribute("requests", requests);
        return "admin-suspensions";
    }

    @PostMapping("/admin/wnioski/decyzja")
    public String decideSuspension(@RequestParam Integer requestId,
                                   @RequestParam boolean accepted,
                                   RedirectAttributes ra) {
        try {
            suspensionService.resolveRequest(requestId, accepted);
            if (accepted) {
                ra.addFlashAttribute("successMessage", "Wniosek zatwierdzony. Zawodnik zawieszony na następny mecz.");
            } else {
                ra.addFlashAttribute("infoMessage", "Wniosek odrzucony.");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Błąd: " + e.getMessage());
        }
        return "redirect:/admin/wnioski";
    }
}
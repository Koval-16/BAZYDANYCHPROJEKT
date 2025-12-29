package pl.pwr.football.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pwr.football.dto.TeamInLeagueDto;
import pl.pwr.football.service.LeagueSeasonViewService;
import pl.pwr.football.service.MatchScheduleService;
import pl.pwr.football.service.TableService;
import pl.pwr.football.service.TeamInLeagueService;

@Controller
public class TableController {
    private final TeamInLeagueService teamInLeagueService;
    private final TableService tableService; // Żeby wyświetlić kto już jest w lidze
    private final LeagueSeasonViewService leagueSeasonViewService;
    @Autowired // lub w konstruktorze
    private MatchScheduleService scheduleService;

    public TableController(TeamInLeagueService teamInLeagueService, TableService tableService, LeagueSeasonViewService leagueSeasonViewService){
        this.teamInLeagueService = teamInLeagueService;
        this.tableService = tableService;
        this.leagueSeasonViewService = leagueSeasonViewService;
    }

    @GetMapping("/rozgrywki/{id}")
    public String get_all_seasons(@PathVariable Integer id, Model model) {
        var table = tableService.get_table(id);
        model.addAttribute("table", table);
        return "table";
    }

    @PreAuthorize("hasRole('Administrator')")
    @GetMapping("/admin/rozgrywki/{id}/druzyny")
    public String showManageTeams(@PathVariable Integer id, Model model) {
        // 1. Pobierz info o lidze (nagłówek)
        var ls = leagueSeasonViewService.findById(id); // Musisz mieć taką metodę w serwisie widoku zwracającą pojedynczy obiekt lub listę z 1 el.
        if (ls == null) {
            return "redirect:/sezony";
        }
        model.addAttribute("leagueSeason", ls);

        // 2. Lista już dodanych drużyn (tabela)
        model.addAttribute("currentTeams", tableService.get_table(id));

        // 3. Lista wszystkich dostępnych drużyn (do selecta)
        model.addAttribute("allTeams", teamInLeagueService.getAllTeams());

        // 4. Obiekt formularza
        model.addAttribute("addTeamDto", new TeamInLeagueDto());

        boolean hasSchedule = scheduleService.hasSchedule(id); // Dodaj metodę w serwisie, która deleguje do repo
        model.addAttribute("hasSchedule", hasSchedule);

        return "manage-teams";
    }

    // Akcja dodawania
    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/admin/rozgrywki/{id}/druzyny/dodaj")
    public String addTeam(@PathVariable Integer id,
                          @ModelAttribute TeamInLeagueDto dto,
                          RedirectAttributes ra) {
        try {
            teamInLeagueService.addTeamToLeague(id, dto.getTeamId());
            ra.addFlashAttribute("successMessage", "Drużyna dodana pomyślnie.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Błąd: " + e.getMessage());
        }
        return "redirect:/admin/rozgrywki/" + id + "/druzyny";
    }



    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/admin/rozgrywki/{id}/generuj-terminarz")
    public String generateSchedule(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            scheduleService.generateSchedule(id);
            ra.addFlashAttribute("successMessage", "Terminarz został wygenerowany pomyślnie! Mecze zaplanowane.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Błąd generowania: " + e.getMessage());
        }
        return "redirect:/admin/rozgrywki/" + id + "/druzyny";
    }

}

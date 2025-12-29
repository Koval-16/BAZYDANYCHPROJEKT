package pl.pwr.football.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pwr.football.dto.LeagueSeasonAddedDto;
import pl.pwr.football.dto.SeasonCreateDto;
import pl.pwr.football.repository.LeagueRepository;
import pl.pwr.football.service.LeagueSeasonService;
import pl.pwr.football.service.LeagueSeasonViewService;
import pl.pwr.football.service.SeasonService;

@Controller
public class SeasonController {

    private final SeasonService seasonService;
    private final LeagueSeasonViewService leagueSeasonViewService;
    private final LeagueRepository leagueRepository; // Dodaj to
    private final LeagueSeasonService leagueSeasonService; // Dodaj to

    public SeasonController(SeasonService seasonService, LeagueSeasonViewService leagueSeasonViewService,
                            LeagueRepository leagueRepository, LeagueSeasonService leagueSeasonService){
        this.seasonService = seasonService;
        this.leagueSeasonViewService = leagueSeasonViewService;
        this.leagueRepository = leagueRepository;
        this.leagueSeasonService = leagueSeasonService;
    }

    @GetMapping("/sezony")
    public String get_all_seasons(Model model) {
        var seasons = seasonService.get_all_seasons();
        model.addAttribute("seasons", seasons);
        if (!model.containsAttribute("newSeason")) {
            model.addAttribute("newSeason", new SeasonCreateDto());
        }
        return "seasons";
    }

    @GetMapping("/sezony/{seasonID}")
    public String get_by_season_id(@PathVariable Integer seasonID, Model model) {
        var ls = leagueSeasonViewService.get_leagueseason_by_season_id(seasonID);
        model.addAttribute("ls", ls);

        var season = seasonService.getSeasonById(seasonID);
        model.addAttribute("season", season);

        // 3. Dane do formularza
        model.addAttribute("allLeagues", leagueRepository.findAll());
        model.addAttribute("addLeagueDto", new LeagueSeasonAddedDto());
        model.addAttribute("currentSeasonId", seasonID);

        return "season-details";
    }
    // 3. F16 - Tworzenie (Tylko Admin)
    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/admin/sezony/nowy")
    public String createSeason(@Valid @ModelAttribute("newSeason") SeasonCreateDto seasonDto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("seasons", seasonService.get_all_seasons());
            return "seasons"; // Wracamy do tego samego widoku z błędami
        }
        try {
            seasonService.createSeason(seasonDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("seasons", seasonService.get_all_seasons());
            return "seasons";
        }
        return "redirect:/sezony";
    }

    // 4. F22 - Archiwizacja (Tylko Admin)
    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/admin/sezony/{id}/archiwizuj")
    public String archiveSeason(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            seasonService.archiveSeason(id);
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
            leagueSeasonService.addLeagueToSeason(seasonId, dto.getLeagueId());
            ra.addFlashAttribute("successMessage", "Liga została dodana do sezonu.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Błąd: " + e.getMessage());
        }
        return "redirect:/sezony/" + seasonId;
    }
}

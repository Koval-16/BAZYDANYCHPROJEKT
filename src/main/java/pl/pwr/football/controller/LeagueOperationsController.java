package pl.pwr.football.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pwr.football.dto.AssignCoachDto;
import pl.pwr.football.service.ContractService;
import pl.pwr.football.service.MatchScheduleService;

@Controller
@PreAuthorize("hasRole('Administrator')") // Tylko admin tu wchodzi
public class LeagueOperationsController {

    private final ContractService contractService;
    private final MatchScheduleService matchScheduleService;

    public LeagueOperationsController(ContractService contractService, MatchScheduleService matchScheduleService) {
        this.contractService = contractService;
        this.matchScheduleService = matchScheduleService;
    }

    // F13: Przypisywanie trenera do drużyny w lidze
    @PostMapping("/admin/rozgrywki/{id}/druzyny/{teamInLeagueId}/trener")
    public String assignCoach(@PathVariable Integer id,
                              @PathVariable Integer teamInLeagueId,
                              @ModelAttribute AssignCoachDto dto,
                              RedirectAttributes ra) {
        try {
            // Logika przeniesiona do ContractService
            // Uwaga: DTO ma tylko coachId, teamInLeagueId bierzemy ze ścieżki
            dto.setCoachId(dto.getCoachId()); // To już mamy z formularza
            // Musimy lekko zmodyfikować serwis lub dto, tutaj zakładam wywołanie bezpośrednie
            contractService.assignCoachToTeam(teamInLeagueId, dto.getCoachId());

            ra.addFlashAttribute("successMessage", "Trener został przypisany.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Błąd: " + e.getMessage());
        }
        return "redirect:/admin/rozgrywki/{id}/druzyny"; // Wracamy do widoku ligi
    }

    // F18: Generowanie terminarza (Algorytm Bergera)
    @PostMapping("/admin/rozgrywki/{id}/generuj-terminarz")
    public String generateSchedule(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            matchScheduleService.generateSchedule(id);
            ra.addFlashAttribute("successMessage", "Terminarz został wygenerowany pomyślnie!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Błąd generowania: " + e.getMessage());
        }
        return "redirect:/rozgrywki/" + id;
    }
}
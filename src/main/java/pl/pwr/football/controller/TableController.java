package pl.pwr.football.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.pwr.football.service.MatchService;
import pl.pwr.football.service.TableService;

@Controller
public class TableController {

    private final TableService tableService;
    private final MatchService matchService;

    public TableController(TableService tableService, MatchService matchService){
        this.tableService = tableService;
        this.matchService = matchService;
    }

    // F05: Widok tabeli ligowej
    @GetMapping("/dom/rozgrywki/{id}")
    public String getTable(@PathVariable Integer id, Model model) {
        var table = tableService.getTable(id);
        var matches = matchService.getMatchesByLeague(id);
        model.addAttribute("table", table);
        model.addAttribute("matches", matches);
        model.addAttribute("leagueSeasonId", id);
        return "table";
    }
}
package pl.pwr.football.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.pwr.football.service.TableService;

@Controller
public class TableController {
    private final TableService tableService;

    public TableController(TableService tableService){
        this.tableService = tableService;
    }

    @GetMapping("/rozgrywki/{id}")
    public String get_all_seasons(@PathVariable Integer id, Model model) {
        var table = tableService.get_table(id);
        model.addAttribute("table", table);
        return "table";
    }
}

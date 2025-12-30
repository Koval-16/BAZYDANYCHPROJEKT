package pl.pwr.football.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.pwr.football.entity.views.TableView;
import pl.pwr.football.repository.views.TableViewRepository;

import java.util.List;

@Service
public class TableService {

    private final TableViewRepository tableViewRepository;

    public TableService(TableViewRepository tableViewRepository){
        this.tableViewRepository = tableViewRepository;
    }

    public List<TableView> getTable(Integer leagueSeasonId){

        // Sortowanie: Punkty DESC -> Różnica Bramek DESC -> Bramki Zdobyte DESC
        Sort sorting = Sort.by(Sort.Direction.DESC, "points")
                .and(Sort.by(Sort.Direction.DESC, "goalDifference"))
                .and(Sort.by(Sort.Direction.DESC, "goalsScored"));

        return tableViewRepository.findByLeagueSeasonId(leagueSeasonId, sorting);
    }
}
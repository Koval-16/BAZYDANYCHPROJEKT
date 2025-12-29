package pl.pwr.football.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.pwr.football.entity.TableView;
import pl.pwr.football.repository.TableViewRepository;

import java.util.List;

@Service
public class TableService {

    private final TableViewRepository tableViewRepository;

    public TableService(TableViewRepository tableViewRepository){
        this.tableViewRepository = tableViewRepository;
    }

    public List<TableView> get_table(Integer id){

        Sort sorting = Sort.by(Sort.Direction.DESC, "punkty")
                .and(Sort.by(Sort.Direction.DESC, "goleRoznica"))
                .and(Sort.by(Sort.Direction.DESC, "goleZdobyte"));

        return tableViewRepository.findByLeagueSeasonId(id,sorting);
    }

}

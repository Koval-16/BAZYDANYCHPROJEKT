package pl.pwr.football.repository.views;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import pl.pwr.football.entity.views.TableView;
import java.util.List;


public interface TableViewRepository extends JpaRepository<TableView, Integer> {
    List<TableView> findByLeagueSeasonId(Integer id, Sort sort);
}
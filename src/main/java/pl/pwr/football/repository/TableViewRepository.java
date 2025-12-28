package pl.pwr.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import pl.pwr.football.entity.TableView;
import java.util.List;


public interface TableViewRepository extends JpaRepository<TableView, Integer> {

    List<TableView> findByLigaSezonID(Integer id, Sort sort);

}
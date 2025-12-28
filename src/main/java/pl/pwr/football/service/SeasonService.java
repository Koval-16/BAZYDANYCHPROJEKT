package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.entity.Season;
import pl.pwr.football.repository.SeasonRepository;

import java.util.List;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;

    SeasonService(SeasonRepository seasonRepository){
        this.seasonRepository = seasonRepository;
    }

    public List<Season> get_all_seasons(){
        return seasonRepository.findAll();
    }
}

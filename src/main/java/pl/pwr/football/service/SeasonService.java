package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.dto.SeasonCreateDto;
import pl.pwr.football.entity.Season;
import pl.pwr.football.repository.MatchViewRepository;
import pl.pwr.football.repository.SeasonRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;
    private final MatchViewRepository matchViewRepository;

    public SeasonService(SeasonRepository seasonRepository, MatchViewRepository matchViewRepository) {
        this.seasonRepository = seasonRepository;
        this.matchViewRepository = matchViewRepository;
    }

    public List<Season> get_all_seasons(){
        return seasonRepository.findAll();
    }

    public void createSeason(SeasonCreateDto dto) {
        if (seasonRepository.existsBySeasonIsActiveTrue()) {
            throw new IllegalStateException("Istnieje już aktywny sezon! Musisz go najpierw zakończyć.");
        }
        if (!dto.getEndDate().isAfter(dto.getStartDate())) {
            throw new IllegalArgumentException("Data końca musi być później niż data początku.");
        }

        Season season = new Season();
        season.setSeasonYear(dto.getYear());
        season.setSeasonStartDate(dto.getStartDate());
        season.setSeasonEndDate(dto.getEndDate());
        season.setSeasonIsActive(true);

        seasonRepository.save(season);
    }

    // F22: Archiwizacja
    public void archiveSeason(Integer seasonId) {
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new IllegalArgumentException("Brak sezonu o ID: " + seasonId));

        // 1. Sprawdź datę
        if (LocalDate.now().isBefore(season.getSeasonEndDate())) {
            throw new IllegalStateException("Nie można zakończyć sezonu przed datą jego końca: " + season.getSeasonEndDate());
        }

        // 2. Sprawdź mecze (używając MatchViewRepository)
        Integer unfinishedMatches = matchViewRepository.countUnfinishedMatches(seasonId);

        if (unfinishedMatches > 0) {
            throw new IllegalStateException("W sezonie wciąż są nierozegrane mecze (" + unfinishedMatches + "). Wpisz wyniki lub anuluj je.");
        }

        season.setSeasonIsActive(false);
        seasonRepository.save(season);
    }

    public Season getSeasonById(Integer id) {
        return seasonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brak sezonu"));
    }
}

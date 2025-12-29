package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.entity.League;
import pl.pwr.football.entity.LeagueSeason;
import pl.pwr.football.entity.Season;
import pl.pwr.football.repository.LeagueRepository;
import pl.pwr.football.repository.LeagueSeasonRepository;
import pl.pwr.football.repository.SeasonRepository;

@Service
public class LeagueSeasonService {

    private final LeagueSeasonRepository leagueSeasonRepository;
    private final SeasonRepository seasonRepository;
    private final LeagueRepository leagueRepository;

    public LeagueSeasonService(LeagueSeasonRepository leagueSeasonRepository,
                               SeasonRepository seasonRepository,
                               LeagueRepository leagueRepository) {
        this.leagueSeasonRepository = leagueSeasonRepository;
        this.seasonRepository = seasonRepository;
        this.leagueRepository = leagueRepository;
    }

    public void addLeagueToSeason(Integer seasonId, Integer leagueId) {
        if (!seasonRepository.existsById(seasonId)) {
            throw new IllegalArgumentException("Nie znaleziono sezonu o ID: " + seasonId);
        }
        if (!leagueRepository.existsById(leagueId)) {
            throw new IllegalArgumentException("Nie znaleziono ligi o ID: " + leagueId);
        }

        // 2. Sprawdź duplikaty (używając Integerów)
        if (leagueSeasonRepository.existsBySeasonIdAndLeagueId(seasonId, leagueId)) {
            throw new IllegalArgumentException("Ta liga jest już dodana do tego sezonu!");
        }

        // 3. Zapisz (Wpisujemy same ID)
        LeagueSeason ls = new LeagueSeason();
        ls.setSeasonId(seasonId); // Wstawiamy int
        ls.setLeagueId(leagueId); // Wstawiamy int

        leagueSeasonRepository.save(ls);
    }
}
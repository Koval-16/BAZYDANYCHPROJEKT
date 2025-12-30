package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.football.dto.LeagueCreateDto;
import pl.pwr.football.dto.SeasonCreateDto;
import pl.pwr.football.dto.TeamAddedDto;
import pl.pwr.football.entity.entities.*;
import pl.pwr.football.entity.views.LeagueSeasonView;
import pl.pwr.football.repository.entities.*;
import pl.pwr.football.repository.views.LeagueSeasonViewRepository;
import pl.pwr.football.repository.views.MatchViewRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeagueManagementService {

    // Repozytoria encji
    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final LeagueSeasonRepository leagueSeasonRepository;
    private final TeamInLeagueRepository teamInLeagueRepository;

    // Repozytoria widoków
    private final LeagueSeasonViewRepository leagueSeasonViewRepository;
    private final MatchViewRepository matchViewRepository;

    public LeagueManagementService(LeagueRepository leagueRepository,
                                   SeasonRepository seasonRepository,
                                   TeamRepository teamRepository,
                                   LeagueSeasonRepository leagueSeasonRepository,
                                   TeamInLeagueRepository teamInLeagueRepository,
                                   LeagueSeasonViewRepository leagueSeasonViewRepository,
                                   MatchViewRepository matchViewRepository) {
        this.leagueRepository = leagueRepository;
        this.seasonRepository = seasonRepository;
        this.teamRepository = teamRepository;
        this.leagueSeasonRepository = leagueSeasonRepository;
        this.teamInLeagueRepository = teamInLeagueRepository;
        this.leagueSeasonViewRepository = leagueSeasonViewRepository;
        this.matchViewRepository = matchViewRepository;
    }

    // ================= SEZONY (Ze starego SeasonService) =================

    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }

    public Season getSeasonById(Integer id) {
        return seasonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brak sezonu o ID: " + id));
    }

    public void createSeason(SeasonCreateDto dto) {
        if (seasonRepository.existsByActiveTrue()) {
            throw new IllegalStateException("Istnieje już aktywny sezon! Musisz go najpierw zakończyć.");
        }
        if (!dto.getEndDate().isAfter(dto.getStartDate())) {
            throw new IllegalArgumentException("Data końca musi być później niż data początku.");
        }

        Season season = new Season();
        season.setYear(dto.getYear());
        season.setStartDate(dto.getStartDate());
        season.setEndDate(dto.getEndDate());
        season.setActive(true);

        seasonRepository.save(season);
    }

    @Transactional
    public void archiveSeason(Integer seasonId) {
        Season season = getSeasonById(seasonId);

        // 1. Sprawdź datę
        if (LocalDate.now().isBefore(season.getEndDate())) {
            throw new IllegalStateException("Nie można zakończyć sezonu przed datą jego końca: " + season.getEndDate());
        }

        // 2. Sprawdź mecze (używając MatchViewRepository)
        Integer unfinishedMatches = matchViewRepository.countUnfinishedMatches(seasonId);
        if (unfinishedMatches > 0) {
            throw new IllegalStateException("W sezonie wciąż są nierozegrane mecze (" + unfinishedMatches + "). Wpisz wyniki lub anuluj je.");
        }

        season.setActive(false);
        seasonRepository.save(season);
    }

    // ================= LIGI (Ze starego LeagueService) =================

    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }

    public void createLeague(LeagueCreateDto dto) {
        if (leagueRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Liga o nazwie '" + dto.getName() + "' już istnieje!");
        }
        if (leagueRepository.existsByAbbreviation(dto.getAbbreviation())) {
            throw new IllegalArgumentException("Liga ze skrótem '" + dto.getAbbreviation() + "' już istnieje!");
        }

        League league = new League();
        league.setName(dto.getName());
        league.setAbbreviation(dto.getAbbreviation().toUpperCase());

        leagueRepository.save(league);
    }

    // ================= LIGA W SEZONIE (Ze starego LeagueSeasonService i ViewService) =================

    public void addLeagueToSeason(Integer seasonId, Integer leagueId) {
        if (!seasonRepository.existsById(seasonId)) {
            throw new IllegalArgumentException("Nie znaleziono sezonu o ID: " + seasonId);
        }
        if (!leagueRepository.existsById(leagueId)) {
            throw new IllegalArgumentException("Nie znaleziono ligi o ID: " + leagueId);
        }

        if (leagueSeasonRepository.existsBySeasonIdAndLeagueId(seasonId, leagueId)) {
            throw new IllegalArgumentException("Ta liga jest już dodana do tego sezonu!");
        }

        LeagueSeason ls = new LeagueSeason();
        ls.setSeasonId(seasonId);
        ls.setLeagueId(leagueId);

        leagueSeasonRepository.save(ls);
    }

    // Metody pobierające (z LeagueSeasonViewService)
    public List<LeagueSeasonView> getLeagueSeasonsByLeagueId(Integer leagueId) {
        return leagueSeasonViewRepository.findByLeagueId(leagueId);
    }

    public List<LeagueSeasonView> getLeagueSeasonsBySeasonId(Integer seasonId) {
        return leagueSeasonViewRepository.findBySeasonId(seasonId);
    }

    public LeagueSeasonView getLeagueSeasonViewById(Integer id) {
        return leagueSeasonViewRepository.findById(id).orElse(null);
    }

    // ================= DRUŻYNY (Ze starego TeamService) =================

    public List<Team> getAllTeams() {
        return teamRepository.findAllByOrderByNameAsc(); // Ujednoliciłem z metodą z TeamInLeagueService
    }

    public Team getTeamById(Integer id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono drużyny o ID: " + id));
    }

    public void createTeam(TeamAddedDto dto) {
        if (teamRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Drużyna o takiej nazwie już istnieje!");
        }
        if (teamRepository.existsByAbbreviation(dto.getAbbreviation())) {
            throw new IllegalArgumentException("Taki skrót jest już zajęty!");
        }

        Team team = new Team();
        team.setName(dto.getName());
        team.setAbbreviation(dto.getAbbreviation().toUpperCase());
        team.setFoundationDate(LocalDate.now());
        team.setKitsHome(dto.getKitsHome());
        team.setKitsAway(dto.getKitsAway());
        team.setStadium(dto.getStadium());
        team.setAddress(dto.getAddress());

        teamRepository.save(team);
    }

    // ================= DRUŻYNA W LIDZE (Ze starego TeamInLeagueService) =================

    public void addTeamToLeagueSeason(Integer leagueSeasonId, Integer teamId) {
        // Używam metody lokalnej zamiast serwisu, żeby sprawdzić status sezonu
        LeagueSeasonView lsView = leagueSeasonViewRepository.findById(leagueSeasonId).orElse(null);

        if (lsView != null && !lsView.isSeasonIsActive()) {
            throw new IllegalStateException("Nie można dodawać drużyn do zakończonego sezonu!");
        }

        if (!teamRepository.existsById(teamId)) {
            throw new IllegalArgumentException("Wybrana drużyna nie istnieje.");
        }

        if (teamInLeagueRepository.existsByLeagueSeasonIdAndTeamId(leagueSeasonId, teamId)) {
            throw new IllegalArgumentException("Ta drużyna już gra w tej lidze w tym sezonie.");
        }

        TeamInLeague til = new TeamInLeague();
        til.setLeagueSeasonId(leagueSeasonId);
        til.setTeamId(teamId);

        teamInLeagueRepository.save(til);
    }
}
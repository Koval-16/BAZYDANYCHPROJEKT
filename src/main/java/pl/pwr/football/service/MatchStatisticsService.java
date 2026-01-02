package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.football.entity.entities.Match;
import pl.pwr.football.entity.entities.MatchStatistic;
import pl.pwr.football.entity.entities.PlayerInTeam;
import pl.pwr.football.entity.entities.SuspensionRequest;
import pl.pwr.football.entity.views.PlayerStatsView;
import pl.pwr.football.repository.entities.MatchRepository;
import pl.pwr.football.repository.entities.MatchStatisticRepository;
import pl.pwr.football.repository.entities.PlayerInTeamRepository;
import pl.pwr.football.repository.views.PlayerStatsViewRepository;

import java.util.List;

@Service
public class MatchStatisticsService {

    private final MatchStatisticRepository statRepo;
    private final MatchRepository matchRepo;
    private final PlayerInTeamRepository contractRepo; // Potrzebne, żeby sprawdzić z jakiej drużyny jest piłkarz
    private final PlayerStatsViewRepository playerStatsViewRepository;
    private final SuspensionService suspensionService;

    public MatchStatisticsService(MatchStatisticRepository statRepo, MatchRepository matchRepo,
                                  SuspensionService suspensionService,
                                  PlayerInTeamRepository contractRepo, PlayerStatsViewRepository playerStatsViewRepository) {
        this.statRepo = statRepo;
        this.matchRepo = matchRepo;
        this.contractRepo = contractRepo;
        this.playerStatsViewRepository = playerStatsViewRepository;
        this.suspensionService = suspensionService;
    }

    public List<MatchStatistic> getStatsForMatch(Integer matchId) {
        return statRepo.findByMatchId(matchId);
    }

    @Transactional
    public void addStatistic(Integer matchId, Integer contractId, Integer type) {
        Match match = matchRepo.findById(matchId).orElseThrow(() -> new IllegalArgumentException("Brak meczu"));
        PlayerInTeam contract = contractRepo.findById(contractId).orElseThrow(() -> new IllegalArgumentException("Brak kontraktu"));

        List<MatchStatistic> currentStats = statRepo.findByMatchId(matchId);

        if (suspensionService.isPlayerSuspendedForMatch(contractId, matchId)) {
            throw new IllegalStateException("ZAWODNIK JEST ZAWIESZONY W TYM MECZU! Nie może grać.");
        }

        // --- WALIDACJA 1: GOL (0) ---
        if (type == 0) {
            boolean isHost = contract.getTeamInLeagueId().equals(match.getHostId());
            boolean isAway = contract.getTeamInLeagueId().equals(match.getAwayId());

            if (!isHost && !isAway) throw new IllegalStateException("Piłkarz nie gra w żadnej z drużyn tego meczu!");

            long assignedGoals = currentStats.stream()
                    .filter(s -> s.getType() == 0)
                    .filter(s -> {
                        var c = contractRepo.findById(s.getPlayerInTeamId()).orElse(null);
                        return c != null && c.getTeamInLeagueId().equals(contract.getTeamInLeagueId());
                    })
                    .count();

            int maxGoals = isHost ? match.getHomeScore() : match.getAwayScore();
            if (assignedGoals >= maxGoals) {
                throw new IllegalStateException("Limit goli wyczerpany (" + maxGoals + ")");
            }

            // WAŻNE: Aktualizacja licznika w kontrakcie (dla Widoku!)
            contract.setGoals(contract.getGoals() + 1);
        }

        // --- WALIDACJA 2: KARTKI ---
        if (type == 1) { // Żółta
            Integer yellowCount = statRepo.countByMatchIdAndPlayerInTeamIdAndType(matchId, contractId, 1);
            if (yellowCount >= 2) throw new IllegalStateException("Ten zawodnik ma już 2 żółte kartki.");

            // Aktualizacja licznika
            contract.setYellowCards(contract.getYellowCards() + 1);

            if (yellowCount == 1) {
                // Auto-czerwona
                statRepo.save(new MatchStatistic(matchId, contractId, 1)); // Druga żółta
                statRepo.save(new MatchStatistic(matchId, contractId, 2)); // Auto Czerwona

                // Aktualizacja liczników dla auto-czerwonej
                contract.setRedCards(contract.getRedCards() + 1);
                contractRepo.save(contract);
                return;
            }
        }

        if (type == 2) { // Czerwona bezpośrednia
            Integer redCount = statRepo.countByMatchIdAndPlayerInTeamIdAndType(matchId, contractId, 2);
            if (redCount >= 1) throw new IllegalStateException("Ten zawodnik ma już czerwoną kartkę.");

            // Aktualizacja licznika
            contract.setRedCards(contract.getRedCards() + 1);
        }

        // Zapis w bazie
        statRepo.save(new MatchStatistic(matchId, contractId, type));
        contractRepo.save(contract); // Zapisujemy zaktualizowany kontrakt
    }

    public List<PlayerStatsView> getTopScorers(Integer leagueSeasonId) {
        return playerStatsViewRepository.findByLeagueSeasonIdAndGoalsGreaterThanOrderByGoalsDesc(leagueSeasonId, 0);
    }

    public List<PlayerStatsView> getYellowCardsStats(Integer leagueSeasonId) {
        return playerStatsViewRepository.findByLeagueSeasonIdAndYellowCardsGreaterThanOrderByYellowCardsDesc(leagueSeasonId, 0);
    }

    public List<PlayerStatsView> getRedCardsStats(Integer leagueSeasonId) {
        return playerStatsViewRepository.findByLeagueSeasonIdAndRedCardsGreaterThanOrderByRedCardsDesc(leagueSeasonId, 0);
    }

    @Transactional
    public void removeStatistic(Integer statId) {
        statRepo.deleteById(statId);
    }
}
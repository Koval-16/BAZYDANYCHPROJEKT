package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.football.entity.Match;
import pl.pwr.football.entity.TeamInLeague;
import pl.pwr.football.repository.MatchRepository;
import pl.pwr.football.repository.TeamInLeagueRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MatchScheduleService {

    private final MatchRepository matchRepository;
    private final TeamInLeagueRepository teamInLeagueRepository;
    private final LeagueSeasonViewService leagueSeasonViewService;

    public MatchScheduleService(MatchRepository matchRepository, TeamInLeagueRepository teamInLeagueRepository, LeagueSeasonViewService leagueSeasonViewService) {
        this.matchRepository = matchRepository;
        this.teamInLeagueRepository = teamInLeagueRepository;
        this.leagueSeasonViewService = leagueSeasonViewService;
    }

    public boolean hasSchedule(Integer leagueSeasonId) {
        return matchRepository.existsByLeagueSeasonId(leagueSeasonId);
    }

    @Transactional
    public void generateSchedule(Integer leagueSeasonId) {
        // 1. Walidacja
        if (hasSchedule(leagueSeasonId)) {
            throw new IllegalStateException("Terminarz już istnieje!");
        }

        List<TeamInLeague> teams = teamInLeagueRepository.findAllByLeagueSeasonId(leagueSeasonId);
        if (teams.size() < 2) {
            throw new IllegalStateException("Za mało drużyn (min. 2).");
        }

        // 2. Pobierz daty z widoku
        var view = leagueSeasonViewService.findById(leagueSeasonId);
        LocalDate startDate = view.getSeasonDateStart();
        LocalDate endDate = view.getSeasonDateEnd();

        // 3. Algorytm Bergera
        if (teams.size() % 2 != 0) {
            teams.add(null); // Pauza (duch)
        }

        int numTeams = teams.size();
        int roundsPerLeg = numTeams - 1;
        int matchesPerRound = numTeams / 2;

        LocalDate lastMatchDate = endDate.minusDays(7);
        long totalDays = ChronoUnit.DAYS.between(startDate, lastMatchDate);
        int totalRounds = roundsPerLeg * 2;
        long daysInterval = totalDays / (totalRounds > 1 ? totalRounds - 1 : 1);

        List<Match> allMatches = new ArrayList<>();
        List<TeamInLeague> rotationList = new ArrayList<>(teams);
        TeamInLeague fixedTeam = rotationList.remove(0);

        // RUNDA 1
        for (int round = 0; round < roundsPerLeg; round++) {
            LocalDate roundDate = startDate.plusDays(round * daysInterval);

            List<TeamInLeague> currentRoundTeams = new ArrayList<>();
            currentRoundTeams.add(fixedTeam);
            currentRoundTeams.addAll(rotationList);

            generateMatchesForRound(leagueSeasonId, matchesPerRound, currentRoundTeams, roundDate, false, allMatches);
            Collections.rotate(rotationList, 1);
        }

        // RUNDA 2 (Reset rotacji)
        rotationList = new ArrayList<>(teams);
        rotationList.remove(0);

        for (int round = 0; round < roundsPerLeg; round++) {
            LocalDate roundDate = startDate.plusDays((roundsPerLeg + round) * daysInterval);

            List<TeamInLeague> currentRoundTeams = new ArrayList<>();
            currentRoundTeams.add(fixedTeam);
            currentRoundTeams.addAll(rotationList);

            generateMatchesForRound(leagueSeasonId, matchesPerRound, currentRoundTeams, roundDate, true, allMatches);
            Collections.rotate(rotationList, 1);
        }

        matchRepository.saveAll(allMatches);
    }

    private void generateMatchesForRound(Integer leagueSeasonId, int matchesPerRound, List<TeamInLeague> currentRoundTeams, LocalDate date, boolean isRematch, List<Match> allMatches) {
        int numTeams = currentRoundTeams.size();

        for (int i = 0; i < matchesPerRound; i++) {
            TeamInLeague t1 = currentRoundTeams.get(i);
            TeamInLeague t2 = currentRoundTeams.get(numTeams - 1 - i);

            // Jeśli gra "duch" -> pauza
            if (t1 == null || t2 == null) continue;

            Match match = new Match();
            match.setLeagueSeasonId(leagueSeasonId);
            match.setDate(date);
            match.setPlayed(false);

            // ZMIANA: Ustawiamy same ID (Integer)
            if (!isRematch) {
                // Runda 1
                if (i == 0) { // Fixed team logic
                    match.setHostId(t1.getId());
                    match.setAwayId(t2.getId());
                } else {
                    match.setHostId(t1.getId());
                    match.setAwayId(t2.getId());
                }
            } else {
                // Runda 2 (Rewanż - zamiana)
                match.setHostId(t2.getId());
                match.setAwayId(t1.getId());
            }
            allMatches.add(match);
        }
    }
}
package pl.pwr.football.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pwr.football.entity.entities.Match;
import pl.pwr.football.repository.entities.MatchRepository;
import pl.pwr.football.repository.views.MatchViewRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository; // Udajemy repozytorium meczów

    @Mock
    private MatchViewRepository matchViewRepository; // Udajemy widoki (wymagane przez konstruktor)

    @InjectMocks
    private MatchService matchService; // To testujemy

    // --- TEST 1: Czy gospodarz może poprawnie zaproponować datę? ---
    @Test
    void proposeMatchDate_Success() {
        // Given (Dane wejściowe)
        Integer matchId = 1;
        Integer coachTeamId = 10; // ID drużyny trenera (Gospodarza)
        LocalDate newDate = LocalDate.of(2023, 10, 10);

        Match match = new Match();
        match.setId(matchId);
        match.setHostId(coachTeamId); // Ustawiamy, że ten trener jest gospodarzem
        match.setProposalStatus(0);   // Status początkowy

        // Mówimy mockowi: "Jak ktoś zapyta o mecz ID 1, zwróć ten obiekt wyżej"
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));

        // When (Akcja)
        matchService.proposeMatchDate(matchId, newDate, coachTeamId);

        // Then (Weryfikacja)
        assertEquals(1, match.getProposalStatus(), "Status powinien zmienić się na 1 (Propozycja wysłana)");
        assertEquals(newDate, match.getProposedDate(), "Data propozycji powinna zostać zapisana");
        verify(matchRepository).save(match); // Sprawdzamy, czy zapisano zmiany w bazie
    }

    // --- TEST 2: Czy system blokuje trenera gości przed zmianą daty? ---
    @Test
    void proposeMatchDate_Fail_WrongTeam() {
        // Given
        Integer matchId = 1;
        Integer hostTeamId = 10;
        Integer guestCoachTeamId = 20; // To jest trener GOŚCI, nie gospodarzy

        Match match = new Match();
        match.setHostId(hostTeamId);

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));

        // When & Then (Oczekujemy błędu)
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            matchService.proposeMatchDate(matchId, LocalDate.now(), guestCoachTeamId);
        });

        assertEquals("Tylko trener gospodarzy może zaproponować datę.", exception.getMessage());
    }

    // --- TEST 3: Czy wpisanie wyniku meczu działa poprawnie? ---
    @Test
    void submitMatchResult_Success() {
        // Given
        Match match = new Match();
        when(matchRepository.findById(1)).thenReturn(Optional.of(match));

        // When
        matchService.submitMatchResult(1, 2, 1); // Wynik 2:1

        // Then
        assertEquals(2, match.getHomeScore());
        assertEquals(1, match.getAwayScore());
        assertTrue(match.isPlayed());
        assertEquals(3, match.getProposalStatus()); // 3 = Zatwierdzone/Zagrane
    }

    // --- TEST 4: Czy system blokuje ujemne wyniki? ---
    @Test
    void submitMatchResult_Fail_NegativeScore() {
        // Given
        Match match = new Match();
        when(matchRepository.findById(1)).thenReturn(Optional.of(match));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            matchService.submitMatchResult(1, -5, 0);
        });
    }
}
package br.com.af.pokerchase.service;


import br.com.af.pokerchase.common.exception.TournamentNotFoundException;
import br.com.af.pokerchase.domain.Tournament;
import br.com.af.pokerchase.domain.TournamentStatus;
import br.com.af.pokerchase.repository.TournamentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class TournamentServiceTest {

  @Mock
  private TournamentRepository tournamentRepository;


  @InjectMocks
  private TournamentService tournamentService;

  @Test
  void startTournament_Success() {
    // Dado um torneio válido com jogadores suficientes
    Tournament tournament = new Tournament();
    tournament.setId("1");
    tournament.setStatus(TournamentStatus.REGISTERING);
    tournament.setPlayerIds(List.of("1", "2", "3", "4", "5"));

    when(tournamentRepository.findById("1")).thenReturn(Optional.of(tournament));
    when(tournamentRepository.save(any(Tournament.class))).thenReturn(tournament);

    // Quando o torneio é iniciado
    tournamentService.startTournament("1", List.of("1", "2", "3", "4", "5"));

    Tournament startedTournament = tournamentRepository.findById("1").get();

    // Então o status deve ser ACTIVE
    assertEquals(TournamentStatus.IN_PROGRESS, startedTournament.getStatus());
    verify(tournamentRepository, times(1)).save(tournament);
  }

  @Test
  void startTournament_ThrowsException_WhenTournamentNotFound() {
    when(tournamentRepository.findById("1")).thenReturn(Optional.empty());

    assertThrows(TournamentNotFoundException.class, () -> {
      tournamentService.startTournament("1", List.of("1", "2", "3", "4", "5"));
    });
  }

  @Test
  void endTournament_Success() {
    Tournament tournament = new Tournament();
    tournament.setId("1");
    tournament.setStatus(TournamentStatus.IN_PROGRESS);

    when(tournamentRepository.findById("1")).thenReturn(Optional.of(tournament));
    when(tournamentRepository.save(any(Tournament.class))).thenReturn(tournament);

    tournamentService.endTournament("1");

    Tournament endedTournament = tournamentRepository.findById("1").get();

    assertEquals(TournamentStatus.COMPLETED, endedTournament.getStatus());
    verify(tournamentRepository, times(1)).save(tournament);
  }
}
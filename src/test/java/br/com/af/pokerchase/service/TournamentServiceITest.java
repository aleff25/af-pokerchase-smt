package br.com.af.pokerchase.service;

import br.com.af.pokerchase.domain.Player;
import br.com.af.pokerchase.domain.Tournament;
import br.com.af.pokerchase.domain.TournamentStatus;
import br.com.af.pokerchase.repository.TournamentRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TournamentServiceITest {

  private static Long tournamentId;
  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private TournamentRepository tournamentRepository;
  @LocalServerPort
  private int port;

  @Test
  @Order(1)
  void createTournament() {
    Tournament tournament = new Tournament();
    ResponseEntity<Tournament> response = restTemplate.postForEntity(
      "http://localhost:" + port + "/tournaments",
      tournament,
      Tournament.class
    );

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody().getId());
    tournamentId = Long.valueOf(response.getBody().getId());
  }

  @Test
  @Order(2)
  void addPlayersToTournament() {
    Player player1 = new Player();
    player1.setId("Jogador 1");

    Player player2 = new Player();
    player2.setId("Jogador 2");

    restTemplate.postForEntity(
      "http://localhost:" + port + "/tournaments/" + tournamentId + "/players",
      player1,
      Void.class
    );

    restTemplate.postForEntity(
      "http://localhost:" + port + "/tournaments/" + tournamentId + "/players",
      player2,
      Void.class
    );

    Tournament tournament = tournamentRepository.findById(String.valueOf(tournamentId)).orElseThrow();
    assertEquals(2, tournament.getPlayerIds().size());
  }

  @Test
  @Order(3)
  void startTournament() {
    ResponseEntity<Tournament> response = restTemplate.postForEntity(
      "http://localhost:" + port + "/tournaments/" + tournamentId + "/start",
      null,
      Tournament.class
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(TournamentStatus.IN_PROGRESS, response.getBody().getStatus());
  }

  @Test
  @Order(4)
  void endTournament() {
    ResponseEntity<Tournament> response = restTemplate.postForEntity(
      "http://localhost:" + port + "/tournaments/" + tournamentId + "/end",
      null,
      Tournament.class
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(TournamentStatus.COMPLETED, response.getBody().getStatus());
  }
}

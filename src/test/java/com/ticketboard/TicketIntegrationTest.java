package com.ticketboard;

import com.ticketboard.model.Ticket;
import com.ticketboard.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketIntegrationTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate rest;

  @Autowired
  private TicketRepository repo;

  @Test
  void createAndGetTicket() {
    Ticket t = new Ticket("ititle","idesc","LOW","OPEN","tester", LocalDateTime.now());
    ResponseEntity<Ticket> resp = rest.postForEntity("http://localhost:" + port + "/tickets", t, Ticket.class);
    assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    Ticket created = resp.getBody();
    assertThat(created.getId()).isNotNull();
    ResponseEntity<Ticket> fetched = rest.getForEntity("http://localhost:" + port + "/tickets/" + created.getId(), Ticket.class);
    assertThat(fetched.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}

package com.ticketboard.controller;

import com.ticketboard.model.Ticket;
import com.ticketboard.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
  private final TicketService svc;
  public TicketController(TicketService svc) { this.svc = svc; }

  @GetMapping
  public List<Ticket> all() { return svc.findAll(); }

  @GetMapping("/{id}")
  public ResponseEntity<Ticket> get(@PathVariable Long id) {
    return svc.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Ticket> create(@RequestBody Ticket ticket) {
    Ticket saved = svc.create(ticket);
    return ResponseEntity.ok(saved);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Ticket> update(@PathVariable Long id, @RequestBody Ticket ticket) {
    return svc.update(id, ticket).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    boolean ok = svc.delete(id);
    return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
}

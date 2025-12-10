package com.ticketboard.service;

import com.ticketboard.model.Ticket;
import com.ticketboard.repository.TicketRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
  private final TicketRepository repo;
  public TicketService(TicketRepository repo) { this.repo = repo; }

  public List<Ticket> findAll() { return repo.findAll(); }

  public Optional<Ticket> findById(Long id) { return repo.findById(id); }

  public Ticket create(Ticket t) {
    t.setStatus(t.getStatus() == null ? "OPEN" : t.getStatus());
    t.setCreatedAt(LocalDateTime.now());
    return repo.save(t);
  }

  public Optional<Ticket> update(Long id, Ticket t) {
    return repo.findById(id).map(existing -> {
      existing.setTitle(t.getTitle());
      existing.setDescription(t.getDescription());
      existing.setPriority(t.getPriority());
      existing.setStatus(t.getStatus());
      existing.setCreatedBy(t.getCreatedBy());
      return repo.save(existing);
    });
  }

  public boolean delete(Long id) {
    return repo.findById(id).map(t -> {
      repo.deleteById(id);
      return true;
    }).orElse(false);
  }
}

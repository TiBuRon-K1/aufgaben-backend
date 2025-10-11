package com.example.aufgaben_backend.controller;

import com.example.aufgaben_backend.model.CardStatus;
import com.example.aufgaben_backend.model.ProjectCard;
import com.example.aufgaben_backend.repository.ProjectCardRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/kanban/cards")
@CrossOrigin(origins = "*")
public class KanbanController {

    private final ProjectCardRepository repo;

    public KanbanController(ProjectCardRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<ProjectCard> all() {
        List<ProjectCard> out = new ArrayList<>(repo.findAll());
        out.sort(Comparator.comparing(ProjectCard::getStatus)
                .thenComparing(c -> c.getPosition() == null ? 0 : c.getPosition()));
        return out;
    }

    @PostMapping
    public ProjectCard create(@RequestBody ProjectCard in) {
        // дефолты
        if (in.getStatus() == null) in.setStatus(CardStatus.IDEEN);
        if (in.getPosition() == null) in.setPosition(0);
        return repo.save(in);
    }

    @GetMapping("/{id}")
    public ProjectCard one(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public ProjectCard update(@PathVariable Long id, @RequestBody ProjectCard in) {
        ProjectCard card = repo.findById(id).orElseThrow();
        if (in.getText() != null) card.setText(in.getText());
        if (in.getColor() != null) card.setColor(in.getColor());
        if (in.getStatus() != null) card.setStatus(in.getStatus());
        if (in.getPosition() != null) card.setPosition(in.getPosition());
        return repo.save(card);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}

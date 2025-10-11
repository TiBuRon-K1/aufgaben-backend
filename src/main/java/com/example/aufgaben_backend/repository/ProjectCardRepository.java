package com.example.aufgaben_backend.repository;

import com.example.aufgaben_backend.model.ProjectCard;
import com.example.aufgaben_backend.model.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectCardRepository extends JpaRepository<ProjectCard, Long> {
    List<ProjectCard> findAllByStatusOrderByPositionAsc(CardStatus status);
}

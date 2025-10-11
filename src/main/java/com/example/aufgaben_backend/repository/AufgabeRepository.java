package com.example.aufgaben_backend.repository;

import com.example.aufgaben_backend.model.Aufgabe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AufgabeRepository extends JpaRepository<Aufgabe, Long> {
}

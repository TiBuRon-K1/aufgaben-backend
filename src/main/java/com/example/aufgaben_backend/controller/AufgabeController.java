package com.example.aufgaben_backend.controller;

import com.example.aufgaben_backend.model.Aufgabe;
import com.example.aufgaben_backend.repository.AufgabeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/aufgaben")
public class AufgabeController {

    private final AufgabeRepository aufgabeRepository;

    public AufgabeController(AufgabeRepository aufgabeRepository) {
        this.aufgabeRepository = aufgabeRepository;
    }

    @GetMapping("/{id}")
    public Aufgabe eineAufgabe(@PathVariable("id") Long id) {
        return aufgabeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aufgabe nicht gefunden"));
    }

    @PutMapping("/{id}")
    public Aufgabe aufgabeAktualisieren(@PathVariable("id") Long id, @RequestBody Aufgabe aktualisiert) {
        return aufgabeRepository.findById(id).map(a -> {
            if (aktualisiert.getTitel() != null) a.setTitel(aktualisiert.getTitel());
            if (aktualisiert.getBeschreibung() != null) a.setBeschreibung(aktualisiert.getBeschreibung());
            a.setErledigt(aktualisiert.getErledigt());
            if (aktualisiert.getDatum() != null) a.setDatum(aktualisiert.getDatum());
            if (aktualisiert.getZeit() != null) a.setZeit(aktualisiert.getZeit());
            return aufgabeRepository.save(a);
        }).orElseThrow(() -> new RuntimeException("Aufgabe nicht gefunden"));
    }

    @DeleteMapping("/{id}")
    public void aufgabeLöschen(@PathVariable("id") Long id) {
        aufgabeRepository.deleteById(id);

    }
}

package com.example.aufgaben_backend.controller;

import com.example.aufgaben_backend.model.Aufgabe;
import com.example.aufgaben_backend.repository.AufgabeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aufgaben")
@CrossOrigin(origins = "*")
public class AufgabeController {

    private final AufgabeRepository aufgabeRepository;

    public AufgabeController(AufgabeRepository aufgabeRepository) {
        this.aufgabeRepository = aufgabeRepository;
    }

    @GetMapping
    public List<Aufgabe> alleAufgaben() {
        return aufgabeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Aufgabe eineAufgabe(@PathVariable Long id) {
        return aufgabeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aufgabe nicht gefunden"));
    }

    @PostMapping
    public Aufgabe neueAufgabe(@RequestBody Aufgabe aufgabe) {
        if (aufgabe.getBeschreibung() == null) {
            aufgabe.setBeschreibung("");
        }
        return aufgabeRepository.save(aufgabe);
    }

    @PutMapping("/{id}")
    public Aufgabe aufgabeAktualisieren(@PathVariable Long id, @RequestBody Aufgabe aktualisiert) {
        return aufgabeRepository.findById(id).map(a -> {
            // ✅ Обновляем только те поля, которые реально пришли
            if (aktualisiert.getTitel() != null) a.setTitel(aktualisiert.getTitel());
            if (aktualisiert.getBeschreibung() != null) a.setBeschreibung(aktualisiert.getBeschreibung());
            a.setErledigt(aktualisiert.isErledigt());
            return aufgabeRepository.save(a);
        }).orElseThrow(() -> new RuntimeException("Aufgabe nicht gefunden"));
    }

    @DeleteMapping("/{id}")
    public void aufgabeLöschen(@PathVariable Long id) {
        aufgabeRepository.deleteById(id);
    }
}

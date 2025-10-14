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

    // 📋 Все задачи
    @GetMapping
    public List<Aufgabe> alleAufgaben() {
        return aufgabeRepository.findAll();
    }

    // 🔍 Одна задача по ID
    @GetMapping("/{id}")
    public Aufgabe eineAufgabe(@PathVariable Long id) {
        return aufgabeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aufgabe nicht gefunden"));
    }

    // ➕ Создать новую задачу
    @PostMapping
    public Aufgabe neueAufgabe(@RequestBody Aufgabe aufgabe) {
        if (aufgabe.getBeschreibung() == null) {
            aufgabe.setBeschreibung("");
        }
        if (aufgabe.getErledigt() == null) {
            aufgabe.setErledigt(false);
        }
        return aufgabeRepository.save(aufgabe);
    }

    // ✏️ Обновить задачу (например, отметить выполненной)
    @PutMapping("/{id}")
    public Aufgabe aufgabeAktualisieren(@PathVariable Long id, @RequestBody Aufgabe aktualisiert) {
        return aufgabeRepository.findById(id).map(a -> {
            if (aktualisiert.getTitel() != null) {
                a.setTitel(aktualisiert.getTitel());
            }
            if (aktualisiert.getBeschreibung() != null) {
                a.setBeschreibung(aktualisiert.getBeschreibung());
            }
            if (aktualisiert.getDatum() != null) {
                a.setDatum(aktualisiert.getDatum());
            }
            if (aktualisiert.getZeit() != null) {
                a.setZeit(aktualisiert.getZeit());
            }
            if (aktualisiert.getErledigt() != null) {
                a.setErledigt(aktualisiert.getErledigt()); // ✅ заменили isErledigt() → getErledigt()
            }
            return aufgabeRepository.save(a);
        }).orElseThrow(() -> new RuntimeException("Aufgabe nicht gefunden"));
    }

    // 🗑️ Удалить задачу
    @DeleteMapping("/{id}")
    public void aufgabeLöschen(@PathVariable Long id) {
        aufgabeRepository.deleteById(id);
    }
}

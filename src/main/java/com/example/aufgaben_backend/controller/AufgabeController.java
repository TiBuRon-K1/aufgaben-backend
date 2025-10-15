package com.example.aufgaben_backend.controller;

import com.example.aufgaben_backend.model.Aufgabe;
import com.example.aufgaben_backend.repository.AufgabeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aufgaben")
@CrossOrigin(origins = "*") // <-- чтобы фронтенд мог обращаться к API
public class AufgabeController {

    private final AufgabeRepository aufgabeRepository;

    public AufgabeController(AufgabeRepository aufgabeRepository) {
        this.aufgabeRepository = aufgabeRepository;
    }

    // ✅ Получить все задачи
    @GetMapping
    public List<Aufgabe> alleAufgaben() {
        return aufgabeRepository.findAll();
    }

    // ✅ Добавить новую задачу
    @PostMapping
    public Aufgabe neueAufgabe(@RequestBody Aufgabe aufgabe) {
        return aufgabeRepository.save(aufgabe);
    }

    // ✅ Получить задачу по id
    @GetMapping("/{id}")
    public Aufgabe eineAufgabe(@PathVariable("id") Long id) {
        return aufgabeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aufgabe nicht gefunden"));
    }

    // ✅ Обновить задачу
    @PutMapping("/{id}")
    public Aufgabe aufgabeAktualisieren(@PathVariable("id") Long id, @RequestBody Aufgabe aktualisiert) {
        return aufgabeRepository.findById(id).map(a -> {
            if (aktualisiert.getTitel() != null) a.setTitel(aktualisiert.getTitel());
            if (aktualisiert.getBeschreibung() != null) a.setBeschreibung(aktualisiert.getBeschreibung());
            if (aktualisiert.getErledigt() != null) a.setErledigt(aktualisiert.getErledigt());
            if (aktualisiert.getDatum() != null) a.setDatum(aktualisiert.getDatum());
            if (aktualisiert.getZeit() != null) a.setZeit(aktualisiert.getZeit());
            return aufgabeRepository.save(a);
        }).orElseThrow(() -> new RuntimeException("Aufgabe nicht gefunden"));
    }

    // ✅ Удалить задачу
    @DeleteMapping("/{id}")
    public void aufgabeLöschen(@PathVariable("id") Long id) {
        aufgabeRepository.deleteById(id);
    }
}

package com.example.aufgaben_backend.controller;

import com.example.aufgaben_backend.model.Aufgabe;
import com.example.aufgaben_backend.repository.AufgabeRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping("/api/aufgaben")
@CrossOrigin(origins = "*")
public class AufgabeController {

    private final AufgabeRepository aufgabeRepository;

    public AufgabeController(AufgabeRepository aufgabeRepository) {
        this.aufgabeRepository = aufgabeRepository;
    }

    @GetMapping
    public java.util.List<Aufgabe> alleAufgaben() {
        return aufgabeRepository.findAll();
    }

    @PostMapping
    public Aufgabe neueAufgabe(@RequestBody Aufgabe aufgabe) {
        if (aufgabe.getBeschreibung() == null) aufgabe.setBeschreibung("");
        if (aufgabe.getErledigt() == null) aufgabe.setErledigt(false);
        return aufgabeRepository.save(aufgabe);
    }

    // ✅ Исправленный метод обновления
    @PutMapping("/{id}")
    public Aufgabe aufgabeAktualisieren(@PathVariable Long id, @RequestBody Map<String, Object> in) {
        return aufgabeRepository.findById(id).map(a -> {
            try {
                if (in.containsKey("titel")) a.setTitel((String) in.get("titel"));
                if (in.containsKey("beschreibung")) a.setBeschreibung((String) in.get("beschreibung"));

                if (in.containsKey("erledigt")) {
                    Object val = in.get("erledigt");
                    if (val instanceof Boolean b) a.setErledigt(b);
                    else if (val instanceof String s) a.setErledigt(Boolean.parseBoolean(s));
                }

                if (in.containsKey("datum")) {
                    String d = (String) in.get("datum");
                    a.setDatum((d == null || d.isBlank()) ? null : LocalDate.parse(d));
                }

                if (in.containsKey("zeit")) {
                    String z = (String) in.get("zeit");
                    a.setZeit((z == null || z.isBlank()) ? null : LocalTime.parse(z));
                }

                return aufgabeRepository.save(a);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Fehler beim Aktualisieren der Aufgabe", e);
            }
        }).orElseThrow(() -> new RuntimeException("Aufgabe nicht gefunden"));
    }

    @DeleteMapping("/{id}")
    public void aufgabeLöschen(@PathVariable Long id) {
        aufgabeRepository.deleteById(id);
    }
}

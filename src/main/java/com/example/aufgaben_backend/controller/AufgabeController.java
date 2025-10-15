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

    // 🔹 Все задачи
    @GetMapping
    public List<Aufgabe> alleAufgaben() {
        return aufgabeRepository.findAll();
    }

    // 🔹 Создать новую задачу
    @PostMapping
    public Aufgabe neueAufgabe(@RequestBody Aufgabe aufgabe) {
        if (aufgabe.getErledigt() == null) {
            aufgabe.setErledigt(false);
        }
        System.out.println("🟢 Neue Aufgabe: " + aufgabe);
        return aufgabeRepository.save(aufgabe);
    }

    // 🔹 Обновить задачу (галочка)
    @PutMapping("/{id}")
    public Aufgabe aufgabeAktualisieren(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        System.out.println("🟢 PUT AUFGERUFEN: id=" + id + ", body=" + body);

        Optional<Aufgabe> optionalAufgabe = aufgabeRepository.findById(id);
        if (optionalAufgabe.isEmpty()) {
            throw new RuntimeException("Aufgabe mit ID " + id + " nicht gefunden");
        }

        Aufgabe a = optionalAufgabe.get();

        if (body.containsKey("titel")) a.setTitel((String) body.get("titel"));
        if (body.containsKey("beschreibung")) a.setBeschreibung((String) body.get("beschreibung"));
        if (body.containsKey("datum")) a.setDatum(body.get("datum") != null ? java.time.LocalDate.parse((String) body.get("datum")) : null);
        if (body.containsKey("zeit")) a.setZeit(body.get("zeit") != null ? java.time.LocalTime.parse((String) body.get("zeit")) : null);
        if (body.containsKey("erledigt")) a.setErledigt((Boolean) body.get("erledigt"));

        return aufgabeRepository.save(a);
    }

    // 🔹 Удалить задачу
    @DeleteMapping("/{id}")
    public void aufgabeLöschen(@PathVariable Long id) {
        System.out.println("🗑️ DELETE AUFGERUFEN: id=" + id);
        aufgabeRepository.deleteById(id);
    }
}

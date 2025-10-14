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

    private final AufgabeRepository repo;

    public AufgabeController(AufgabeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public java.util.List<Aufgabe> alle() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Aufgabe eine(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Aufgabe neu(@RequestBody Aufgabe in) {
        // дефолты, чтобы не ловить NPE
        if (in.getBeschreibung() == null) in.setBeschreibung("");
        if (in.getErledigt() == null) in.setErledigt(false);
        return repo.save(in);
    }

    /**
     * Частичное обновление. Принимаем любой json и аккуратно применяем.
     */
    @PutMapping("/{id}")
    public Aufgabe update(@PathVariable Long id, @RequestBody Map<String, Object> in) {
        Aufgabe a = repo.findById(id).orElseThrow();

        if (in.containsKey("titel")) {
            Object v = in.get("titel");
            a.setTitel(v == null ? null : String.valueOf(v));
        }
        if (in.containsKey("beschreibung")) {
            Object v = in.get("beschreibung");
            String s = (v == null) ? "" : String.valueOf(v);
            a.setBeschreibung(s);
        }
        if (in.containsKey("erledigt")) {
            Object v = in.get("erledigt");
            if (v instanceof Boolean b) {
                a.setErledigt(b);
            } else if (v != null) {
                a.setErledigt(Boolean.parseBoolean(String.valueOf(v)));
            } else {
                a.setErledigt(false);
            }
        }
        if (in.containsKey("datum")) {
            Object v = in.get("datum");
            if (v == null) {
                a.setDatum(null);
            } else {
                String s = String.valueOf(v).trim();
                a.setDatum(s.isEmpty() ? null : LocalDate.parse(s)); // ожидаем YYYY-MM-DD
            }
        }
        if (in.containsKey("zeit")) {
            Object v = in.get("zeit");
            if (v == null) {
                a.setZeit(null);
            } else {
                String s = String.valueOf(v).trim();
                a.setZeit(s.isEmpty() ? null : parseTimeFlexible(s)); // примем HH:mm или HH:mm:ss
            }
        }
        return repo.save(a);
    }

    /**
     * Узкоспециализированный endpoint — менять только флаг erledigt.
     * Фронт можешь не трогать, но будет полезно для теста через curl/браузер.
     */
    @PatchMapping("/{id}/erledigt")
    public Aufgabe setErledigt(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Aufgabe a = repo.findById(id).orElseThrow();
        Object v = body.get("erledigt");
        boolean flag = (v instanceof Boolean b) ? b : Boolean.parseBoolean(String.valueOf(v));
        a.setErledigt(flag);
        return repo.save(a);
    }

    @DeleteMapping("/{id}")
    public void loeschen(@PathVariable Long id) {
        repo.deleteById(id);
    }

    // ===== helpers =====

    /** Принимает "HH:mm" или "HH:mm:ss" */
    private LocalTime parseTimeFlexible(String s) {
        try {
            if (s.length() == 5) {            // HH:mm
                return LocalTime.parse(s + ":00");
            }
            return LocalTime.parse(s);        // HH:mm:ss
        } catch (Exception e) {
            // на всякий случай не валим обработку — просто очистим поле
            return null;
        }
    }
}

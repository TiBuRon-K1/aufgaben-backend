package com.example.aufgaben_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Aufgabe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titel;

    @Column(length = 1000)
    private String beschreibung = "";

    private Boolean erledigt = false;  // ✅ Boolean вместо boolean

    private LocalDate datum;
    private LocalTime zeit;

    public Aufgabe() {}

    public Aufgabe(String titel, String beschreibung, Boolean erledigt, LocalDate datum, LocalTime zeit) {
        this.titel = titel;
        this.beschreibung = (beschreibung != null) ? beschreibung : "";
        this.erledigt = (erledigt != null) ? erledigt : false;
        this.datum = datum;
        this.zeit = zeit;
    }

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return (beschreibung != null) ? beschreibung : "";
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = (beschreibung != null) ? beschreibung : "";
    }

    public Boolean getErledigt() {
        return erledigt;
    }

    public void setErledigt(Boolean erledigt) {
        this.erledigt = (erledigt != null) ? erledigt : false;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalTime getZeit() {
        return zeit;
    }

    public void setZeit(LocalTime zeit) {
        this.zeit = zeit;
    }
}

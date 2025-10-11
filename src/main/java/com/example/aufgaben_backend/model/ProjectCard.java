package com.example.aufgaben_backend.model;

import jakarta.persistence.*;

@Entity
public class ProjectCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String text;

    private String color; // пастельная метка (необяз.)

    @Enumerated(EnumType.STRING)
    private CardStatus status; // IDEEN / ARBEIT / FERTIG

    private Integer position; // порядок внутри колонки

    public ProjectCard() {}

    public ProjectCard(String text, String color, CardStatus status, Integer position) {
        this.text = text;
        this.color = color;
        this.status = status;
        this.position = position;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public CardStatus getStatus() { return status; }
    public void setStatus(CardStatus status) { this.status = status; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
}

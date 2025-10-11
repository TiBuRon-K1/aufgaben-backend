# Aufgaben Backend (ToDo)

Ein einfaches Spring Boot Backend für eine Aufgabenliste (auf Deutsch).

## Voraussetzungen
- JDK 21 oder höher
- Maven

## Starten
1. Projekt entpacken
2. In IntelliJ öffnen (File → Open → ordner `aufgaben-backend`) oder mit Maven starten:
```bash
./mvnw spring-boot:run
```

Das Backend läuft unter `http://localhost:8080`
H2 Konsole: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:aufgaben_db`)

## API (Deutsch)
- `GET /api/aufgaben` — alle Aufgaben holen
- `POST /api/aufgaben` — neue Aufgabe erstellen (JSON)
- `PUT /api/aufgaben/{id}` — Aufgabe aktualisieren
- `DELETE /api/aufgaben/{id}` — Aufgabe löschen

### Beispiel: neue Aufgabe erstellen
```bash
curl -X POST http://localhost:8080/api/aufgaben -H "Content-Type: application/json" -d '{"titel":"Neues Ziel","beschreibung":"Beschreibung","erledigt":false}'
```

### Beispiel: Aufgaben abrufen
```bash
curl http://localhost:8080/api/aufgaben
```

## Hinweise
- Der Server verwendet eine eingebaute H2 In-Memory Datenbank. Beim Neustart gehen die Daten verloren (außer `data.sql` initialisiert beim Start).
- API-Pfade und Felder sind auf Deutsch (titel, beschreibung, erledigt).

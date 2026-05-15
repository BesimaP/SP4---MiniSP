# Simpl

Simpl is a JavaFX desktop application designed to support patients through any type of treatment journey. Whether the journey involves fertility treatment, cancer, psychiatric care, rehabilitation, or something else entirely, Simpl helps patients stay organised — logging hormone values, tracking medication, managing appointments, writing diary entries, and viewing their complete treatment timeline in one place.

## About the project

Simpl was built as a 1st semester Computer Science (Datamatiker) project (SP4). The goal was to design and develop a real desktop application using the MVC architecture, a SQLite database, and a clean, accessible user interface.

The application is designed to support multiple treatment journey types — fertility, cancer, psychiatry, rehabilitation, and other. For this version, we have gone in-depth with the **IVF fertility journey** as a concrete example of how a complete treatment flow can look. The IVF journey demonstrates the full feature set: multiple treatment rounds, hormone tracking, appointment management, diary entries, and a chronological timeline.

This approach shows two things: the architecture is flexible enough to support **any patient group**, and we have built a working, polished experience for one specific group. The model can be extended to reach many more patient groups beyond the ones currently included.

## Features

- **User accounts** — create a profile, log in securely, and resume your journey
- **Journey selection** — choose between fertility, cancer, psychiatry, rehabilitation, or other
- **Dashboard** — overview with key statistics (latest hormone, next appointment, total rounds, diary entries)
- **Hormone log** — track hormone values with units over time
- **Medication log** — record medications, dosage, and whether they were taken
- **Appointments** — schedule and view upcoming appointments (scanning, consultation, egg retrieval, transfer, blood test, etc.)
- **Diary** — write daily diary entries with mood tracking
- **Timeline** — see all events across the journey in chronological order
- **Round management** — start a new treatment round and end it with a result (positive, negative, pending)
- **Round history** — review past treatment rounds

## Tech stack

- **Java 21**
- **JavaFX 21** — user interface
- **SQLite** (via `sqlite-jdbc`) — local database
- **Maven** — build and dependency management

## Architecture

The project follows the **MVC** (Model-View-Controller) pattern:

```
src/
├── Main.java              # Application entry point
├── model/                 # Data classes, database access, Session
├── view/                  # JavaFX views (UI)
├── controller/            # Business logic between view and model
├── enums/                 # Enums (AppointmentType, EventType, Result, Status)
├── design/                # CSS stylesheet and image assets
└── test/                  # Tests
```

A `Session` class holds the currently logged-in patient and active journey ID, so any view can access this data without passing it around.

## Database

The SQLite database (`simpl.db`) is created automatically on first run from `data/schema.sql`. It contains the following tables:

- `patient` — user accounts
- `journey` — a patient's overall treatment journey
- `fertility_journey` — IVF-specific data (round number, eggs retrieved, eggs fertilised, result)
- `event` — events on the timeline
- `appointment` — scheduled appointments
- `medication_log` — medication entries
- `hormone_log` — hormone value entries
- `diary_entry` — diary entries

## Getting started

### Prerequisites

- Java 21 (JDK)
- Maven 3.x

### Run the application

Clone the repository and run with Maven:

```bash
git clone https://github.com/BesimaP/SP4---MiniSP.git
cd SP4---MiniSP
mvn clean javafx:run
```

The application opens with the login screen. Create a new profile to get started.

## Project structure

```
SP4---MiniSP/
├── data/
│   └── schema.sql          # Database schema
├── doc/                    # UML diagrams and documentation
├── src/
│   ├── Main.java
│   ├── controller/
│   ├── model/
│   ├── view/
│   ├── enums/
│   ├── design/
│   │   ├── styles.css
│   │   ├── heart.png
│   │   ├── diary.png
│   │   ├── appointments.png
│   │   ├── medication.png
│   │   ├── timeline.png
│   │   ├── roundHistory.png
│   │   ├── start.png
│   │   └── stop.png
│   └── test/
├── simpl.db                # SQLite database (created on first run)
├── pom.xml
└── README.md
```

## Design

Simpl uses a calm, supportive green colour palette to fit its purpose as a healthcare application. The design system is defined in `src/design/styles.css` and used consistently across all views. Key components include:

- Gradient background (`#e8f5e9` → `#e3f2fd`)
- White cards with rounded corners and subtle shadows
- Modern input fields with green focus state
- Primary buttons in green (`#4caf50`)
- Colour-coded section cards (green for logging, blue for planning, pink for rounds)

## Authors

Built by 3 students as part of the SP4 project, 1st semester Datamatiker.

## License

This project was developed for educational purposes as part of a school assignment.
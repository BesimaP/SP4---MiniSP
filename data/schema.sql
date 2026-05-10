CREATE TABLE IF NOT EXISTS patient (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    dateOfBirth TEXT NOT NULL,
    diagnosis TEXT,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS journey (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    patient_id INTEGER NOT NULL,
    type TEXT NOT NULL,
    startDate TEXT NOT NULL,
    status TEXT NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE TABLE IF NOT EXISTS fertility_journey (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    journey_id INTEGER NOT NULL,
    roundNumber INTEGER NOT NULL,
    eggsRetrieved INTEGER,
    eggsFertilised INTEGER,
    result TEXT,
    FOREIGN KEY (journey_id) REFERENCES journey(id)
);

CREATE TABLE IF NOT EXISTS event (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    journey_id INTEGER NOT NULL,
    date TEXT NOT NULL,
    type TEXT NOT NULL,
    description TEXT,
    FOREIGN KEY (journey_id) REFERENCES journey(id)
);

CREATE TABLE IF NOT EXISTS appointment (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    journey_id INTEGER NOT NULL,
    date TEXT NOT NULL,
    type TEXT NOT NULL,
    location TEXT,
    completed INTEGER DEFAULT 0,
    FOREIGN KEY (journey_id) REFERENCES journey(id)
);

CREATE TABLE IF NOT EXISTS medication_log (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    journey_id INTEGER NOT NULL,
    date TEXT NOT NULL,
    medication TEXT NOT NULL,
    dose TEXT NOT NULL,
    taken INTEGER DEFAULT 0,
    FOREIGN KEY (journey_id) REFERENCES journey(id)
);

CREATE TABLE IF NOT EXISTS hormone_log (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    journey_id INTEGER NOT NULL,
    date TEXT NOT NULL,
    hormone TEXT NOT NULL,
    value REAL NOT NULL,
    unit TEXT NOT NULL,
    FOREIGN KEY (journey_id) REFERENCES journey(id)
);

CREATE TABLE IF NOT EXISTS diary_entry (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    journey_id INTEGER NOT NULL,
    date TEXT NOT NULL,
    title TEXT NOT NULL,
    content TEXT,
    FOREIGN KEY (journey_id) REFERENCES journey(id)
);
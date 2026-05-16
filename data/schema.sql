-- ============================================================
-- DatabaseInitializer — opretter alle 8 tabeller i Simpl
-- Kører hver gang programmet starter
-- IF NOT EXISTS sikrer at tabellerne ikke oprettes igen
-- hvis databasen allerede eksisterer
-- ============================================================

-- Patient tabellen — gemmer alle patienters oplysninger
-- username er UNIQUE så to patienter ikke kan have samme brugernavn
CREATE TABLE IF NOT EXISTS patient (
    id          INTEGER PRIMARY KEY AUTOINCREMENT, -- unikt id der tæller op automatisk
    name        TEXT NOT NULL,                     -- patientens fulde navn
    dateOfBirth TEXT NOT NULL,                     -- fødselsdato gemt som tekst
    diagnosis   TEXT,                              -- diagnose fx PCOS — må gerne være tom
    username    TEXT NOT NULL UNIQUE,              -- brugernavn — må ikke bruges to gange
    password    TEXT NOT NULL                      -- adgangskode
);

-- Journey tabellen — gemmer patientens forløb
-- patient_id er en FOREIGN KEY der peger på patient tabellen
-- Et forløb kan fx være Fertility, Cancer eller Rehabilitation
CREATE TABLE IF NOT EXISTS journey (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    patient_id INTEGER NOT NULL,                   -- hvilken patient forløbet tilhører
    type       TEXT NOT NULL,                      -- forløbstype fx Fertility
    startDate  TEXT NOT NULL,                      -- startdato gemt som tekst
    status     TEXT NOT NULL,                      -- ACTIVE, COMPLETED eller PAUSED
    FOREIGN KEY (patient_id) REFERENCES patient(id) -- kobler til patient tabellen
);

-- Fertility_journey tabellen — gemmer IVF-rundedata
-- journey_id er en FOREIGN KEY der peger på journey tabellen
-- En runde starter med result = PENDING og opdateres ved afslutning
CREATE TABLE IF NOT EXISTS fertility_journey (
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    journey_id     INTEGER NOT NULL,               -- hvilket forløb runden tilhører
    roundNumber    INTEGER NOT NULL,               -- rundenummer fx 1, 2 eller 3
    eggsRetrieved  INTEGER,                        -- antal udtagne æg — udfyldes senere
    eggsFertilised INTEGER,                        -- antal befrugtede æg — udfyldes senere
    result         TEXT,                           -- POSITIVE, NEGATIVE eller PENDING
    FOREIGN KEY (journey_id) REFERENCES journey(id)
);

-- Event tabellen — gemmer alle hændelser til tidslinjen
-- Fyldes automatisk op når patienten logger hormoner, medicin, aftaler osv.
CREATE TABLE IF NOT EXISTS event (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    journey_id  INTEGER NOT NULL,                  -- hvilket forløb hændelsen tilhører
    date        TEXT NOT NULL,                     -- dato for hændelsen
    type        TEXT NOT NULL,                     -- fx STIMULATION, CONSULTATION, OTHER
    description TEXT,                              -- beskrivelse af hændelsen
    FOREIGN KEY (journey_id) REFERENCES journey(id)
);

-- Appointment tabellen — gemmer patientens aftaler
-- completed = 0 betyder ikke gennemført, completed = 1 betyder gennemført
CREATE TABLE IF NOT EXISTS appointment (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    journey_id INTEGER NOT NULL,                   -- hvilket forløb aftalen tilhører
    date       TEXT NOT NULL,                      -- dato for aftalen
    type       TEXT NOT NULL,                      -- fx Scanning, Transfer, Blood Test
    location   TEXT,                               -- sted fx Vitanova
    completed  INTEGER DEFAULT 0,                  -- 0 = ikke gennemført, 1 = gennemført
    FOREIGN KEY (journey_id) REFERENCES journey(id)
);

-- Medication_log tabellen — gemmer daglige medicinindtastninger
-- taken = 0 betyder ikke taget, taken = 1 betyder taget
CREATE TABLE IF NOT EXISTS medication_log (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    journey_id INTEGER NOT NULL,                   -- hvilket forløb medicinen tilhører
    date       TEXT NOT NULL,                      -- dato for indtagelse
    medication TEXT NOT NULL,                      -- medicinens navn fx Gonal-F
    dose       TEXT NOT NULL,                      -- dosis fx 150 IU
    taken      INTEGER DEFAULT 0,                  -- 0 = ikke taget, 1 = taget
    FOREIGN KEY (journey_id) REFERENCES journey(id)
);

-- Hormone_log tabellen — gemmer hormonværdier
-- value er REAL fordi hormonværdier kan være decimaltal fx 450.5
CREATE TABLE IF NOT EXISTS hormone_log (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    journey_id INTEGER NOT NULL,                   -- hvilket forløb værdien tilhører
    date       TEXT NOT NULL,                      -- dato for målingen
    hormone    TEXT NOT NULL,                      -- hormontype fx Oestradiol
    value      REAL NOT NULL,                      -- målt værdi fx 450.5
    unit       TEXT NOT NULL,                      -- enhed fx pmol/L
    FOREIGN KEY (journey_id) REFERENCES journey(id)
);

-- Diary_entry tabellen — gemmer dagbogsnoter
-- content må gerne være tom hvis patienten kun skriver en titel
CREATE TABLE IF NOT EXISTS diary_entry (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    journey_id INTEGER NOT NULL,                   -- hvilket forløb noten tilhører
    date       TEXT NOT NULL,                      -- dato for noten
    title      TEXT NOT NULL,                      -- titel på noten
    content    TEXT,                               -- indhold — må gerne være tomt
    FOREIGN KEY (journey_id) REFERENCES journey(id)
);
## UC1 – StartSystem
- Systemet starter og indlæser patientdata fra SQLite
- Viser dashboard
- Ingen patient → UC2

- Regnvejrsdag: Databasen kan ikke læses → fejlbesked vises, brugeren kan prøve igen

## UC2 – Opretpatient
- Systemet viser en skærm med felter til navn, fødselsdato og diagnose
- Bruger udfylder felterne og klikker Gem
- Patient gemmes i DB
- Viser dashboard

- Regnvejrsdag: Et eller flere påkrævede felter er tomme → fejlbesked, gemmer ikke

## UC3 – VælgForløbstype
- Systemet viser en skærm med valgmulighederne: Fertilitet, Kræft, Genoptræning, Psykiatri, Andet 
- Bruger vælger en type og klikker Fortsæt 
- Journey oprettes med status AKTIV, gemmes i DB 
- Sendes videre til relevant dashboard 

- Regnvejrsdag: Ingen forløbstype er valgt → fejlbesked, opretter ikke

## UC4 – StartNyRunde
- Systemet viser en skærm til ny IVF-runde
- Bruger udfylder felterne og klikker Start Runde
- Systemet tjekker om der allerede er en aktiv runde
- Ny runde oprettes med status AKTIV, gemmes i DB
- Dashboard opdateres med den nye runde

- Regnvejrsdag: Der er allerede en aktiv runde → fejlbesked, opretter ikke ny runde

## UC5 – LogMedicin
- Systemet viser en skærm med tidligere medicinindtastninger for den aktive runde
- Bruger klikker Tilføj Medicin
- Bruger udfylder navn, dosis og dato
- Bruger klikker Gem → gemmes i DB, liste opdateres

- Regnvejrsdag: Et eller flere påkrævede felter er tomme → fejlbesked, gemmer ikke

## UC6 – LogHormonværdi
- Systemet viser en skærm med hormonværdier for den aktive runde
- Bruger klikker Tilføj Værdi
- Bruger udfylder hormontype, værdi, enhed og dato
- Bruger klikker Gem → gemmes i DB, listen opdateres

- Regnvejrsdag: Værdien er ikke et tal → fejlbesked, gemmer ikke

## UC7 – TilføjAftale
- Systemet viser en skærm med kommende aftaler
- Bruger klikker Tilføj Aftale
- Bruger udfylder dato, type og sted
- Bruger klikker Gem → gemmes i DB, liste opdateres

- Regnvejrsdag: Datoen er i fortiden → advarsel, bruger skal bekræfte inden der gemmes

## UC8 – SeTidslinje
- Systemet viser alle hændelser for aktiv runde kronologisk
- Bruger kan klikke på en hændelse for detaljer

- Regnvejrsdag: Ingen hændelser findes for aktiv runde → besked om at tidslinjen er tom

## UC9 – SkrivDagbogsnote
- Systemet viser tidligere noter for den aktive runde 
- Bruger klikker Tilføj Note 
- Bruger udfylder titel og indhold 
- Bruger klikker Gem → gemmes i DB, liste opdateres

- Regnvejrsdag: Titel er tom → fejlbesked, gemmer ikke

## UC10 - SeRundeHistorik
- Systemet viser alle tidligere IVF-runder
- Bruger vælger en runde
- Viser detaljer: udtagne æg, befrugtede æg, resultat

- Regnvejrsdag: Ingen tidligere runder findes → besked om ingen historik

## UC11 - AfslutRunde
- Systemet viser mulighed for at afslutte aktiv runde
- Bruger vælger resultat: Positiv, Negativ eller Afventer
- Bruger klikker Afslut Runde → status sættes til AFSLUTTET, gemmes i DB

- Regnvejrsdag: Intet resultat er valgt → fejlbesked, afslutter ikke runden
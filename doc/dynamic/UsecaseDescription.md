## UC1: StartSystem
Systemet starter og viser en login-skærm. 
Brugeren indtaster brugernavn og adgangskode og klikker Log ind. 
Systemet validerer oplysningerne og indlæser patientdata fra SQLite-databasen.
Hvis patienten har et aktivt forløb vises dashboardet.
Hvis ingen konto findes sendes brugeren til UC2: ManageProfile.
Hvis patienten ikke har et aktivt forløb sendes brugeren til UC3: JourneyType.

Regnvejrsdag:
- Databasen kan ikke læses: Fejlbesked vises, brugeren kan prøve igen.
- Forkert brugernavn eller adgangskode: Systemet viser en fejlbesked og logger ikke ind.
- Felter er tomme: Systemet viser en fejlbesked og logger ikke ind.


## UC2: ManageProfile
Systemet viser en skærm med felter til navn, fødselsdato, diagnose, brugernavn og adgangskode.
Brugeren udfylder felterne og klikker Gem.
Systemet opretter en ny patient og gemmer den i databasen.
Brugeren sendes videre til UC3: JourneyType.
Brugeren kan efterfølgende redigere sine oplysninger eller slette sin konto og data.

Regnvejrsdag:
- Et eller flere påkrævede felter er tomme: Systemet viser en fejlbesked og gemmer ikke.
- Brugernavn er allerede i brug: Systemet viser en fejlbesked og gemmer ikke.


## UC3: JourneyType
Systemet viser en skærm med valgmulighederne: Fertility, Cancer, Rehabilitation, Psychiatry eller Other.
Brugeren vælger en type og klikker Continue.
Systemet opretter et nyt Journey med den valgte type og status ACTIVE og gemmer det i databasen.
Journey_id gemmes i Session.
Systemet sender brugeren videre til dashboardet.

Regnvejrsdag:
- Ingen forløbstype er valgt: Systemet viser en fejlbesked og opretter ikke et forløb.


## UC4: NewRound
Systemet viser en skærm til ny IVF-runde med dagens dato som startdato.
Brugeren udfylder rundenummeret og klikker Start Round.
Ny runde oprettes med result PENDING og gemmes i databasen.
Systemet gemmer en hændelse i event tabellen.
Dashboardet opdateres med den nye runde.

Regnvejrsdag:
- Rundenummer er tomt: Systemet viser en fejlbesked og opretter ikke en ny runde.


## UC5: Appointment
Systemet viser en skærm med kommende aftaler i en kalendervisning. 
Brugeren klikker Tilføj Aftale og udfylder dato, type (scanning, konsultation mv.) og sted. 
Brugeren klikker Gem. Systemet gemmer aftalen i databasen og opdaterer listen.
Brugeren kan markere en aftale som gennemført.
Dashboardet viser kommende vigtige datoer.
Systemet gemmer en hændelse i event tabellen.

Regnvejrsdag:
- Et eller flere påkrævede felter er tomme: Systemet viser en fejlbesked og gemmer ikke.
- Datoen er i fortiden: Systemet viser en advarsel og beder brugeren bekræfte inden der gemmes.

## UC6: HormoneLog
Systemet viser en skærm med hormonværdier for den aktive runde. 
Brugeren klikker Tilføj Værdi og udfylder hormontype, værdi, enhed og dato. 
Brugeren klikker Gem. Systemet gemmer hormonværdien i databasen og opdaterer listen.
Systemet gemmer en hændelse i event tabellen.

Regnvejrsdag:
- Værdien er ikke et tal: Systemet viser en fejlbesked og gemmer ikke.


## UC7: MedicationLog
Systemet viser en skærm med tidligere medicinindtastninger for den aktive runde.
Brugeren klikker Tilføj Medicin og udfylder navn, dosis og tidspunkt.
Brugeren klikker Gem. Systemet gemmer medicinindtastningen i databasen og opdaterer listen.
Systemet gemmer en hændelse i event tabellen.

Regnvejrsdag:
- Et eller flere påkrævede felter er tomme: Systemet viser en fejlbesked og gemmer ikke.


## UC8: Timeline
Systemet viser en skærm med alle hændelser for den aktive runde i kronologisk rækkefølge. 
Brugeren kan klikke på en hændelse for at se detaljer.

Regnvejrsdag:
- Ingen hændelser findes for den aktive runde: Systemet viser en besked om at tidslinjen er tom.


## UC9: Diary
Systemet viser en skærm til dagbogsnote med dato, titel og indhold.
Brugeren udfylder felterne og klikker Save.
Systemet gemmer noten i databasen.
Systemet gemmer en hændelse i event tabellen.

Regnvejrsdag:
- Titel er tom: Systemet viser en fejlbesked og gemmer ikke.
- Indhold er tomt: Systemet viser en fejlbesked og gemmer ikke.
- Dato er ikke valgt: Systemet viser en fejlbesked og gemmer ikke.


## UC10: RoundHistory
Systemet viser en skærm med alle tidligere IVF-runder. 
Brugeren vælger en runde og systemet viser detaljer inklusiv antal udtagne æg, antal befrugtede æg og resultat.

Regnvejrsdag:
- Ingen tidligere runder findes: Systemet viser en besked om at der ingen historik er.


## UC11: EndRound
Systemet viser en mulighed for at markere den aktive runde som afsluttet. 
Brugeren vælger et resultat (POSITIVE, NEGATIVE eller PENDING) og klikker End Runde. 
Systemet opdaterer rundens status til COMPLETED og gemmer det i databasen.
Systemet gemmer en hændelse i event tabellen.

Regnvejrsdag:
- Intet resultat er valgt: Systemet viser en fejlbesked og afslutter ikke runden.


## Fremtidige features

Følgende features er identificeret men ikke implementeret i denne version:
- Humør-felt på dagbogsnoter (UC9) — for at give patienten et nemt overblik over deres følelsesmæssige forløb
- Redigér og slet aftaler (UC5) — for at give patienten fuld kontrol over deres kalender
- Markér medicin som taget (UC7) — så medicinloggen kan bruges som daglig tjekliste
- Indtastning af eggsRetrieved og eggsFertilised ved End Round (UC11) — så rundehistorikken viser fuldstændige data
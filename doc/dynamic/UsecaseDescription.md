## UC1: StartSystem
Systemet starter og viser en login-skærm. 
Brugeren indtaster brugernavn og adgangskode og klikker Log ind. 
Systemet validerer oplysningerne og indlæser patientdata fra SQLite-databasen. 
Systemet viser dashboardet. 
Hvis ingen konto findes sendes brugeren til UC2: OpretPatient.

Regnvejrsdag:
- Databasen kan ikke læses: Fejlbesked vises, brugeren kan prøve igen.
- Forkert brugernavn eller adgangskode: Systemet viser en fejlbesked og logger ikke ind.


## UC2: AdministrerProfil
Systemet viser en skærm med felter til navn, fødselsdato, diagnose, brugernavn og adgangskode. 
Brugeren udfylder felterne og klikker Gem. 
Systemet opretter en ny patient og gemmer den i databasen. 
Brugeren kan efterfølgende redigere sine oplysninger eller slette sin konto og data.

Regnvejrsdag:
- Et eller flere påkrævede felter er tomme: Systemet viser en fejlbesked og gemmer ikke.
- Brugernavn er allerede i brug: Systemet viser en fejlbesked og gemmer ikke.


## UC3: VælgForløbstype
Systemet viser en skærm med valgmulighederne: Fertilitet, Kræft, Genoptræning, Psykiatri eller Andet. 
Brugeren vælger en type og klikker Fortsæt. 
Systemet opretter et nyt Journey med den valgte type og status AKTIV og gemmer det i databasen. 
Systemet sender brugeren videre til det relevante dashboard.

Regnvejrsdag:
- Ingen forløbstype er valgt: Systemet viser en fejlbesked og opretter ikke et forløb.


## UC4: StartNyRunde
Systemet viser en skærm til ny IVF-runde. 
Brugeren udfylder felterne og klikker Start Runde. 
Systemet tjekker om der allerede er en aktiv runde. 
Ny runde oprettes med status AKTIV og gemmes i databasen. 
Dashboardet opdateres med den nye runde.

Regnvejrsdag:
- Der er allerede en aktiv runde: Systemet viser en fejlbesked og opretter ikke en ny runde.


## UC5: TilføjAftale
Systemet viser en skærm med kommende aftaler i en kalendervisning. 
Brugeren klikker Tilføj Aftale og udfylder dato, type (scanning, konsultation mv.) og sted. 
Brugeren klikker Gem. Systemet gemmer aftalen i databasen og opdaterer listen. 
Brugeren kan markere en aftale som gennemført samt redigere eller slette en aftale. 
Dashboardet viser kommende vigtige datoer.

Regnvejrsdag:
- Et eller flere påkrævede felter er tomme: Systemet viser en fejlbesked og gemmer ikke.
- Datoen er i fortiden: Systemet viser en advarsel og beder brugeren bekræfte inden der gemmes.

## UC6: LogHormonværdi
Systemet viser en skærm med hormonværdier for den aktive runde. 
Brugeren klikker Tilføj Værdi og udfylder hormontype, værdi, enhed og dato. 
Brugeren klikker Gem. Systemet gemmer hormonværdien i databasen og opdaterer listen.

Regnvejrsdag:
- Værdien er ikke et tal: Systemet viser en fejlbesked og gemmer ikke.


## UC7: LogMedicin
Systemet viser en skærm med tidligere medicinindtastninger for den aktive runde. 
Brugeren klikker Tilføj Medicin og udfylder navn, dosis og tidspunkt. 
Brugeren kan registrere om medicin er taget samt redigere eller afslutte medicinering. 
Brugeren klikker Gem. Systemet gemmer medicinindtastningen i databasen og opdaterer listen.

Regnvejrsdag:
- Et eller flere påkrævede felter er tomme: Systemet viser en fejlbesked og gemmer ikke.

## UC8: SeTidslinje
Systemet viser en skærm med alle hændelser for den aktive runde i kronologisk rækkefølge. 
Brugeren kan klikke på en hændelse for at se detaljer.

Regnvejrsdag:
- Ingen hændelser findes for den aktive runde: Systemet viser en besked om at tidslinjen er tom.


## UC9: SkrivDagbogsnote
Systemet viser en skærm med tidligere noter for den aktive runde. 
Brugeren klikker Tilføj Note og udfylder en titel og indhold. 
Brugeren klikker Gem. Systemet gemmer noten i databasen og opdaterer listen.

Regnvejrsdag:
- Titel er tom: Systemet viser en fejlbesked og gemmer ikke.


## UC10: SeRundeHistorik
Systemet viser en skærm med alle tidligere IVF-runder. 
Brugeren vælger en runde og systemet viser detaljer inklusiv antal udtagne æg, antal befrugtede æg og resultat.

Regnvejrsdag:
- Ingen tidligere runder findes: Systemet viser en besked om at der ingen historik er.


## UC11: AfslutRunde
Systemet viser en mulighed for at markere den aktive runde som afsluttet. 
Brugeren vælger et resultat (Positiv, Negativ eller Afventer) og klikker Afslut Runde. 
Systemet opdaterer rundens status til AFSLUTTET og gemmer det i databasen.

Regnvejrsdag:
- Intet resultat er valgt: Systemet viser en fejlbesked og afslutter ikke runden.


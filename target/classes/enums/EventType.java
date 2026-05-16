package enums;

    // EventType definerer de mulige typer af hændelser i tidslinjen
    // Bruges når vi gemmer en event i databasen så typen altid er gyldig
    public enum EventType {
        CONSULTATION,    // konsultation
        EXAMINATION,     // undersøgelse
        STIMULATION,     // hormonstimulation — bruges ved hormonlog
        EGG_RETRIEVAL,   // ægudtagning
        FERTILISATION,   // befrugtning af æg
        TRANSFER,        // embryotransfer
        PREGNANCY_TEST,  // graviditetstest — bruges ved afslut runde
        OTHER            // anden hændelse — bruges ved medicin og dagbog
    }
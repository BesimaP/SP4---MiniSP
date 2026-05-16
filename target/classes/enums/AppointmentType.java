package enums;

    // AppointmentType definerer de mulige typer af aftaler i Simpl
    // Planlagt aftale
    // En enum sikrer at kun gyldige værdier kan bruges — fx kan man ikke skrive "Scaning" med stavefejl
    public enum AppointmentType {
        SCANNING,       // scanning
        CONSULTATION,   // konsultation
        EGG_RETRIEVAL,  // ægudtagning
        TRANSFER,       // embryotransfer
        BLOOD_TEST,     // blodprøve
        OTHER           // anden type aftale
    }
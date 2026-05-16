package enums;

    // Result definerer de mulige udfald af en IVF-runde
    // Sættes når patienten afslutter en runde via EndRoundView
    public enum Result {
        POSITIVE,  // graviditetstest positiv
        NEGATIVE,  // graviditetstest negativ
        PENDING    // afventer — bruges som standard når runden starter
    }

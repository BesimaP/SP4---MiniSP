package enums;

    // Status definerer de mulige tilstande for et forløb
    // Bruges i Journey tabellen til at holde styr på om forløbet er aktivt eller afsluttet
    public enum Status {
        ACTIVE,    // forløbet er aktivt — patienten er i gang
        COMPLETED, // forløbet er afsluttet — sættes når patienten kører EndRound
        PAUSED     // forløbet er sat på pause — fremtidig feature
    }
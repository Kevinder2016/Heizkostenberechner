package ch.bbbaden.idpa.vorprojekt.heizkostenberechner;

/**
 * Der Benutzer erstellt indirekt eine Anfrage.
 * Hier werden seine angegebenen Werte gespeichert und weitere Werte berechnet.
 */
public class Anfrage {

    private float Wohnungsfläche;
    private float Raumhöhe;
    private Heizungsart heizungsart;
    private float Temperatur;
    private float PreisProEinheit;
    private float EinheitProKwh;

    private static final float DURCHSCHNITTLICHER_U_WERT = 0.2f;
    private static final float DURCHSCHNITTLICHE_AUSSENTEMPERATUR = 5.6f;
    private static final int DURCHSCHNITTLICHE_STUNDEN_IM_MONAT = 730;

    public Anfrage(float wohnungsfläche, float raumhöhe, String heizungsart, float temperatur) {
        Wohnungsfläche = wohnungsfläche;
        Raumhöhe = raumhöhe;
        Temperatur = temperatur;

        switch (heizungsart) {
            case "Ölheizung":
                this.heizungsart = Heizungsart.ÖL;
                EinheitProKwh = 9.8f;
                PreisProEinheit = 1.5853f;
                break;
            case "Gasheizung":
                this.heizungsart = Heizungsart.GAS;
                EinheitProKwh = 1;
                PreisProEinheit = 0.1475f;
                break;
            case "Pellets":
                this.heizungsart = Heizungsart.PELLETS;
                EinheitProKwh = 4.8f;
                PreisProEinheit = 0.55444583f;
                break;
            default:
                this.heizungsart = Heizungsart.WÄRMEPUMPE;
                EinheitProKwh = 1;
                PreisProEinheit = 0.212f;
                break;
        }
    }

    public float getWohnungsfläche() {
        return Wohnungsfläche;
    }

    public float getRaumhöhe() {
        return Raumhöhe;
    }

    public Heizungsart getHeizungsart() {
        return heizungsart;
    }

    public String getHeizungsartAsString(){
        String heizungAsString;
        switch (heizungsart) {
            case ÖL -> heizungAsString = "Ölheizung";
            case GAS -> heizungAsString = "Gasheizung";
            case PELLETS -> heizungAsString = "Holzpellets";
            default -> heizungAsString = "Wärmepumpe";
        }
        return heizungAsString;
    }

    public float getTemperatur() {
        return Temperatur;
    }

    public void setTemperatur(float temperatur) {
        Temperatur = temperatur;
    }

    public float getPreisProKwh(){
        return Math.round(PreisProEinheit / EinheitProKwh * 1000.0f) / 1000.0f;
    } // Drei Stellen nach dem Komma gerundet

    public float berechnePreisProMonat() {
        float ungerundeterPreisProMonat = (float) (DURCHSCHNITTLICHER_U_WERT * (Temperatur - DURCHSCHNITTLICHE_AUSSENTEMPERATUR) * ((2*Wohnungsfläche) + (4*(Math.sqrt(Wohnungsfläche)*Raumhöhe))) * DURCHSCHNITTLICHE_STUNDEN_IM_MONAT / 1000 * (PreisProEinheit/EinheitProKwh));
        return Math.round(ungerundeterPreisProMonat * 100.0f) /100.0f;
    }
}

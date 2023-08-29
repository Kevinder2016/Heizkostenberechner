package ch.bbbaden.idpa.vorprojekt.heizkostenberechner;

import javafx.beans.property.SimpleStringProperty;

/**
 * Klasse, um die richtigen Werte in der Tabelle auf der Startseite anzuzeigen
 */
public class TablePair {
    private SimpleStringProperty beschriftung;
    private SimpleStringProperty wert;

    public TablePair(String beschriftung, String wert) {
        this.beschriftung = new SimpleStringProperty(beschriftung);
        this.wert = new SimpleStringProperty(wert);
    }

    public String getBeschriftung() {
        return beschriftung.get();
    }

    public void setBeschriftung(String beschriftung) {
        this.beschriftung = new SimpleStringProperty(beschriftung);
    }

    public String getWert() {
        return wert.get();
    }

    public void setWert(String wert) {
        this.wert = new SimpleStringProperty(wert);
    }
}

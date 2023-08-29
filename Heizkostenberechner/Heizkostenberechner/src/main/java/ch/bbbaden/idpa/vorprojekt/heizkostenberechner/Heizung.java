package ch.bbbaden.idpa.vorprojekt.heizkostenberechner;

/**
 * Klasse um die richtigen Werte in der Schlusstabelle darzustellen.
 */

import javafx.beans.property.SimpleStringProperty;

public class Heizung {
    private SimpleStringProperty name;
    private SimpleStringProperty kostenProKwh;
    private SimpleStringProperty kostenProMonat;
    private SimpleStringProperty energieverbrauch;
    private SimpleStringProperty prozentualeAbweichung;

    public Heizung(String name, String kostenProKwh, String kostenProMonat, String energieverbrauch, String prozentualeAbweichung) {
        this.name = new SimpleStringProperty(name);
        this.kostenProKwh = new SimpleStringProperty(kostenProKwh);
        this.kostenProMonat = new SimpleStringProperty(kostenProMonat);
        this.energieverbrauch = new SimpleStringProperty(energieverbrauch);
        this.prozentualeAbweichung = new SimpleStringProperty(prozentualeAbweichung);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getKostenProKwh() {
        return kostenProKwh.get();
    }

    public void setKostenProKwh(String kostenProKwh) {
        this.kostenProKwh = new SimpleStringProperty(kostenProKwh);
    }

    public String getKostenProMonat() {
        return kostenProMonat.get();
    }

    public void setKostenProMonat(String kostenProMonat) {
        this.kostenProKwh = new SimpleStringProperty(kostenProMonat);
    }

    public String getEnergieverbrauch() {
        return energieverbrauch.get();
    }

    public void setEnergieverbrauch(String energieverbrauch) {
        this.energieverbrauch = new SimpleStringProperty(energieverbrauch);
    }

    public String getProzentualeAbweichung() {
        return prozentualeAbweichung.get();
    }

    public void setProzentualeAbweichung(String prozentualeAbweichung) {
        this.prozentualeAbweichung = new SimpleStringProperty(prozentualeAbweichung);
    }
}

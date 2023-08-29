package ch.bbbaden.idpa.vorprojekt.heizkostenberechner;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class ControllerSchlusstabelle {

    //FXML Elemente
    @FXML
    private TableColumn<Heizung, String> heizungsart;
    @FXML
    private TableColumn<Heizung, String > energieverbrauch;
    @FXML
    private TableColumn<Heizung, String> costs;
    @FXML
    private TableColumn<Heizung, String> preispromonat;
    @FXML
    private TableColumn<Heizung, String > prozentualeAbweichung;
    @FXML
    private TableView<Heizung> tableViewSchlusstabelle;

    //Variablen
    private Anfrage aktuelleAnfrage;
    private ArrayList<Heizung> items = new ArrayList<>();
    private static final float DURCHSCHNITTLICHER_U_WERT = 0.2f;
    private static final float DURCHSCHNITTLICHE_AUSSENTEMPERATUR = 5.6f;
    private static final int DURCHSCHNITTLICHE_STUNDEN_IM_MONAT = 730;

    /**
     * Methode, die vor Beginn aufgerufen wird, um alle Einstellungen richtig zu setzen
     * @param aktuelleAnfrage Die Anfrage, die der Benutzer getätigt hat, wird für Vergleiche mitgeliefert
     */
    public void start(Anfrage aktuelleAnfrage) {
        this.aktuelleAnfrage = aktuelleAnfrage;

        //Tabellen-Einstellungen
        heizungsart.setCellValueFactory(new PropertyValueFactory<>("Name"));
        energieverbrauch.setCellValueFactory(new PropertyValueFactory<>("Energieverbrauch"));
        costs.setCellValueFactory(new PropertyValueFactory<>("KostenProKwh"));
        preispromonat.setCellValueFactory(new PropertyValueFactory<>("KostenProMonat"));
        prozentualeAbweichung.setCellValueFactory(new PropertyValueFactory<>("ProzentualeAbweichung"));

        //Item der Anfrage wird hinzugefügt
        items.add(new Heizung(aktuelleAnfrage.getHeizungsartAsString(), "CHF" + (aktuelleAnfrage.getPreisProKwh()),"CHF" + (aktuelleAnfrage.berechnePreisProMonat()), calculateEnergieverbrauch() + "kWh", "0%"));

        //Die anderen 3 Heizungsarten werden in die Liste gefügt
        if(!(aktuelleAnfrage.getHeizungsart() == Heizungsart.ÖL)){
            items.add(new Heizung("Ölheizung", "CHF " + Math.round(1.5853f/9.8f * 1000.0f) / 1000.0f, "CHF " + Math.round(calculatePreis(Heizungsart.ÖL) * 100.0f) / 100.0f,calculateEnergieverbrauch() + "kWh", calculateAbweichung(Heizungsart.ÖL) + "%"));
        }
        if(!(aktuelleAnfrage.getHeizungsart() == Heizungsart.GAS)){
            items.add(new Heizung("Gasheizung", "CHF " + 0.148f, "CHF " + Math.round(calculatePreis(Heizungsart.GAS) * 100.0f) / 100.0f,calculateEnergieverbrauch() + "kWh", calculateAbweichung(Heizungsart.GAS) + "%"));
        }
        if(!(aktuelleAnfrage.getHeizungsart() == Heizungsart.PELLETS)){
            items.add(new Heizung("Holzpellets", "CHF " + Math.round(0.55444583f/4.8f * 1000.0f) / 1000.0f, "CHF " + Math.round(calculatePreis(Heizungsart.PELLETS) * 100.0f) / 100.0f, calculateEnergieverbrauch() + "kWh", calculateAbweichung(Heizungsart.PELLETS) + "%"));
        }
        if(!(aktuelleAnfrage.getHeizungsart() == Heizungsart.WÄRMEPUMPE)){
            items.add(new Heizung("Wärmepumpe", "CHF " + 0.212f,"CHF " + Math.round(calculatePreis(Heizungsart.WÄRMEPUMPE) * 100.0f) / 100.0f, calculateEnergieverbrauch() + "kWh", calculateAbweichung(Heizungsart.WÄRMEPUMPE) + "%"));
        }

        //Liste wird an die Tabelle gehängt
        tableViewSchlusstabelle.setItems(FXCollections.observableArrayList(items));
    }

    /**
     * Berechnet den Energieverbauch in kWh.
     * Es wurde leider keine Formel gefunden, die den Energieverbrauch spezifisch auf die Heizungsart bezieht.
     *
     * @return Energieverbrauch in kWh als Float
     */
    private float calculateEnergieverbrauch(){

        float ungerundeterEnergieVerbrauch = (float) (DURCHSCHNITTLICHER_U_WERT * (aktuelleAnfrage.getTemperatur() - DURCHSCHNITTLICHE_AUSSENTEMPERATUR) * ((2* aktuelleAnfrage.getWohnungsfläche()) + (4*(Math.sqrt(aktuelleAnfrage.getWohnungsfläche())* aktuelleAnfrage.getRaumhöhe()))) * DURCHSCHNITTLICHE_STUNDEN_IM_MONAT / 1000);
        return Math.round(ungerundeterEnergieVerbrauch * 100.0f) / 100.0f;

    }

    /**
     * Berechnet die Abweichung des Preises pro Monat zur Heizung die der Benutzer hat
     *
     * @param heizungsart Heizungsart, die verglichen werden soll
     * @return Unterschied in Prozent (z.B. 9.0% oder -7.5%)
     */
    private float calculateAbweichung(Heizungsart heizungsart){
        float energieverbrauchGewählt = aktuelleAnfrage.berechnePreisProMonat();
        float energieverbrauchAndereHeizung = calculatePreis(heizungsart);
        float prozent = (energieverbrauchAndereHeizung/energieverbrauchGewählt) * 100.0f;
        return Math.round((prozent - 100.0f) * 10.0f) / 10.0f;
    }

    /**
     * Berechnet den Preis pro Monat für eine Heizungsart
     *
     * @param heizungsart Heizungsart, für welche der Preis berechnet werden soll
     * @return Preis pro Monat
     */
    private float calculatePreis(Heizungsart heizungsart){
        String heizungAsString = "";
        switch (heizungsart) {
            case ÖL:
                heizungAsString = "Ölheizung";
                break;
            case GAS:
                heizungAsString = "Gasheizung";
                break;
            case PELLETS:
                heizungAsString = "Pellets";
                break;
            default:
                heizungAsString = "Wärmepumpe";
                break;
        }
        Anfrage temporäreAnfrage = new Anfrage(aktuelleAnfrage.getWohnungsfläche(), aktuelleAnfrage.getRaumhöhe(), heizungAsString, aktuelleAnfrage.getTemperatur());
        return temporäreAnfrage.berechnePreisProMonat();
    }
}

package ch.bbbaden.idpa.vorprojekt.heizkostenberechner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerBerechner {

    //FXML Elemente
    @FXML
    private Button btnSchlusstabelle;
    @FXML
    private ChoiceBox<String> choiceBoxArt;
    @FXML
    private TableColumn<TablePair, String> colBeschriftung;
    @FXML
    private TableColumn<TablePair, String> colWert;
    @FXML
    private Slider sliderTemperatur;
    @FXML
    private TableView<TablePair> tableViewLive;
    @FXML
    private Spinner<Integer> spinnerQm;
    @FXML
    private Spinner<Integer> spinnerHöhe;
    @FXML
    private Label lblTemperaturJetzt;
    @FXML
    private Label lblError;

    //Variablen
    private MainStarter main;
    private Anfrage aktuelleAnfrage = null;

    /**
     * Methode, die vor Beginn aufgerufen wird, um alle Einstellungen richtig zu setzen
     * @param main Der MainStarter, welcher gebraucht wird, um die Schlusstabelle zu öffnen
     */
    public void start(MainStarter main) {

        this.main = main;

        //Spinner Einstellungen
        SpinnerValueFactory<Integer> spinnerValueFactoryQm = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0); // MIN, MAX, DEFAULT
        SpinnerValueFactory<Integer> spinnerValueFactoryHöhe = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0);
        spinnerQm.setValueFactory(spinnerValueFactoryQm);
        spinnerHöhe.setValueFactory(spinnerValueFactoryHöhe);

        //Choicebox Einstellungen
        String[] arrHeizungsarten = new String[]{"Ölheizung", "Gasheizung", "Pellets", "Wärmepumpe"};
        List heizungsarten = new ArrayList(List.of(arrHeizungsarten));
        choiceBoxArt.getItems().addAll(FXCollections.observableArrayList(heizungsarten));

        //Tabelleneinestellungen
        colBeschriftung.setCellValueFactory(new PropertyValueFactory<>("Beschriftung"));
        colWert.setCellValueFactory(new PropertyValueFactory<>("Wert"));

        //Slider Einstellungen
        sliderTemperatur.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                float temperaturJetzt = Math.round(newValue.floatValue() * 2) / 2.0f;
                lblTemperaturJetzt.setText("Ausgewählte Temperatur: " + temperaturJetzt + "°C");
                if (aktuelleAnfrage != null) {
                    aktuelleAnfrage.setTemperatur(temperaturJetzt);
                    setItemsForTable();
                }
            }

        });
        lblTemperaturJetzt.setText("Ausgewählt Temperatur: 20.0°C");

        btnSchlusstabelle.setVisible(false);
    }

    /**
     * Setzt die Items in die Tabelle, wurde erstellt um Duplikate zu vermeiden
     */
    private void setItemsForTable() {
        ObservableList<TablePair> items = FXCollections.observableArrayList(
                new TablePair[]{
                        new TablePair("Wohnungsgrösse", aktuelleAnfrage.getWohnungsfläche() * aktuelleAnfrage.getRaumhöhe() + " m^3"),
                        new TablePair("Temperatur", Math.round(aktuelleAnfrage.getTemperatur() * 2) / 2.0f + "°C"),
                        new TablePair("Heizungsart", aktuelleAnfrage.getHeizungsart().toString()),
                        new TablePair("Heizungskosten/kWh", aktuelleAnfrage.getPreisProKwh() + " pro kWh"),
                        new TablePair("Total/Monat (730h)", "CHF " + aktuelleAnfrage.berechnePreisProMonat())
                }
        );
        tableViewLive.setItems(items);
    }

    /**
     * Methode wird ausgeführt, wenn auf den Button "Berechnen" gedrückt wird.
     * Füllt die Mini-Tabelle
     */
    public void onBerechnen() {
        try {
            lblError.setText("");
            float wohnungsfläche = (float) spinnerQm.getValue();
            float raumhöhe = (float) spinnerHöhe.getValue();
            String heizungsart = choiceBoxArt.getSelectionModel().getSelectedItem().toString();
            float temperatur = (float) sliderTemperatur.getValue();
            aktuelleAnfrage = new Anfrage(wohnungsfläche, raumhöhe, heizungsart, temperatur);
            setItemsForTable();

            btnSchlusstabelle.setVisible(true);
        } catch (Exception e){
            lblError.setText("Fehler: Bitte überprüfen Sie ihre Eingaben.");
        }
    }

    /**
     * Zeigt auf Knopfdruck die Schlusstabelle, öffnet separates Fenster
     * @throws IOException
     */
    public void onSchlusstabelle() throws IOException {
        main.openSchlusstabelle(new Stage(), aktuelleAnfrage);
    }
}

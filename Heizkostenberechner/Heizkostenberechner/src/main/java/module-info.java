module ch.bbbaden.idpa.vorprojekt.heizkostenberechner {
    requires javafx.controls;
    requires javafx.fxml;


    opens ch.bbbaden.idpa.vorprojekt.heizkostenberechner to javafx.fxml;
    exports ch.bbbaden.idpa.vorprojekt.heizkostenberechner;
}
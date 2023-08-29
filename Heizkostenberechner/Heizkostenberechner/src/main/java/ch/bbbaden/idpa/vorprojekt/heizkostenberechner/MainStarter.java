package ch.bbbaden.idpa.vorprojekt.heizkostenberechner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainStarter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainStarter.class.getResource("Heizkostenrechner.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ControllerBerechner controllerBerechner = fxmlLoader.getController();
        controllerBerechner.start(this);
        stage.setTitle("Heizkostenberechner");
        stage.getIcons().add(new Image(MainStarter.class.getResource("Logo.png").openStream()));
        stage.setScene(scene);
        stage.show();
    }

    public void openSchlusstabelle(Stage stage, Anfrage aktuelleAnfrage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainStarter.class.getResource("Schlusstabelle.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ControllerSchlusstabelle controllerSchlusstabelle = fxmlLoader.getController();
        controllerSchlusstabelle.start(aktuelleAnfrage);
        stage.setTitle("Schlusstabelle");
        stage.getIcons().add(new Image(MainStarter.class.getResource("Logo.png").openStream()));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
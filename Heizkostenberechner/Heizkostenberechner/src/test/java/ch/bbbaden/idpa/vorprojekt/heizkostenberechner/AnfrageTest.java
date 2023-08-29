package ch.bbbaden.idpa.vorprojekt.heizkostenberechner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnfrageTest {

    @Test
    void getPreisProKwhGas() {
        Anfrage gas = new Anfrage(25, 3, "Gasheizung", 18.5f);
        float soll = Math.round(0.1475f * 1000.0f) /1000.0f;

        float ist = gas.getPreisProKwh();

        assertEquals(soll, ist);
    }

    @Test
    void getPreisProKwhOel() {
        Anfrage oel = new Anfrage(25, 3, "Ölheizung", 18.5f);
        float soll = Math.round((1.5853f / 9.8f) * 1000.0f) / 1000.0f;

        float ist = oel.getPreisProKwh();

        assertEquals(soll, ist);
    }

    @Test
    void getPreisProKwhPellets() {
        Anfrage pellets = new Anfrage(25, 3, "Pellets", 18.5f);
        float soll = Math.round((0.55444583f / 4.8f) * 1000.0f) / 1000.0f;

        float ist = pellets.getPreisProKwh();

        assertEquals(soll, ist);
    }

    @Test
    void getPreisProKwhWaermepumpe() {
        Anfrage wärmepumpe = new Anfrage(25, 3, "Wärmepumpe", 18.5f);
        float soll = Math.round(0.212f * 1000.0f) / 1000.0f;

        float ist = wärmepumpe.getPreisProKwh();

        assertEquals(soll, ist);
    }

    @Test
    void berechnePreisProMonatGas() {
        Anfrage gas = new Anfrage(25, 3, "Gasheizung", 18.5f);
        float soll = Math.round(0.2f * (18.5f - 5.6f) * ((2*25) + (4*5*3)) * 730.0f / 1000.0f * (Math.round(0.1475f * 1000.0f) /1000.0f) * 100.0f) / 100.0f;

        float ist = gas.berechnePreisProMonat();

        assertEquals(soll, ist);
    }

    @Test
    void berechnePreisProMonatOel() {
        Anfrage oel = new Anfrage(25, 3, "Ölheizung", 18.5f);
        float soll = Math.round(0.2f * (18.5f - 5.6f) * ((2*25) + (4*5*3)) * 730.0f / 1000.0f * (Math.round((1.5853f / 9.8f) * 1000.0f) / 1000.0f) * 100.0f) / 100.0f;


        float ist = oel.berechnePreisProMonat();

        assertEquals(soll, ist);

    }

    @Test
    void berechnePreisProMonatPellets() {
        Anfrage pellets = new Anfrage(25, 3, "Pellets", 18.5f);
        float soll = Math.round(0.2f * (18.5f - 5.6f) * ((2*25) + (4*5*3)) * 730.0f / 1000.0f * (Math.round((0.55444583f / 4.8f) * 1000.0f) / 1000.0f) * 100.0f) / 100.0f;


        float ist = pellets.berechnePreisProMonat();

        assertEquals(soll, ist);
    }

    @Test
    void berechnePreisProMonatWaermepumpe() {
        Anfrage wärmepumpe = new Anfrage(25, 3, "Wärmepumpe", 18.5f);
        float soll = Math.round(0.2f * (18.5f - 5.6f) * ((2*25) + (4*5*3)) * 730.0f / 1000.0f * (Math.round(0.212f * 1000.0f) / 1000.0f) * 100.0f) / 100.0f;

        float ist = wärmepumpe.berechnePreisProMonat();

        assertEquals(soll, ist);
    }
}
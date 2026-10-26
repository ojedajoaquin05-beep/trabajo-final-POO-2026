package Vista;

import Modelo.Juego;
import Modelo.Peleador;

// HUD del jugador 1: arriba a la izquierda, las barras se llenan desde la izquierda
public class HudJugador1 extends HudPeleador {

    private static final int MARGEN = 50;

    @Override
    protected Peleador elegirPeleador(Juego juego) {
        return juego.getJugador1();
    }

    @Override
    protected int calcularXMarco(int anchoPanel) {
        return MARGEN;
    }

    @Override
    protected int calcularXRelleno(int xMarco, int anchoRelleno) {
        return xMarco;
    }
}

package Vista;

import Modelo.Juego;
import Modelo.Peleador;

// HUD del jugador 2: arriba a la derecha, las barras se llenan desde la derecha (espejo del jugador 1)
public class HudJugador2 extends HudPeleador {

    private static final int MARGEN = 50;

    @Override
    protected Peleador elegirPeleador(Juego juego) {
        return juego.getJugador2();
    }

    @Override
    protected int calcularXMarco(int anchoPanel) {
        return anchoPanel - MARGEN - ANCHO_BARRA;
    }

    @Override
    protected int calcularXRelleno(int xMarco, int anchoRelleno) {
        return xMarco + ANCHO_BARRA - anchoRelleno;
    }
}

package Vista;

import Modelo.Juego;
import Modelo.Peleador;
import java.awt.Color;
import java.awt.Graphics;

// HUD de un peleador: nombre y vida, barra de vida, puntaje y barra del especial (joaco)
public abstract class HudPeleador implements Dibujable {

    protected static final int ANCHO_BARRA = 300;
    private static final int Y_BARRA_VIDA = 40;
    private static final int ALTO_BARRA_VIDA = 25;
    private static final int Y_BARRA_ESPECIAL = 95;
    private static final int ALTO_BARRA_ESPECIAL = 12;

    @Override
    public final void dibujar(Graphics g, Juego juego, int anchoPanel, int altoPanel) {
        Peleador peleador = elegirPeleador(juego);
        if (peleador == null) return;
        int xMarco = calcularXMarco(anchoPanel);
        dibujarVida(g, peleador, xMarco);
        dibujarPuntaje(g, peleador, xMarco);
        dibujarBarraEspecial(g, peleador, xMarco);
    }

    // Lo que cada hija tiene que resolver
    protected abstract Peleador elegirPeleador(Juego juego);
    protected abstract int calcularXMarco(int anchoPanel);
    // de donde arranca el relleno de una barra (P1 se vacia hacia la izquierda, P2 hacia la derecha)
    protected abstract int calcularXRelleno(int xMarco, int anchoRelleno);

    //BARRA DE VIDA (joaco)
    private void dibujarVida(Graphics g, Peleador peleador, int xMarco) {
        g.setColor(Color.RED);
        g.fillRect(xMarco, Y_BARRA_VIDA, ANCHO_BARRA, ALTO_BARRA_VIDA);
        g.setColor(Color.GREEN);
        int anchoVida = Math.max(0, (int) (ANCHO_BARRA * ((double) peleador.getVida() / peleador.getVidaMaxima())));
        g.fillRect(calcularXRelleno(xMarco, anchoVida), Y_BARRA_VIDA, anchoVida, ALTO_BARRA_VIDA);

        g.setColor(Color.BLACK);
        g.drawRect(xMarco, Y_BARRA_VIDA, ANCHO_BARRA, ALTO_BARRA_VIDA);
        g.setFont(Tipografias.VIDA.deriveFont(24.0f));
        g.drawString(peleador.getNombre().toUpperCase() + " : " + peleador.getVida() + " HP", xMarco, 35);
    }

    // PUNTAJE
    private void dibujarPuntaje(Graphics g, Peleador peleador, int xMarco) {
        g.setFont(Tipografias.VIDA.deriveFont(20.0f));
        g.setColor(Color.black);
        g.drawString("SCORE: " + peleador.getPuntaje(), xMarco, 85);
    }

    // Barra del ataque especial: celeste mientras se carga, amarilla cuando esta listo
    private void dibujarBarraEspecial(Graphics g, Peleador peleador, int xMarco) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(xMarco, Y_BARRA_ESPECIAL, ANCHO_BARRA, ALTO_BARRA_ESPECIAL);
        g.setColor(peleador.isEspecialListo() ? Color.YELLOW : Color.CYAN);
        int anchoCarga = (int) (ANCHO_BARRA * peleador.getProgresoEspecial());
        g.fillRect(calcularXRelleno(xMarco, anchoCarga), Y_BARRA_ESPECIAL, anchoCarga, ALTO_BARRA_ESPECIAL);
        g.setColor(Color.BLACK);
        g.drawRect(xMarco, Y_BARRA_ESPECIAL, ANCHO_BARRA, ALTO_BARRA_ESPECIAL);
    }
}

package Vista;

import Modelo.Juego;
import java.awt.Color;
import java.awt.Graphics;

//TEMPORIZADOR (joaco): arriba al centro, se pone rojo en los ultimos 10 segundos
public class RelojPartida implements Dibujable {

    private static final long DIEZ_SEGUNDOS = 10000000000L; // en nanosegundos

    @Override
    public void dibujar(Graphics g, Juego juego, int anchoPanel, int altoPanel) {
        long tiempoRestanteNanos = juego.getTiempoRestante();
        g.setFont(Tipografias.DISPLAY); // cargada una sola vez
        String textoTiempo = String.format("%02.0f", Math.min(99, Math.ceil(tiempoRestanteNanos / 1000000000.0)));
        int anchoTexto = g.getFontMetrics().stringWidth(textoTiempo);
        int x = (anchoPanel / 2 - anchoTexto / 2);
        final int margenH = 5, margenV = 7;
        g.setColor(Color.BLACK);
        g.fillRect(x - margenH, 0, anchoTexto + margenH * 2, 60 + margenV * 2);
        g.setColor(tiempoRestanteNanos <= DIEZ_SEGUNDOS ? Color.RED : Color.YELLOW);
        g.drawString(textoTiempo, x, 60);
    }
}

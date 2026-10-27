package Vista;

import Modelo.Juego;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MensajeGanador implements Dibujable {

    private static final Font TIPOGRAFIA = new Font("Arial", Font.BOLD, 60); // antes se creaba en cada cuadro

    @Override
    public void dibujar(Graphics g, Juego juego, int anchoPanel, int altoPanel) {
        if (!juego.hayGanador()) return;
        String mensaje = juego.getMensajeGanador();
        g.setFont(TIPOGRAFIA);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, (altoPanel / 2) - 180, anchoPanel, 120);
        g.setColor(Color.YELLOW);
        int anchoMensaje = g.getFontMetrics().stringWidth(mensaje);
        g.drawString(mensaje, (anchoPanel / 2 - anchoMensaje / 2), altoPanel / 2 - 100);
    }
}

package Vista;

import Modelo.Juego;
import java.awt.Graphics;

public interface Dibujable {
    void dibujar(Graphics g, Juego juego, int anchoPanel, int altoPanel);
}

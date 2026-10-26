package Vista;

import Modelo.Juego;
import java.awt.Graphics;

// Todo lo que se dibuja encima de los peleadores (HUD, rayos, reloj, mensaje del ganador).
// PanelJuego recorre una lista de Dibujable y le dice a cada uno "dibujate": no sabe que es cada cosa (polimorfismo).
// Para agregar algo nuevo a la pantalla se crea otra clase y se suma a la lista, sin tocar el panel (OCP)
public interface Dibujable {
    void dibujar(Graphics g, Juego juego, int anchoPanel, int altoPanel);
}

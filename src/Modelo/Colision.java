package Modelo;

import java.awt.rectangle;

public interface Colision {
    int x();
    int y();
    int ancho();
    int alto();
    Rectangle limites();
    boolean colisionaCon(Colision otro);
}


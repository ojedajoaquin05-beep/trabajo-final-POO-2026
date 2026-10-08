package Modelo;

import java.awt.Rectangle;



public interface Colision {
    int x();
    int y();
    int ancho();
    int alto();
    Rectangle getLimites();
    boolean colisionaCon(Colision otro);
}


package Modelo;

import java.awt.Rectangle;

public abstract class Suelo implements Colision {
    private final int x;
    private final int y;
    private final int ancho;
    private final int alto;

    public Suelo(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public int ancho() {
        return ancho;
    }

    @Override
    public int alto() {
        return alto;
    }

    @Override
    public Rectangle getLimites() {
        return new Rectangle(x, y, ancho, alto);
    }

    @Override
    public boolean colisionaCon(Colision otro) {
        return getLimites().intersects(otro.getLimites());
    }
    
}

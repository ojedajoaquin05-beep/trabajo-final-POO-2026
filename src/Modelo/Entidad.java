package Modelo;

import java.awt.Rectangle;

// abstract porque es el molde/base para otros pj's
public abstract class Entidad implements Colision {
    
    // Usamos protected para que la clase hija (Luchador) pueda acceder y modificarlos
    protected String nombre;
    protected int  posicionX; // Lo pasamos a float para que el movimiento sea más fluido
    protected int posicionY;
    protected boolean seleccionado;
    protected int vida;
    protected int vidaMaxima;
    protected int ataque;
    
    // Atributos de colisión (hitbox)
    protected int ancho = 40;
    protected int alto = 80;

    public Entidad(String nombre, int posicionX, int posicionY, int vida, int ataque) {
        this.nombre = nombre;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.vidaMaxima = vida;
        this.vida = vida;
        this.ataque = ataque;
        this.seleccionado = false;
    }

    public void recibirdanio(int cantidad) {
        this.vida -= cantidad;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }

    public boolean estaVivo() {
        return this.vida > 0;   
    }

    // estos metodos se reescriben porque cada personaje tiene su propia altura y ancho. por eso se reescribe 
    @Override
    public int x() { return Math.round(posicionX); }

    @Override
    public int y() { return Math.round(posicionY); }

    @Override
    public int ancho() { return ancho; }

    @Override
    public int alto() { return alto; }

    @Override
    public Rectangle getLimites() {
        return new Rectangle(Math.round(posicionX), Math.round(posicionY), ancho, alto);
    }

    @Override
    public boolean colisionaCon(Colision otro) {
        return getLimites().intersects(otro.getLimites());
    }

    // getters y setters
    public String getNombre() { return nombre; }
    
    public int getPosicionX() { return posicionX; }
    public void setPosicionX(int posicionX) { this.posicionX = posicionX; }
    
    public int getPosicionY() { return posicionY; }
    public void setPosicionY(int posicionY) { this.posicionY = posicionY; }
    
    public boolean isSeleccionado() { return seleccionado; }
    public void setSeleccionado(boolean seleccionado) { this.seleccionado = seleccionado; }
    
    public int getVida() { return vida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getAtaque() { return ataque; }

    public void mover(float dX, float dY){
        this.posicionX += dX;
        this.posicionY += dY;
    }
}
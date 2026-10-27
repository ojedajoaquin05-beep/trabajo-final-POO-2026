package Controlador;

import Modelo.ControlJugador;

public class TeclasJugador implements ControlJugador {

    private boolean arriba, abajo, izquierda, derecha;
    private boolean golpe, patada, especial;

    @Override public boolean arriba() { return arriba; }
    @Override public boolean abajo() { return abajo; }
    @Override public boolean izquierda() { return izquierda; }
    @Override public boolean derecha() { return derecha; }
    @Override public boolean golpe() { return golpe; }
    @Override public boolean patada() { return patada; }
    @Override public boolean especial() { return especial; }

    // los setters los usa ControladorTeclado al apretar y soltar cada tecla
    void setArriba(boolean apretada) { this.arriba = apretada; }
    void setAbajo(boolean apretada) { this.abajo = apretada; }
    void setIzquierda(boolean apretada) { this.izquierda = apretada; }
    void setDerecha(boolean apretada) { this.derecha = apretada; }
    void setGolpe(boolean apretada) { this.golpe = apretada; }
    void setPatada(boolean apretada) { this.patada = apretada; }
    void setEspecial(boolean apretada) { this.especial = apretada; }

    // Suelta todas las teclas (al pausar o al terminar la partida)
    public void resetear() {
        arriba = false;
        abajo = false;
        izquierda = false;
        derecha = false;
        golpe = false;
        patada = false;
        especial = false;
    }
}

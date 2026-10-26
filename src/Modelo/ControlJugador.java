package Modelo;

// Lo que un jugador le pide a su peleador en este momento.
// El modelo no sabe si detras hay un teclado, un joystick o una IA: solo conoce esta interfaz (DIP).
// Hoy la implementa TeclasJugador (Controlador); mañana podria implementarla un rival manejado por la compu sin tocar el modelo (OCP).
public interface ControlJugador {
    boolean arriba();
    boolean abajo();
    boolean izquierda();
    boolean derecha();
    boolean golpe();
    boolean patada();
    boolean especial();
}

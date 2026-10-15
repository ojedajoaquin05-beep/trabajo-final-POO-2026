package Modelo;

import java.time.LocalDate;

// Un resultado de combate. Nace válido o no nace.
public class Puntaje {

    private final String jugador; // 3 letras identificatorias
    private final int puntos;
    private final LocalDate fecha;

    public Puntaje(String jugador, int puntos, LocalDate fecha) {
        if (jugador == null || !jugador.matches("[A-Za-z]{3}") || puntos < 0 || fecha == null) {
            throw new IllegalArgumentException("Jugador (3 letras), puntos y fecha son obligatorios");
        }
        this.jugador = jugador.toUpperCase();
        this.puntos = puntos;
        this.fecha = fecha;
    }

    public String getJugador() { return jugador; }
    public int getPuntos() { return puntos; }
    public LocalDate getFecha() { return fecha; }
}

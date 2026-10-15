package Modelo;

import java.util.List;

// La interfaz vive en el modelo y no tiene ni una palabra de SQL.
public interface PuntajeDAO {
    boolean guardar(Puntaje puntaje);
    List<Puntaje> mejores(int cantidad);
}